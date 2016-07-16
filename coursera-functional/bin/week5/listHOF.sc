package week5

/** Higher Order List Functions
 *  (1) unary operator
 *  		filter, filterNot and Partition (combination filter and filterNot)
 *  		takeWhile, dropWhile and span (combibnation takeWhile and dropWhile)
 *	(2) binary operator
 * 			reduce
 *			fold
 */
object listHOF {

	//Data Definition
	val nums = List(2, -4, 5, 7, 1)           //> nums  : List[Int] = List(2, -4, 5, 7, 1)
	val fruits = List("apple", "pineapple", "orange", "banana")
                                                  //> fruits  : List[String] = List(apple, pineapple, orange, banana)
 

	//a generic function
	def scaleList(xs: List[Double], factor: Double): List[Double] = xs match {
		case Nil => xs
		case y :: ys => y * factor :: scaleList(ys, factor)
	}                                         //> scaleList: (xs: List[Double], factor: Double)List[Double]
	
	//a higher order function
	def myMap[T,U](l: List[T])(f: T => U): List[U] = l match {
		case Nil => Nil
		case x :: xs => f(x) :: xs.map(f) // not tail recursive
	}                                         //> myMap: [T, U](l: List[T])(f: T => U)List[U]
	
	/** Map */
	def squareList(xs: List[Int]): List[Int] = xs match {
		case Nil => Nil
		case y :: ys => (y*y) :: squareList(ys)
	}                                         //> squareList: (xs: List[Int])List[Int]
	def squareListViaMap(xs: List[Int]): List[Int] = xs map (x => x*x)
                                                  //> squareListViaMap: (xs: List[Int])List[Int]
	
	squareList(List(1,2,3))                   //> res0: List[Int] = List(1, 4, 9)
	squareListViaMap(List(1,2,3))             //> res1: List[Int] = List(1, 4, 9)

	/** Filter */
	def posElem(xs: List[Int]): List[Int] = xs match {
		case Nil => xs
		case y :: ys => if(y > 0) y :: posElem(ys) else posElem(ys)
	}                                         //> posElem: (xs: List[Int])List[Int]
	
	def posElemViaFilter(xs: List[Int]): List[Int] = xs filter (_ >0)
                                                  //> posElemViaFilter: (xs: List[Int])List[Int]
 	posElem(List(-2,-1,0,1,2))                //> res2: List[Int] = List(1, 2)
 	posElemViaFilter(List(-2,-1,0,1,2))       //> res3: List[Int] = List(1, 2)
 
	//variations of filter
	nums filter (_ > 0)                       //> res4: List[Int] = List(2, 5, 7, 1)
	nums filterNot (_ > 0)                    //> res5: List[Int] = List(-4)
	 
	//partition is filter and filterNot in one go
	nums partition ( _ > 0)                   //> res6: (List[Int], List[Int]) = (List(2, 5, 7, 1),List(-4))
	
	/** takeWhile */
	//takes the longest prefix of the last that satisfy the criteria
	nums takeWhile (_ > 0)                    //> res7: List[Int] = List(2)
	List(1,2,3,-4) takeWhile (_ > 0)          //> res8: List[Int] = List(1, 2, 3)
	
	nums dropWhile (_ > 0)                    //> res9: List[Int] = List(-4, 5, 7, 1)
	List(1,2,3,-4) dropWhile (_ > 0)          //> res10: List[Int] = List(-4)
	
	//span combines takeWhile and dropWhile
	nums span (_ > 0)                         //> res11: (List[Int], List[Int]) = (List(2),List(-4, 5, 7, 1))
	
	
	//A function that packs consecutive duplicates of list elements into sublists
	def pack[T](xs: List[T]): List[List[T]] = xs match {
		case Nil => Nil
		case x :: xs1 => {
			val part = xs span(_ == x)
			(part._1) :: pack(part._2)
		}
	}                                         //> pack: [T](xs: List[T])List[List[T]]
	
	def data = List("a", "a", "a", "b", "c", "c", "a")
                                                  //> data: => List[String]
	pack(data)                                //> res12: List[List[String]] = List(List(a, a, a), List(b), List(c, c), List(a
                                                  //| ))
  
  /* encode
   * uses pack to write encode that encodes n consecutive duplicates of an
   * element x as a pair (x, n)
   */
  def encode[T](xs: List[T]): List[(T, Int)] = {
  	val part = pack(xs)
  	part map (a => (a.head, a.length))
  }                                               //> encode: [T](xs: List[T])List[(T, Int)]
  encode(data)                                    //> res13: List[(String, Int)] = List((a,3), (b,1), (c,2), (a,1))
	
	//gives the head of a list
	data(0)                                   //> res14: String = a
	data.head                                 //> res15: String = a
	
	/** Reduce */
	//generic pattern matching solution
	def sum(xs: List[Int]): Int = xs match {
		case Nil => 0
		case y :: ys => y + sum(ys)
	}                                         //> sum: (xs: List[Int])Int
	/** reduce */
	def sumViaReduce(xs: List[Int]) = (0 :: xs) reduceLeft (_ + _)
                                                  //> sumViaReduce: (xs: List[Int])Int
	def productViaReduce(xs: List[Int]) = (1 :: xs) reduceLeft (_ * _)
                                                  //> productViaReduce: (xs: List[Int])Int
	/** fold */
	//foldLeft is like reduceLeft but takes an accumulator, z, as an additional parameter
	//(starting element)
	def sumViaFold(xs: List[Int]) = (xs foldLeft 0) (_ + _)
                                                  //> sumViaFold: (xs: List[Int])Int
	def productViaFold(xs: List[Int]) = (xs foldLeft 1) (_ * _)
                                                  //> productViaFold: (xs: List[Int])Int
	
	//Practice with foldLeft and foldRight
	//foldRight is not right because there will be a type error
	def concat[T](xs: List[T], ys: List[T]): List[T] =
		(xs foldRight ys)(_ :: _)         //> concat: [T](xs: List[T], ys: List[T])List[T]
	
	def mapFun[T, U](xs: List[T], f: T => U): List[U] =
		(xs foldRight List[U]())((a,b) => f(a) :: b)
                                                  //> mapFun: [T, U](xs: List[T], f: T => U)List[U]
	def lengthFun[T](xs: List[T]): Int =
		(xs foldRight(0))((a,b) => 1 + b) //> lengthFun: [T](xs: List[T])Int
	lengthFun(List(1,2,3,4,5))                //> res16: Int = 5
	
	
	/** Proof **/
	//proof of the following distribution law for map over concatenation
	val xs = List(1,2)                        //> xs  : List[Int] = List(1, 2)
	val ys = List(2,3)                        //> ys  : List[Int] = List(2, 3)
	val zs = List(3,4)                        //> zs  : List[Int] = List(3, 4)
	def f = (a: Int) => a*a                   //> f: => Int => Int
	
	/* Prove Associative law of concat */
	(xs ++ ys) ++ zs                          //> res17: List[Int] = List(1, 2, 2, 3, 3, 4)
	xs ++ (ys ++ zs)                          //> res18: List[Int] = List(1, 2, 2, 3, 3, 4)
	
	/* Prove Distribution law of map */
	//base case
	Nil map f                                 //> res19: List[Int] = List()
	
	//induction step
	(xs ++ ys) map f                          //> res20: List[Int] = List(1, 4, 4, 9)
	(xs map f) ++ (ys map f)                  //> res21: List[Int] = List(1, 4, 4, 9)
	
}