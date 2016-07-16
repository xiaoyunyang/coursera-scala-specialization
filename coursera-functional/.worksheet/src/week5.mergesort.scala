package week5
import math.Ordering

/** Demonstrates Pairs and Tuples and implicit parameters */
object mergesort {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(285); 
  def msort(xs: List[Int]): List[Int] = {
  	val n = xs.length/2
  	if(n == 0) xs
  	else {
  		val (fst, snd) = xs splitAt n
  		merge2(msort(fst), msort(snd))
  	}
  };System.out.println("""msort: (xs: List[Int])List[Int]""");$skip(236); 
  def merge(xs: List[Int], ys: List[Int]): List[Int] = xs match {
  	case Nil => ys
  	case x :: xs1 => ys match{
  		case Nil => xs
  		case y :: ys1 => {
  			if(x < y) x :: merge(xs1, ys)
  			else y :: merge(xs, ys1)
  		}
  	}
  };System.out.println("""merge: (xs: List[Int], ys: List[Int])List[Int]""");$skip(249); 
  //Merge over pairs
	def merge2(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
  	case (Nil, ys) => ys
  	case (xs, Nil) => xs
  	case (x :: xs1, y :: ys1) => {
  		if(x < y) x :: merge2(xs1, ys)
  		else y :: merge2(xs, ys1)
  	}
  };System.out.println("""merge2: (xs: List[Int], ys: List[Int])List[Int]""");$skip(21); 
	val a = List(1,3,4);System.out.println("""a  : List[Int] = """ + $show(a ));$skip(23); 
	val b = List(0, 2, 8);System.out.println("""b  : List[Int] = """ + $show(b ));$skip(14); val res$0 = 

	merge(a ,b);System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(14); val res$1 = 
	merge2(a, b);System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(52); 

	/** Pair and Tuples */
	val pair = ("answer", 42);System.out.println("""pair  : (String, Int) = """ + $show(pair ));$skip(151); 
	
	//pattern matching form - preferred because it's shorter and clearer
	val (label, value1, value2) = (pair._1, pair._2, 80);System.out.println("""label  : String = """ + $show(label ));System.out.println("""value1  : Int = """ + $show(value1 ));System.out.println("""value2  : Int = """ + $show(value2 ));$skip(24); val res$2 =  //using pattern matching
 	pair._1;System.out.println("""res2: String = """ + $show(res$2));$skip(25); val res$3 =  //gives label
 	pair._2;System.out.println("""res3: Int = """ + $show(res$3));$skip(35);  //gives value1


	val nums = List(2, -4, 5, 7, 1);System.out.println("""nums  : List[Int] = """ + $show(nums ));$skip(61); 
	val fruits = List("apple", "pineapple", "orange", "banana");System.out.println("""fruits  : List[String] = """ + $show(fruits ));$skip(13); val res$4 = 
	msort(nums);System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(477); 

	/** Polymorphic msort */
	def msortPara[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
  	val n = xs.length/2
  	if(n == 0) xs
  	else {
  	  def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
		  	case (Nil, ys) => ys
		  	case (xs, Nil) => xs
		  	case (x :: xs1, y :: ys1) => {
		  		if(lt(x, y)) x :: merge(xs1, ys)
		  		else y :: merge(xs, ys1)
		  	}
		  }
  		val (fst, snd) = xs splitAt n
  		merge(msortPara(fst)(lt), msortPara(snd)(lt))
  	}
  };System.out.println("""msortPara: [T](xs: List[T])(lt: (T, T) => Boolean)List[T]""");$skip(101); val res$5 = 
	
	//sort in lexicographical order
  msortPara(fruits)((x: String, y: String) => x.compareTo(y) < 0);System.out.println("""res5: List[String] = """ + $show(res$5));$skip(46); val res$6 = 

  msortPara(nums)((x: Int, y: Int) => x < y);System.out.println("""res6: List[Int] = """ + $show(res$6));$skip(103); val res$7 = 
  
  //Scala can figure out that x and y are Int by type inference.
  msortPara(nums)((x, y) => x < y);System.out.println("""res7: List[Int] = """ + $show(res$7));$skip(795); 
  
  /** Implicit parameter of type T */
  /* There is alreadya a class in the standard library (scala.math.Ordering[T] that
  	 represents prdering. This provides ways to compare elements of type T. So instead of
  	 parameterizing with the lt operation directly, we could parameterize with Ordering
  	 instead
  */
  	def msortStandardPara[T](xs: List[T])(ord: Ordering[T]): List[T] = {
  	val n = xs.length/2
  	if(n == 0) xs
  	else {
  	  def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
		  	case (Nil, ys) => ys
		  	case (xs, Nil) => xs
		  	case (x :: xs1, y :: ys1) => {
		  		if(ord.lt(x, y)) x :: merge(xs1, ys)
		  		else y :: merge(xs, ys1)
		  	}
		  }
  		val (fst, snd) = xs splitAt n
  		merge(msortStandardPara(fst)(ord), msortStandardPara(snd)(ord))
  	}
  };System.out.println("""msortStandardPara: [T](xs: List[T])(ord: scala.math.Ordering[T])List[T]""");$skip(40); val res$8 = 
  msortStandardPara(nums)(Ordering.Int);System.out.println("""res8: List[Int] = """ + $show(res$8));$skip(45); val res$9 = 
  msortStandardPara(fruits)(Ordering.String);System.out.println("""res9: List[String] = """ + $show(res$9));$skip(531); 
  
  /** Implicit parameter T */
	def msortStandardImplicitPara[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
  	val n = xs.length/2
  	if(n == 0) xs
  	else {
  	  def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
		  	case (Nil, ys) => ys
		  	case (xs, Nil) => xs
		  	case (x :: xs1, y :: ys1) => {
		  		if(ord.lt(x, y)) x :: merge(xs1, ys)
		  		else y :: merge(xs, ys1)
		  	}
		  }
  		val (fst, snd) = xs splitAt n
  		merge(msortStandardImplicitPara(fst), msortStandardImplicitPara(snd))
  	}
  };System.out.println("""msortStandardImplicitPara: [T](xs: List[T])(implicit ord: scala.math.Ordering[T])List[T]""");$skip(37); val res$10 = 
  
  msortStandardImplicitPara(nums);System.out.println("""res10: List[Int] = """ + $show(res$10));$skip(36); val res$11 = 
  msortStandardImplicitPara(fruits);System.out.println("""res11: List[String] = """ + $show(res$11))}
  
  
  
}
