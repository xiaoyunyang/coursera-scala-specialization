package week2

import common._

object parallelFoldReduce {

	/** implement fold or reduce in parallel */
	//fold takes a binary operation
	//fold, foldLeft and foldRight take an initial element as a starting point and a binary operation
	//scan, scanLeft, and scanRight records the intermediate values of the corresponding fold operations
	List(1,3,8).fold(100)((s,x) => s+x)       //> res0: Int = 112
 	List(1,3,8).scan(100)((s,x) => s+x)       //> res1: List[Int] = List(100, 101, 104, 112)
 	
  //foldLeft goes through the original list from left to right, and constructs the new list from left to right
  List(1,3,8).foldLeft(100)((s,x) => s-x)  == ((100-1)-3)-8
                                                  //> res2: Boolean = true
  
  //foldRight goes through the original list from right to left, and constructs the new list from right to left
  List(1,3,8).foldRight(100)((s,x) => s-x)  == 1-(3-(8-100))
                                                  //> res3: Boolean = true
  

	//reduce takes the original list as the starting point
	//reduceLeft and reduceRight differ in the order in which they combine operations of collections
	List(1,3,8).reduceLeft((s,x) => s-x) == (1-3)-8
                                                  //> res4: Boolean = true
	List(1,3,8).reduceRight((s,x) => s-x) == 1-(3-8)
                                                  //> res5: Boolean = true
	
	//To enable parallel operations, we look at associative operations, e.g., addition,
	//string concatenation. Not the minus operation above. Associative removes the constraint
	//that the list must be processed in certain orders

	List("a", "b", "c").foldLeft("!")((s,x) => s+x)
                                                  //> res6: String = !abc
	List("a", "b", "c").scanLeft("!")((s,x) => s+x)
                                                  //> res7: List[String] = List(!, !a, !ab, !abc)
  
  List("a", "b", "c").foldRight("!")((s,x) => s+x)//> res8: String = abc!
  List("a", "b", "c").scanRight("!")((s,x) => s+x)//> res9: List[String] = List(abc!, bc!, c!, !)
  
  //sequential definition of the folding tree
  def reduce[A](t: FoldTree[A], f: (A,A) => A): A = t match {
  	case FoldLeaf(v) => v
  	case FoldNode(l,r) => f(reduce(l,f), reduce(r, f))
  }                                               //> reduce: [A](t: week2.FoldTree[A], f: (A, A) => A)A
  //parallel definition of the folding tree
  def parReduce[A](t: FoldTree[A], f: (A,A) => A): A = t match {
  	case FoldLeaf(v) => v
  	case FoldNode(l,r) => {
  		val (lb, rb) = parallel(parReduce(l, f), parReduce(r,f))
  		f(lb, rb)
  	}
  }                                               //> parReduce: [A](t: week2.FoldTree[A], f: (A, A) => A)A
  
  /* Express toList using map and reduce
   *
   */
	def toList[A](t: FoldTree[A]): List[A] = {
		def f = (l:List[A], r:List[A]) => l ++ r
		parReduce(t.map(List(_)), f)
	}                                         //> toList: [A](t: week2.FoldTree[A])List[A]
	
  val foldTree = FoldNode(FoldLeaf(1), FoldNode(FoldLeaf(3), FoldLeaf(8)))
                                                  //> foldTree  : week2.FoldNode[Int] = {FoldLeaf(1) | {FoldLeaf(3) | FoldLeaf(8)
                                                  //| }}
  def fMinus = (x: Int, y: Int) => x - y          //> fMinus: => (Int, Int) => Int
  val resSeq = reduce(foldTree, fMinus)           //> resSeq  : Int = 6
	val resPar = parReduce(foldTree, fMinus)  //> resPar  : Int = 6
	
	foldTree.toList                           //> res10: List[Int] = List(1, 3, 8)
	foldTree.map(_+1)                         //> res11: week2.FoldTree[Int] = {FoldLeaf(2) | {FoldLeaf(4) | FoldLeaf(9)}}
	
	toList(foldTree)                          //> res12: List[Int] = List(1, 3, 8)
		
	/* consequence of associativity
	 * consider two expressions with teh same list of
	 * operands connected with a binary operation, but different parentheses, Then these
	 * expressions evaluate to the same result
	 */
	val foldTree1 = FoldNode(FoldLeaf(1), FoldNode(FoldLeaf(3), FoldLeaf(8)))
                                                  //> foldTree1  : week2.FoldNode[Int] = {FoldLeaf(1) | {FoldLeaf(3) | FoldLeaf(8
                                                  //| )}}
	val foldTree2 = FoldNode(FoldNode(FoldLeaf(1), FoldLeaf(3)), FoldLeaf(8))
                                                  //> foldTree2  : week2.FoldNode[Int] = {{FoldLeaf(1) | FoldLeaf(3)} | FoldLeaf(
                                                  //| 8)}
  def fPlus = (x: Int, y: Int) => x + y           //> fPlus: => (Int, Int) => Int
 	foldTree.toList == foldTree2.toList       //> res13: Boolean = true
 	reduce(foldTree, fPlus) == reduce(foldTree, fPlus)
                                                  //> res14: Boolean = true
 	reduce(foldTree, fMinus) == reduce(foldTree, fMinus)
                                                  //> res15: Boolean = true
	
	/* Parallel Array reduce
	 * computes fold of a given array segment with a given associative operation f
	 */
	val threshold = 10000                     //> threshold  : Int = 10000
 
  def reduceSeg[A](a: Array[A], left: Int, right: Int,  f: (A, A) => A): A = {
  	
  	if(right - left < threshold) {
  		var res = a(left)
	  	var i = left + 1
	  	while(i < right) {
	  		res = f(res, a(i))
	  		i += 1
	  	}
	  	res
  	} else {
  		val mid = left + (right - left) / 2
  		val (a1, a2) = parallel(reduceSeg(a, left, mid, f), reduceSeg(a, mid, right, f))
  		f(a1, a2)
  	}
  	
  }                                               //> reduceSeg: [A](a: Array[A], left: Int, right: Int, f: (A, A) => A)A
  
  def reduceArr[A](a: Array[A], f: (A, A) => A): A = reduceSeg(a, 0, a.length, f)
                                                  //> reduceArr: [A](a: Array[A], f: (A, A) => A)A

  def norm(a: Array[Int], p: Int) = a.map(Math.abs(_)).map(Math.pow(_, p)).reduce(_+_)
                                                  //> norm: (a: Array[Int], p: Int)Double
  val e = 1e-200                                  //> e  : Double = 1.0E-200
  val x = 1e200                                   //> x  : Double = 1.0E200
  val mx = -1.0e200                               //> mx  : Double = -1.0E200
  (x + mx) + e                                    //> res16: Double = 1.0E-200
  x + (mx + e)                                    //> res17: Double = 0.0

  /** Commutative vs. Associative */
  //Floating point is commutative but not associative
  (x + mx) + e == x + (mx + e)                    //> res18: Boolean = false
  
  //Floating point multiplication is also not associative
  e* (x * x)                                      //> res19: Double = Infinity
  (e * x) * x                                     //> res20: Double = 1.0E200
  (e * x) * x == e * (x * x)                      //> res21: Boolean = false
  

  //making an operation commutative is easy
  //Patching function to make them commutative but we don't have patches to make them
  //associative
  def less(x: Double, y: Double): Boolean = x > y //> less: (x: Double, y: Double)Boolean
  def g(a: Double, b: Double) = a + b             //> g: (a: Double, b: Double)Double
  def f(x: Double, y: Double) = if(less(y,x)) g(y, x) else g(x,y)
                                                  //> f: (x: Double, y: Double)Double
  
  /** Associative Operation on tuples */
  //assume f1 and f2 are associative
  def f1(a: (String, String)) = a._1 + a._2       //> f1: (a: (String, String))String
 	def f2(a: (Int, Int)) = a._1 * a._2       //> f2: (a: (Int, Int))Int
  
  //Then, f3 is associative
  def f3(a1: (String, Int), a2: (String, Int)) = (f1(a1._1, a2._1), f2(a1._2, a2._2))
                                                  //> f3: (a1: (String, Int), a2: (String, Int))(String, Int)
  def x1 = "a"                                    //> x1: => String
  def x2 = "b"                                    //> x2: => String
  def y1 = 1                                      //> y1: => Int
  def y2 = 2                                      //> y2: => Int
  def z1 = 3                                      //> z1: => Int
  def z2 = 4                                      //> z2: => Int
  f3((x1, y1), (x2, y2))                          //> res22: (String, Int) = (ab,2)
 	f3((x1, y1), (x2, y2)) == (f1(x1, x2), f2(y1, y2))
                                                  //> res23: Boolean = true
 
  //Example 1 - rational multiplication
  //suppose we use 32 bit numbers to represent numer and denom. Because multiplication
  //modulo 2^32 is associative, so is times
  def times(x: (Int, Int), y: (Int, Int)) = (x._1*x._2, y._1*y._2)
                                                  //> times: (x: (Int, Int), y: (Int, Int))(Int, Int)
	//Example 2 - compute average using reduction twice
	val sum = List(1,2,3).reduce(_ + _)       //> sum  : Int = 6
	val length = List(1,2,3).map(_ => 1).reduce(_+_)
                                                  //> length  : Int = 3
	sum/length                                //> res24: Int = 2
	
	
	//Example 2a - compute average using reduction once
	val (sum2, length2) = List(1,2,3).map(a => (a, 1))
	                                 .reduce((a, b) => ((a._1 + b._1),  (a._2 + b._2)))
                                                  //> sum2  : Int = 6
                                                  //| length2  : Int = 3
	sum2 / length2                            //> res25: Int = 2
	
	
	//Exampel 3 - addition of modular fractions
	def plus(x: (Int, Int), y: (Int, Int)) = (x._1*y._2 + x._2*y._1, y._1*y._2)
                                                  //> plus: (x: (Int, Int), y: (Int, Int))(Int, Int)
	
	//Example 4 - relativitistic velocity (associative)
	def fRelInt(u: Int, v: Int): Int = (u + v)/(1 + u*v)
                                                  //> fRelInt: (u: Int, v: Int)Int
	def errInt(lst:List[Int]): Int =
  	lst.reduceLeft(fRelInt) - lst.reduceRight(fRelInt)
                                                  //> errInt: (lst: List[Int])Int
  
  def testAssocInt: Int = {
  	val r = new scala.util.Random
  	val lst = List.fill(400)(r.nextInt)
  	errInt(lst)
	}                                         //> testAssocInt: => Int
	testAssocInt                              //> res26: Int = 0
	testAssocInt                              //> res27: Int = 0
	testAssocInt                              //> res28: Int = 0
	testAssocInt                              //> res29: Int = 0
	testAssocInt                              //> res30: Int = 2
	
	
	//Example 4a - relativitistic velocity (not associative because Double is not associative)
	def fRelDoub(u: Double, v: Double): Double = (u + v)/(1.0 + u*v)
                                                  //> fRelDoub: (u: Double, v: Double)Double
	def errDoub(lst:List[Double]): Double =
  	lst.reduceLeft(fRelDoub) - lst.reduceRight(fRelDoub)
                                                  //> errDoub: (lst: List[Double])Double
  
  def testAssocDoub: Double = {
  	val r = new scala.util.Random
  	val lst = List.fill(400)(r.nextDouble*0.002)
  	errDoub(lst)
	}                                         //> testAssocDoub: => Double
	testAssocDoub                             //> res31: Double = -3.885780586188048E-16
	testAssocDoub                             //> res32: Double = -2.220446049250313E-16
	testAssocDoub                             //> res33: Double = 2.7755575615628914E-16
	testAssocDoub                             //> res34: Double = -1.6653345369377348E-16
		
}

/** Folding (reducing) Tree **/
sealed class FoldTree[+A] {
	def toList: List[A] = this match {
		case FoldLeaf(v) => List(v)
		case FoldNode(l, r) => l.toList ++ r.toList
	}
	def map[B](f: A => B): FoldTree[B] = this match {
		case FoldLeaf(v) => FoldLeaf(f(v))
		case FoldNode(l,r) => FoldNode(l.map(f), r.map(f))
	}
}

case class FoldLeaf[A](value: A) extends FoldTree[A]

case class FoldNode[A](l: FoldTree[A], r: FoldTree[A]) extends FoldTree[A] {
	override def toString = s"{" + l + " | " + r + "}"
}

object FoldTree {
	def apply[A](v: A): FoldLeaf[A] = new FoldLeaf(v)
	def apply[A](l: FoldTree[A], r: FoldTree[A]): FoldTree[A] = new FoldNode(l, r)
}