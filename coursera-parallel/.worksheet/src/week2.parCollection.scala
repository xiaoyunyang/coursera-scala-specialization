package week2

import org.scalameter._
import common._

object parCollection {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(148); val res$0 = 

	/** How to implement map in parallel */
 	List(1,3,8).map(x => x*x);System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(434); 

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
	};System.out.println("""mapSeq: [A, B](l: List[A], f: A => B)List[B]""");$skip(168); 
	def mapSeqPar[A, B](l: List[A], f: A => B): List[B] = l match {
		case Nil => Nil
		case h :: t => {
			val (mh, mr) = parallel(f(h), mapSeq(t, f))
			mh :: mr
		}
	};System.out.println("""mapSeqPar: [A, B](l: List[A], f: A => B)List[B]""");$skip(262); 
	
	
	//A sequential map of array producing an array
	def mapASegSeq[A,B](inp: Array[A], left: Int, right: Int, f: A => B, out: Array[B]) = {
		//Writes to out(i) for left <= i <= right-1
		var i = left
		while(i < right) {
			out(i) = f(inp(i))
			i += 1
		}
	};System.out.println("""mapASegSeq: [A, B](inp: Array[A], left: Int, right: Int, f: A => B, out: Array[B])Unit""");$skip(27); 
	
	
	val threshold = 10000;System.out.println("""threshold  : Int = """ + $show(threshold ));$skip(425); 
	
	def mapASegPar[A,B](inp: Array[A], left: Int, right: Int, f: A => B, out: Array[B]): Unit = {
		//Write to out(i) for left <= i <= right - 1
		if(right - left < threshold) {  //Threshold needs to be large enough, otherwise we lose efficiency
			mapASegSeq(inp, left, right, f, out)
		} else {
			val mid = left + (right-left) / 2
			parallel(mapASegPar(inp, left, mid, f, out), mapASegPar(inp, mid, right, f, out))
		}
	};System.out.println("""mapASegPar: [A, B](inp: Array[A], left: Int, right: Int, f: A => B, out: Array[B])Unit""");$skip(27); 
	val in = Array(2,3,4,5,6);System.out.println("""in  : Array[Int] = """ + $show(in ));$skip(29); 
	val out1 = Array(0,0,0,0,0);System.out.println("""out1  : Array[Int] = """ + $show(out1 ));$skip(29); 
	val out2 = Array(0,0,0,0,0);System.out.println("""out2  : Array[Int] = """ + $show(out2 ));$skip(29); 
	val out3 = Array(0,0,0,0,0);System.out.println("""out3  : Array[Int] = """ + $show(out3 ));$skip(34); 
	val out4 = (1 until 100).toArray;System.out.println("""out4  : Array[Int] = """ + $show(out4 ));$skip(25); 
	val f = (x: Int) => x*x;System.out.println("""f  : Int => Int = """ + $show(f ));$skip(27); val res$1 = 
	
	mapSeqPar(in.toList, f);System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(43); 
	
	
	mapASegSeq(in, 0, in.length, f, out1);$skip(6); val res$2 = 
	out1;System.out.println("""res2: Array[Int] = """ + $show(res$2));$skip(33); 
	
	mapASegSeq(in, 3, 2, f, out2);$skip(6); val res$3 = 
	out2;System.out.println("""res3: Array[Int] = """ + $show(res$3));$skip(33); 
	
	mapASegPar(in, 1, 2, f, out3);$skip(6); val res$4 = 
	out3;System.out.println("""res4: Array[Int] = """ + $show(res$4));$skip(33); 
	
	mapASegPar(in, 1, 2, f, out4);$skip(6); val res$5 = 
	out4;System.out.println("""res5: Array[Int] = """ + $show(res$5));$skip(800); 
                                                  
                                                  
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
	};System.out.println("""mapTreePar: [A, B](t: week2.MapTree[A], f: A => B)(implicit evidence$1: Manifest[A], implicit evidence$2: Manifest[B])week2.MapTree[B]""");$skip(61); 
	
	
	val t = MapNode(MapLeaf(Array(1)), MapLeaf(Array(3,8)));System.out.println("""t  : week2.MapNode[Int] = """ + $show(t ));$skip(18); val res$6 = 
	mapTreePar(t, f);System.out.println("""res6: week2.MapTree[Int] = """ + $show(res$6))}
	
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
