package week2

import common._

object associativity {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(371); val res$0 = 

	/** implement fold or reduce in parallel */
	//fold takes a binary operation
	//fold, foldLeft and foldRight take an initial element as a starting point and a binary operation
	//scan, scanLeft, and scanRight records the intermediate values of the corresponding fold operations
	List(1,3,8).fold(100)((s,x) => s+x);System.out.println("""res0: Int = """ + $show(res$0));$skip(38); val res$1 = 
 	List(1,3,8).scan(100)((s,x) => s+x);System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(174); val res$2 = 
 	
  //foldLeft goes through the original list from left to right, and constructs the new list from left to right
  List(1,3,8).foldLeft(100)((s,x) => s-x)  == ((100-1)-3)-8;System.out.println("""res2: Boolean = """ + $show(res$2));$skip(176); val res$3 = 
  
  //foldRight goes through the original list from right to left, and constructs the new list from right to left
  List(1,3,8).foldRight(100)((s,x) => s-x)  == 1-(3-(8-100));System.out.println("""res3: Boolean = """ + $show(res$3));$skip(207); val res$4 = 
  

	//reduce takes the original list as the starting point
	//reduceLeft and reduceRight differ in the order in which they combine operations of collections
	List(1,3,8).reduceLeft((s,x) => s-x) == (1-3)-8;System.out.println("""res4: Boolean = """ + $show(res$4));$skip(50); val res$5 = 
	List(1,3,8).reduceRight((s,x) => s-x) == 1-(3-8);System.out.println("""res5: Boolean = """ + $show(res$5));$skip(281); val res$6 = 
	
	//To enable parallel operations, we look at associative operations, e.g., addition,
	//string concatenation. Not the minus operation above. Associative removes the constraint
	//that the list must be processed in certain orders

	List("a", "b", "c").foldLeft("!")((s,x) => s+x);System.out.println("""res6: String = """ + $show(res$6));$skip(49); val res$7 = 
	List("a", "b", "c").scanLeft("!")((s,x) => s+x);System.out.println("""res7: List[String] = """ + $show(res$7));$skip(54); val res$8 = 
  
  List("a", "b", "c").foldRight("!")((s,x) => s+x);System.out.println("""res8: String = """ + $show(res$8));$skip(51); val res$9 = 
  List("a", "b", "c").scanRight("!")((s,x) => s+x);System.out.println("""res9: List[String] = """ + $show(res$9));$skip(194); 
  
  //sequential definition of the folding tree
  def reduce[A](t: FoldTree[A], f: (A,A) => A): A = t match {
  	case FoldLeaf(v) => v
  	case FoldNode(l,r) => f(reduce(l,f), reduce(r, f))
  };System.out.println("""reduce: [A](t: week2.FoldTree[A], f: (A, A) => A)A""");$skip(245); 
  //parallel definition of the folding tree
  def reducePar[A](t: FoldTree[A], f: (A,A) => A): A = t match {
  	case FoldLeaf(v) => v
  	case FoldNode(l,r) => {
  		val (lb, rb) = parallel(reducePar(l, f), reducePar(r,f))
  		f(lb, rb)
  	}
  };System.out.println("""reducePar: [A](t: week2.FoldTree[A], f: (A, A) => A)A""");$skip(176); 
  
  /* Express toList using map and reduce
   *
   */
	def toList[A](t: FoldTree[A]): List[A] = {
		def f = (l:List[A], r:List[A]) => l ++ r
		reducePar(t.map(List(_)), f)
	};System.out.println("""toList: [A](t: week2.FoldTree[A])List[A]""");$skip(77); 
	
  val foldTree = FoldNode(FoldLeaf(1), FoldNode(FoldLeaf(3), FoldLeaf(8)));System.out.println("""foldTree  : week2.FoldNode[Int] = """ + $show(foldTree ));$skip(41); 
  def fMinus = (x: Int, y: Int) => x - y;System.out.println("""fMinus: => (Int, Int) => Int""");$skip(40); 
  val resSeq = reduce(foldTree, fMinus);System.out.println("""resSeq  : Int = """ + $show(resSeq ));$skip(42); 
	val resPar = reducePar(foldTree, fMinus);System.out.println("""resPar  : Int = """ + $show(resPar ));$skip(19); val res$10 = 
	
	foldTree.toList;System.out.println("""res10: List[Int] = """ + $show(res$10));$skip(19); val res$11 = 
	foldTree.map(_+1);System.out.println("""res11: week2.FoldTree[Int] = """ + $show(res$11));$skip(20); val res$12 = 
	
	toList(foldTree);System.out.println("""res12: List[Int] = """ + $show(res$12));$skip(297); 
		
	/* consequence of associativity
	 * consider two expressions with teh same list of
	 * operands connected with a binary operation, but different parentheses, Then these
	 * expressions evaluate to the same result
	 */
	val foldTree1 = FoldNode(FoldLeaf(1), FoldNode(FoldLeaf(3), FoldLeaf(8)));System.out.println("""foldTree1  : week2.FoldNode[Int] = """ + $show(foldTree1 ));$skip(75); 
	val foldTree2 = FoldNode(FoldNode(FoldLeaf(1), FoldLeaf(3)), FoldLeaf(8));System.out.println("""foldTree2  : week2.FoldNode[Int] = """ + $show(foldTree2 ));$skip(40); 
  def fPlus = (x: Int, y: Int) => x + y;System.out.println("""fPlus: => (Int, Int) => Int""");$skip(38); val res$13 = 
 	foldTree.toList == foldTree2.toList;System.out.println("""res13: Boolean = """ + $show(res$13));$skip(53); val res$14 = 
 	reduce(foldTree, fPlus) == reduce(foldTree, fPlus);System.out.println("""res14: Boolean = """ + $show(res$14));$skip(55); val res$15 = 
 	reduce(foldTree, fMinus) == reduce(foldTree, fMinus);System.out.println("""res15: Boolean = """ + $show(res$15));$skip(136); 
	
	/* Parallel Array reduce
	 * computes fold of a given array segment with a given associative operation f
	 */
	val threshold = 10000;System.out.println("""threshold  : Int = """ + $show(threshold ));$skip(399); 
 
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
  	
  };System.out.println("""reduceSeg: [A](a: Array[A], left: Int, right: Int, f: (A, A) => A)A""");$skip(85); 
  
  def reduceArr[A](a: Array[A], f: (A, A) => A): A = reduceSeg(a, 0, a.length, f);System.out.println("""reduceArr: [A](a: Array[A], f: (A, A) => A)A""");$skip(88); 

  def norm(a: Array[Int], p: Int) = a.map(Math.abs(_)).map(Math.pow(_, p)).reduce(_+_);System.out.println("""norm: (a: Array[Int], p: Int)Double""");$skip(17); 
  val e = 1e-200;System.out.println("""e  : Double = """ + $show(e ));$skip(16); 
  val x = 1e200;System.out.println("""x  : Double = """ + $show(x ));$skip(20); 
  val mx = -1.0e200;System.out.println("""mx  : Double = """ + $show(mx ));$skip(15); val res$16 = 
  (x + mx) + e;System.out.println("""res16: Double = """ + $show(res$16));$skip(15); val res$17 = 
  x + (mx + e);System.out.println("""res17: Double = """ + $show(res$17));$skip(123); val res$18 = 

  /** Commutative vs. Associative */
  //Floating point is commutative but not associative
  (x + mx) + e == x + (mx + e);System.out.println("""res18: Boolean = """ + $show(res$18));$skip(74); val res$19 = 
  
  //Floating point multiplication is also not associative
  e* (x * x);System.out.println("""res19: Double = """ + $show(res$19));$skip(14); val res$20 = 
  (e * x) * x;System.out.println("""res20: Double = """ + $show(res$20));$skip(29); val res$21 = 
  (e * x) * x == e * (x * x);System.out.println("""res21: Boolean = """ + $show(res$21));$skip(200); 
  

  //making an operation commutative is easy
  //Patching function to make them commutative but we don't have patches to make them
  //associative
  def less(x: Double, y: Double): Boolean = x > y;System.out.println("""less: (x: Double, y: Double)Boolean""");$skip(38); 
  def g(a: Double, b: Double) = a + b;System.out.println("""g: (a: Double, b: Double)Double""");$skip(66); 
  def f(x: Double, y: Double) = if(less(y,x)) g(y, x) else g(x,y);System.out.println("""f: (x: Double, y: Double)Double""");$skip(125); 
  
  /** Associative Operation on tuples */
  //assume f1 and f2 are associative
  def f1(a: (String, String)) = a._1 + a._2;System.out.println("""f1: (a: (String, String))String""");$skip(38); 
 	def f2(a: (Int, Int)) = a._1 * a._2;System.out.println("""f2: (a: (Int, Int))Int""");$skip(117); 
  
  //Then, f3 is associative
  def f3(a1: (String, Int), a2: (String, Int)) = (f1(a1._1, a2._1), f2(a1._2, a2._2));System.out.println("""f3: (a1: (String, Int), a2: (String, Int))(String, Int)""");$skip(15); 
  def x1 = "a";System.out.println("""x1: => String""");$skip(15); 
  def x2 = "b";System.out.println("""x2: => String""");$skip(13); 
  def y1 = 1;System.out.println("""y1: => Int""");$skip(13); 
  def y2 = 2;System.out.println("""y2: => Int""");$skip(13); 
  def z1 = 3;System.out.println("""z1: => Int""");$skip(13); 
  def z2 = 4;System.out.println("""z2: => Int""");$skip(25); val res$22 = 
  f3((x1, y1), (x2, y2));System.out.println("""res22: (String, Int) = """ + $show(res$22));$skip(53); val res$23 = 
 	f3((x1, y1), (x2, y2)) == (f1(x1, x2), f2(y1, y2));System.out.println("""res23: Boolean = """ + $show(res$23));$skip(240); 
 
  //Example 1 - rational multiplication
  //suppose we use 32 bit numbers to represent numer and denom. Because multiplication
  //modulo 2^32 is associative, so is times
  def times(x: (Int, Int), y: (Int, Int)) = (x._1*x._2, y._1*y._2);System.out.println("""times: (x: (Int, Int), y: (Int, Int))(Int, Int)""");$skip(90); 
	//Example 2 - compute average using reduction twice
	val sum = List(1,2,3).reduce(_ + _);System.out.println("""sum  : Int = """ + $show(sum ));$skip(50); 
	val length = List(1,2,3).map(_ => 1).reduce(_+_);System.out.println("""length  : Int = """ + $show(length ));$skip(12); val res$24 = 
	sum/length;System.out.println("""res24: Int = """ + $show(res$24));$skip(194); 
	
	
	//Example 2a - compute average using reduction once
	val (sum2, length2) = List(1,2,3).map(a => (a, 1))
	                                 .reduce((a, b) => ((a._1 + b._1),  (a._2 + b._2)));System.out.println("""sum2  : Int = """ + $show(sum2 ));System.out.println("""length2  : Int = """ + $show(length2 ));$skip(16); val res$25 = 
	sum2 / length2;System.out.println("""res25: Int = """ + $show(res$25));$skip(126); 
	
	
	//Exampel 3 - addition of modular fractions
	def plus(x: (Int, Int), y: (Int, Int)) = (x._1*y._2 + x._2*y._1, y._1*y._2);System.out.println("""plus: (x: (Int, Int), y: (Int, Int))(Int, Int)""");$skip(109); 
	
	//Example 4 - relativitistic velocity (associative)
	def fRelInt(u: Int, v: Int): Int = (u + v)/(1 + u*v);System.out.println("""fRelInt: (u: Int, v: Int)Int""");$skip(88); 
	def errInt(lst:List[Int]): Int =
  	lst.reduceLeft(fRelInt) - lst.reduceRight(fRelInt);System.out.println("""errInt: (lst: List[Int])Int""");$skip(121); 
  
  def testAssocInt: Int = {
  	val r = new scala.util.Random
  	val lst = List.fill(400)(r.nextInt)
  	errInt(lst)
	};System.out.println("""testAssocInt: => Int""");$skip(14); val res$26 = 
	testAssocInt;System.out.println("""res26: Int = """ + $show(res$26));$skip(14); val res$27 = 
	testAssocInt;System.out.println("""res27: Int = """ + $show(res$27));$skip(14); val res$28 = 
	testAssocInt;System.out.println("""res28: Int = """ + $show(res$28));$skip(14); val res$29 = 
	testAssocInt;System.out.println("""res29: Int = """ + $show(res$29));$skip(14); val res$30 = 
	testAssocInt;System.out.println("""res30: Int = """ + $show(res$30));$skip(162); 
	
	
	//Example 4a - relativitistic velocity (not associative because Double is not associative)
	def fRelDoub(u: Double, v: Double): Double = (u + v)/(1.0 + u*v);System.out.println("""fRelDoub: (u: Double, v: Double)Double""");$skip(97); 
	def errDoub(lst:List[Double]): Double =
  	lst.reduceLeft(fRelDoub) - lst.reduceRight(fRelDoub);System.out.println("""errDoub: (lst: List[Double])Double""");$skip(135); 
  
  def testAssocDoub: Double = {
  	val r = new scala.util.Random
  	val lst = List.fill(400)(r.nextDouble*0.002)
  	errDoub(lst)
	};System.out.println("""testAssocDoub: => Double""");$skip(15); val res$31 = 
	testAssocDoub;System.out.println("""res31: Double = """ + $show(res$31));$skip(15); val res$32 = 
	testAssocDoub;System.out.println("""res32: Double = """ + $show(res$32));$skip(15); val res$33 = 
	testAssocDoub;System.out.println("""res33: Double = """ + $show(res$33));$skip(15); val res$34 = 
	testAssocDoub;System.out.println("""res34: Double = """ + $show(res$34))}

	
		
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
