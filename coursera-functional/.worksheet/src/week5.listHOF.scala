package week5

/** Higher Order List Functions
 *  (1) unary operator
 *  		filter, filterNot and Partition (combination filter and filterNot)
 *  		takeWhile, dropWhile and span (combibnation takeWhile and dropWhile)
 *	(2) binary operator
 * 			reduce
 *			fold
 */
object listHOF {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(337); 

	//Data Definition
	val nums = List(2, -4, 5, 7, 1);System.out.println("""nums  : List[Int] = """ + $show(nums ));$skip(61); 
	val fruits = List("apple", "pineapple", "orange", "banana");System.out.println("""fruits  : List[String] = """ + $show(fruits ));$skip(175); 
 

	//a generic function
	def scaleList(xs: List[Double], factor: Double): List[Double] = xs match {
		case Nil => xs
		case y :: ys => y * factor :: scaleList(ys, factor)
	};System.out.println("""scaleList: (xs: List[Double], factor: Double)List[Double]""");$skip(168); 
	
	//a higher order function
	def myMap[T,U](l: List[T])(f: T => U): List[U] = l match {
		case Nil => Nil
		case x :: xs => f(x) :: xs.map(f) // not tail recursive
	};System.out.println("""myMap: [T, U](l: List[T])(f: T => U)List[U]""");$skip(132); 
	
	/** Map */
	def squareList(xs: List[Int]): List[Int] = xs match {
		case Nil => Nil
		case y :: ys => (y*y) :: squareList(ys)
	};System.out.println("""squareList: (xs: List[Int])List[Int]""");$skip(68); 
	def squareListViaMap(xs: List[Int]): List[Int] = xs map (x => x*x);System.out.println("""squareListViaMap: (xs: List[Int])List[Int]""");$skip(27); val res$0 = 
	
	squareList(List(1,2,3));System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(31); val res$1 = 
	squareListViaMap(List(1,2,3));System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(150); 

	/** Filter */
	def posElem(xs: List[Int]): List[Int] = xs match {
		case Nil => xs
		case y :: ys => if(y > 0) y :: posElem(ys) else posElem(ys)
	};System.out.println("""posElem: (xs: List[Int])List[Int]""");$skip(69); 
	
	def posElemViaFilter(xs: List[Int]): List[Int] = xs filter (_ >0);System.out.println("""posElemViaFilter: (xs: List[Int])List[Int]""");$skip(29); val res$2 = 
 	posElem(List(-2,-1,0,1,2));System.out.println("""res2: List[Int] = """ + $show(res$2));$skip(38); val res$3 = 
 	posElemViaFilter(List(-2,-1,0,1,2));System.out.println("""res3: List[Int] = """ + $show(res$3));$skip(47); val res$4 = 
 
	//variations of filter
	nums filter (_ > 0);System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(24); val res$5 = 
	nums filterNot (_ > 0);System.out.println("""res5: List[Int] = """ + $show(res$5));$skip(75); val res$6 = 
	 
	//partition is filter and filterNot in one go
	nums partition ( _ > 0);System.out.println("""res6: (List[Int], List[Int]) = """ + $show(res$6));$skip(110); val res$7 = 
	
	/** takeWhile */
	//takes the longest prefix of the last that satisfy the criteria
	nums takeWhile (_ > 0);System.out.println("""res7: List[Int] = """ + $show(res$7));$skip(34); val res$8 = 
	List(1,2,3,-4) takeWhile (_ > 0);System.out.println("""res8: List[Int] = """ + $show(res$8));$skip(26); val res$9 = 
	
	nums dropWhile (_ > 0);System.out.println("""res9: List[Int] = """ + $show(res$9));$skip(34); val res$10 = 
	List(1,2,3,-4) dropWhile (_ > 0);System.out.println("""res10: List[Int] = """ + $show(res$10));$skip(62); val res$11 = 
	
	//span combines takeWhile and dropWhile
	nums span (_ > 0);System.out.println("""res11: (List[Int], List[Int]) = """ + $show(res$11));$skip(243); 
	
	
	//A function that packs consecutive duplicates of list elements into sublists
	def pack[T](xs: List[T]): List[List[T]] = xs match {
		case Nil => Nil
		case x :: xs1 => {
			val part = xs span(_ == x)
			(part._1) :: pack(part._2)
		}
	};System.out.println("""pack: [T](xs: List[T])List[List[T]]""");$skip(54); 
	
	def data = List("a", "a", "a", "b", "c", "c", "a");System.out.println("""data: => List[String]""");$skip(12); val res$12 = 
	pack(data);System.out.println("""res12: List[List[String]] = """ + $show(res$12));$skip(242); 
  
  /* encode
   * uses pack to write encode that encodes n consecutive duplicates of an
   * element x as a pair (x, n)
   */
  def encode[T](xs: List[T]): List[(T, Int)] = {
  	val part = pack(xs)
  	part map (a => (a.head, a.length))
  };System.out.println("""encode: [T](xs: List[T])List[(T, Int)]""");$skip(15); val res$13 = 
  encode(data);System.out.println("""res13: List[(String, Int)] = """ + $show(res$13));$skip(39); val res$14 = 
	
	//gives the head of a list
	data(0);System.out.println("""res14: String = """ + $show(res$14));$skip(11); val res$15 = 
	data.head;System.out.println("""res15: String = """ + $show(res$15));$skip(145); 
	
	/** Reduce */
	//generic pattern matching solution
	def sum(xs: List[Int]): Int = xs match {
		case Nil => 0
		case y :: ys => y + sum(ys)
	};System.out.println("""sum: (xs: List[Int])Int""");$skip(79); 
	/** reduce */
	def sumViaReduce(xs: List[Int]) = (0 :: xs) reduceLeft (_ + _);System.out.println("""sumViaReduce: (xs: List[Int])Int""");$skip(68); 
	def productViaReduce(xs: List[Int]) = (1 :: xs) reduceLeft (_ * _);System.out.println("""productViaReduce: (xs: List[Int])Int""");$skip(179); 
	/** fold */
	//foldLeft is like reduceLeft but takes an accumulator, z, as an additional parameter
	//(starting element)
	def sumViaFold(xs: List[Int]) = (xs foldLeft 0) (_ + _);System.out.println("""sumViaFold: (xs: List[Int])Int""");$skip(61); 
	def productViaFold(xs: List[Int]) = (xs foldLeft 1) (_ * _);System.out.println("""productViaFold: (xs: List[Int])Int""");$skip(183); 
	
	//Practice with foldLeft and foldRight
	//foldRight is not right because there will be a type error
	def concat[T](xs: List[T], ys: List[T]): List[T] =
		(xs foldRight ys)(_ :: _);System.out.println("""concat: [T](xs: List[T], ys: List[T])List[T]""");$skip(102); 
	
	def mapFun[T, U](xs: List[T], f: T => U): List[U] =
		(xs foldRight List[U]())((a,b) => f(a) :: b);System.out.println("""mapFun: [T, U](xs: List[T], f: T => U)List[U]""");$skip(74); 
	def lengthFun[T](xs: List[T]): Int =
		(xs foldRight(0))((a,b) => 1 + b);System.out.println("""lengthFun: [T](xs: List[T])Int""");$skip(28); val res$16 = 
	lengthFun(List(1,2,3,4,5));System.out.println("""res16: Int = """ + $show(res$16));$skip(109); 
	
	
	/** Proof **/
	//proof of the following distribution law for map over concatenation
	val xs = List(1,2);System.out.println("""xs  : List[Int] = """ + $show(xs ));$skip(20); 
	val ys = List(2,3);System.out.println("""ys  : List[Int] = """ + $show(ys ));$skip(20); 
	val zs = List(3,4);System.out.println("""zs  : List[Int] = """ + $show(zs ));$skip(25); 
	def f = (a: Int) => a*a;System.out.println("""f: => Int => Int""");$skip(59); val res$17 = 
	
	/* Prove Associative law of concat */
	(xs ++ ys) ++ zs;System.out.println("""res17: List[Int] = """ + $show(res$17));$skip(18); val res$18 = 
	xs ++ (ys ++ zs);System.out.println("""res18: List[Int] = """ + $show(res$18));$skip(63); val res$19 = 
	
	/* Prove Distribution law of map */
	//base case
	Nil map f;System.out.println("""res19: List[Int] = """ + $show(res$19));$skip(38); val res$20 = 
	
	//induction step
	(xs ++ ys) map f;System.out.println("""res20: List[Int] = """ + $show(res$20));$skip(26); val res$21 = 
	(xs map f) ++ (ys map f);System.out.println("""res21: List[Int] = """ + $show(res$21))}
	
}
