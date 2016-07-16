package week2

import org.scalameter._
import common._

/** How to implement map in parallel */
object parallelMap {

	
 	List(1,3,8).map(x => x*x)                 //> res0: List[Int] = List(1, 9, 64)

	/* Parallel Datastructures (1) - Arrays
	 *
	 */
	//Lists are not good for parallel implementations because we cannot efficiently
	//split them in half and combine them
	//Two alternatives to Lists
	//arrays - imperative
	//trees - can be implemented functionally

	//A recursive definition of a sequential map
	def mapSeq[A, B](l: List[A], f: A => B): List[B] = l match {
		case Nil => Nil
		case h :: t => f(h) :: mapSeq(t, f)
	}                                         //> mapSeq: [A, B](l: List[A], f: A => B)List[B]
	def mapSeqPar[A, B](l: List[A], f: A => B): List[B] = l match {
		case Nil => Nil
		case h :: t => {
			val (mh, mr) = parallel(f(h), mapSeq(t, f))
			mh :: mr
		}
	}                                         //> mapSeqPar: [A, B](l: List[A], f: A => B)List[B]
	
	
	//A sequential map of array producing an array
	def mapASegSeq[A,B](inp: Array[A], left: Int, right: Int, f: A => B, out: Array[B]) = {
		//Writes to out(i) for left <= i <= right-1
		var i = left
		while(i < right) {
			out(i) = f(inp(i))
			i += 1
		}
	}                                         //> mapASegSeq: [A, B](inp: Array[A], left: Int, right: Int, f: A => B, out: Ar
                                                  //| ray[B])Unit
	
	
	val threshold = 10000                     //> threshold  : Int = 10000
	
	def mapASegPar[A,B](inp: Array[A], left: Int, right: Int, f: A => B, out: Array[B]): Unit = {
		//Write to out(i) for left <= i <= right - 1
		if(right - left < threshold) {  //Threshold needs to be large enough, otherwise we lose efficiency
			mapASegSeq(inp, left, right, f, out)
		} else {
			val mid = left + (right-left) / 2
			parallel(mapASegPar(inp, left, mid, f, out), mapASegPar(inp, mid, right, f, out))
		}
	}                                         //> mapASegPar: [A, B](inp: Array[A], left: Int, right: Int, f: A => B, out: Ar
                                                  //| ray[B])Unit
	val in = Array(2,3,4,5,6)                 //> in  : Array[Int] = Array(2, 3, 4, 5, 6)
	val out1 = Array(0,0,0,0,0)               //> out1  : Array[Int] = Array(0, 0, 0, 0, 0)
	val out2 = Array(0,0,0,0,0)               //> out2  : Array[Int] = Array(0, 0, 0, 0, 0)
	val out3 = Array(0,0,0,0,0)               //> out3  : Array[Int] = Array(0, 0, 0, 0, 0)
	val out4 = (1 until 100).toArray          //> out4  : Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1
                                                  //| 5, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 
                                                  //| 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
                                                  //|  53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71
                                                  //| , 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 9
                                                  //| 0, 91, 92, 93, 94, 95, 96, 97, 98, 99)
	val f = (x: Int) => x*x                   //> f  : Int => Int = <function1>
	
	mapSeqPar(in.toList, f)                   //> res1: List[Int] = List(4, 9, 16, 25, 36)
	
	
	mapASegSeq(in, 0, in.length, f, out1)
	out1                                      //> res2: Array[Int] = Array(4, 9, 16, 25, 36)
	
	mapASegSeq(in, 3, 2, f, out2)
	out2                                      //> res3: Array[Int] = Array(0, 0, 0, 0, 0)
	
	mapASegPar(in, 1, 2, f, out3)
	out3                                      //> res4: Array[Int] = Array(0, 9, 0, 0, 0)
	
	mapASegPar(in, 1, 2, f, out4)
	out4                                      //> res5: Array[Int] = Array(1, 9, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                                                  //|  16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34
                                                  //| , 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 5
                                                  //| 3, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 
                                                  //| 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
                                                  //|  91, 92, 93, 94, 95, 96, 97, 98, 99)
                                                  
                                                  
	/* Parallel Datastructures (1) - Trees
	 *
	 */
	//Leaves store array segments
	//non-leaf node stores number of elements
	//Assume our trees are approximately balanced
	def mapTreePar[A: Manifest, B: Manifest](t: MapTree[A], f: A => B): MapTree[B] = t match {
		//match on shape of the tree
		case MapLeaf(a) => { //we do sequential in the Leaf case because Array too small to warrant parallelization
			val len = a.length
			val b = new Array[B](len)
			var i = 0
			while(i<len) {
				b(i) = f(a(i))
				i +=1
			}
			MapLeaf(b)
		}
		case MapNode(l, r) => { //do parallel for the large cases, i.e., Node
			val (lb, rb) = parallel(mapTreePar(l, f), mapTreePar(r, f))
			MapNode(lb, rb)
		}
	}                                         //> mapTreePar: [A, B](t: week2.MapTree[A], f: A => B)(implicit evidence$1: Man
                                                  //| ifest[A], implicit evidence$2: Manifest[B])week2.MapTree[B]
	
	
	val t = MapNode(MapLeaf(Array(1)), MapLeaf(Array(3,8)))
                                                  //> t  : week2.MapNode[Int] = {1  | 3 8 }
	mapTreePar(t, f)                          //> res6: week2.MapTree[Int] = {1  | 9 64 }

}

/** Map Tree **/
sealed abstract class MapTree[A] {
	val size: Int
}

case class MapLeaf[A](a: Array[A]) extends MapTree[A] {
	override val size = a.size
	override def toString = a.foldRight("")(_+ " "+ _)
}
case class MapNode[A](l: MapTree[A], r: MapTree[A]) extends MapTree[A] {
	override val size = l.size + r.size
	override def toString = s"{" + l + " | " + r + "}"
}

object MapTree {
	def apply[A](a: Array[A]): MapLeaf[A] = new MapLeaf(a)
	def apply[A](l: MapTree[A], r: MapTree[A]): MapTree[A] = new MapNode(l, r)
}