package week5
import math.Ordering

/** Demonstrates Pairs and Tuples and implicit parameters */
object mergesort {
  def msort(xs: List[Int]): List[Int] = {
  	val n = xs.length/2
  	if(n == 0) xs
  	else {
  		val (fst, snd) = xs splitAt n
  		merge2(msort(fst), msort(snd))
  	}
  }                                               //> msort: (xs: List[Int])List[Int]
  def merge(xs: List[Int], ys: List[Int]): List[Int] = xs match {
  	case Nil => ys
  	case x :: xs1 => ys match{
  		case Nil => xs
  		case y :: ys1 => {
  			if(x < y) x :: merge(xs1, ys)
  			else y :: merge(xs, ys1)
  		}
  	}
  }                                               //> merge: (xs: List[Int], ys: List[Int])List[Int]
  //Merge over pairs
	def merge2(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
  	case (Nil, ys) => ys
  	case (xs, Nil) => xs
  	case (x :: xs1, y :: ys1) => {
  		if(x < y) x :: merge2(xs1, ys)
  		else y :: merge2(xs, ys1)
  	}
  }                                               //> merge2: (xs: List[Int], ys: List[Int])List[Int]
	val a = List(1,3,4)                       //> a  : List[Int] = List(1, 3, 4)
	val b = List(0, 2, 8)                     //> b  : List[Int] = List(0, 2, 8)

	merge(a ,b)                               //> res0: List[Int] = List(0, 1, 2, 3, 4, 8)
	merge2(a, b)                              //> res1: List[Int] = List(0, 1, 2, 3, 4, 8)

	/** Pair and Tuples */
	val pair = ("answer", 42)                 //> pair  : (String, Int) = (answer,42)
	
	//pattern matching form - preferred because it's shorter and clearer
	val (label, value1, value2) = (pair._1, pair._2, 80) //using pattern matching
                                                  //> label  : String = answer
                                                  //| value1  : Int = 42
                                                  //| value2  : Int = 80
 	pair._1 //gives label                     //> res2: String = answer
 	pair._2 //gives value1                    //> res3: Int = 42


	val nums = List(2, -4, 5, 7, 1)           //> nums  : List[Int] = List(2, -4, 5, 7, 1)
	val fruits = List("apple", "pineapple", "orange", "banana")
                                                  //> fruits  : List[String] = List(apple, pineapple, orange, banana)
	msort(nums)                               //> res4: List[Int] = List(-4, 1, 2, 5, 7)

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
  }                                               //> msortPara: [T](xs: List[T])(lt: (T, T) => Boolean)List[T]
	
	//sort in lexicographical order
  msortPara(fruits)((x: String, y: String) => x.compareTo(y) < 0)
                                                  //> res5: List[String] = List(apple, banana, orange, pineapple)

  msortPara(nums)((x: Int, y: Int) => x < y)      //> res6: List[Int] = List(-4, 1, 2, 5, 7)
  
  //Scala can figure out that x and y are Int by type inference.
  msortPara(nums)((x, y) => x < y)                //> res7: List[Int] = List(-4, 1, 2, 5, 7)
  
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
  }                                               //> msortStandardPara: [T](xs: List[T])(ord: scala.math.Ordering[T])List[T]
  msortStandardPara(nums)(Ordering.Int)           //> res8: List[Int] = List(-4, 1, 2, 5, 7)
  msortStandardPara(fruits)(Ordering.String)      //> res9: List[String] = List(apple, banana, orange, pineapple)
  
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
  }                                               //> msortStandardImplicitPara: [T](xs: List[T])(implicit ord: scala.math.Orderi
                                                  //| ng[T])List[T]
  
  msortStandardImplicitPara(nums)                 //> res10: List[Int] = List(-4, 1, 2, 5, 7)
  msortStandardImplicitPara(fruits)               //> res11: List[String] = List(apple, banana, orange, pineapple)
  
  
  
}