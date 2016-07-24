package week3
import org.scalameter._
import scala.collection._
/** Data parallel definition */
object dataParallel {
  /** Parallel for-loop */
  //adding .par to the range will make the range parallel
  
  //Scala collections can be converted to parallel collections by
  //invoking the .par method. Subsequent data parallel operations
  //are executed in parallel
  //ParRange
  (1 until 4).par                                 //> res0: scala.collection.parallel.immutable.ParRange = ParRange(1, 2, 3)
  List(1,2,3).par                                 //> res1: scala.collection.parallel.immutable.ParSeq[Int] = ParVector(1, 2, 3)
  Set(1,2,3).par                                  //> res2: scala.collection.parallel.ParSet[Int] = ParSet(1, 2, 3)
  Map('I' -> 1, 'V' -> 5, 'C' -> 100).par         //> res3: scala.collection.parallel.ParMap[Char,Int] = ParMap(I -> 1, V -> 5, C 
                                                  //| -> 100)
  
  /*
   * initialize array example
   */
 	def initializeArray(xs: Array[Int])(v: Int): Unit = {
  	for(i <- (0 until xs.length).par) {
  		xs(i) = v //SIDE EFFECT
  	}
  }                                               //> initializeArray: (xs: Array[Int])(v: Int)Unit
  
  /*
   * operation on collections with data parallelelism
   */
  //a program that checks for palindrome
  (1 until 1000).par
  						  .filter(n => n % 3 == 0)
  						  .count(n => n.toString == n.toString.reverse)
                                                  //> res4: Int = 36
 
  /** fold vs. foldLeft
   * def fold(z: A)(f: A, A) => A): A
   * def foldLeft(z: A)(f: (B, A) => B): B
   * def fold(z: A)(op: (A,A) => A): A = foldLeft[A](z)(op)
   */
  //To ensure correctness, the binary operator f MUST be associative
  //f(a, f(b,c)) == f(f(a,b),c)
  //f(z,a) == f(a,z) == a
  //We say that the neutral element z and the binary operator f must form a monoid
  /*
   * sum
   */
  def sum(xs: Array[Int]): Int = {
  	xs.foldLeft(0)(_+_) //<- foldLeft cannot have data parallel
  }                                               //> sum: (xs: Array[Int])Int
  def parSum(xs: Array[Int]): Int = {
  	xs.par.fold(0)(_+_)
  }                                               //> parSum: (xs: Array[Int])Int
  parSum(Array(3,0,1,1))                          //> res5: Int = 5
 
 
 
 	withWarmer(new Warmer.Default) measure {
  	sum((0 until 1000000).toArray)            //> res6: Double = 19.59212
  }
  withWarmer(new Warmer.Default) measure {
  	parSum((0 until 1000000).toArray)         //> res7: Double = 13.550823
  }
  
  /*
   * max
   */
  def max(xs: Array[Int]): Int = {
  	//instead of math.max, you can use (x, y) => if(x > y) x else y
  	xs.par.fold(Int.MinValue)(math.max) //<- math.max is associative. This is important for fold
  }                                               //> max: (xs: Array[Int])Int
 	max(Array(0,-4, 12, 4))                   //> res8: Int = 12
  
  
  /*
   * rock paper scissors
   */
  
  def play(a: String, b: String): String = List(a, b).sorted match {
  	case List(x, "scissors") => {
  		if(x == "rock") "rock"
  		else "scissors"
  	}
  	case List("paper", "rock") => "paper"
  	case List(a, b) if a == b => a
  	case "" ::  y => b

  }                                               //> play: (a: String, b: String)String
  
  //The data parallel schedule is allowed to organize the reduction tree differently:
  //play(play("play", "rock"), play("paper", "scissors")) == "scissors"
  //play("play", play("rock", play("paper", "scissors"))) == "paper"
  
  Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
                                                  //> res9: String = scissors
  Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
                                                  //> res10: String = scissors
  Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
                                                  //> res11: String = scissors
  Array("paper", "rock", "pattern", "scissors").par.fold("")(play)
                                                  //> res12: String = scissors
  
  def isVowel(c: Char): Boolean =
  	List('a','e','i','o','u') exists (a => a == Character.toLowerCase(c))
                                                  //> isVowel: (c: Char)Boolean
  
  /* The following program does not compile
   * because the z is 0 and not a Char
   */
  /*
  Array('E', 'P', 'F', 'L').par.fold(0)((count, c) =>
  	if(isVowel(c)) count+1 else count)
  */
  
  //However, foldLeft compiles because for foldLeft, z element does not have to
  //be the same type as the elements in the array
  
  Array('E', 'P', 'F', 'L').foldLeft(0)((count, c) =>
  	if(isVowel(c)) count+1 else count)        //> res13: Int = 1
  
  
  
  "hellOWorldA".toList map (c => isVowel(c))      //> res14: List[Boolean] = List(false, true, false, false, true, false, true, f
                                                  //| alse, false, false, true)
  
  
  
  /** The aggregate Operation - more powerful than fold
    * def aggregate[B](z: B)(f: (B,A) => B, g: (B, B) => B): B
    * how it works is it divides the data into many smaller parts for mutiple
    * processors to compute via foldLeft. Then it combines back into a
    * form similar to fold
    */
  /* Use aggregate to count the number of vowels in an array */
  
  Array('E', 'P', 'F', 'L').par.aggregate(0)(
  	(count, c) => if(isVowel(c)) count + 1 else count,
  	_ + _
  )                                               //> res15: Int = 1
  
  
  
  /** Writing Parallelism-Agnostic Code
   * Generic collection trait allow us to write code that is
   * unaware of parallelism
   */
  def largestPalindrome(xs: GenSeq[Int]): Int = {
  	xs.aggregate(Int.MinValue)((largest, n) =>
  		if(n > largest && n.toString == n.toString.reverse) n else largest,
  		math.max
  	)
  }                                               //> largestPalindrome: (xs: scala.collection.GenSeq[Int])Int
  largestPalindrome((0 until 123).toArray)        //> res16: Int = 121
  
  
  
  
  
  
  //visualizing the Mandelbrot Set
  //render a set of complex numbers in a plane for which the sequence
  //Z_n+1 = Zn^2 + x does not approach infinity
  def computePixel(xc: Double, yc: Double, maxIterations: Int): Int = {
  	var i = 0
  	var x, y = 0.0
  	while(x*x+y*y < 4 && i < maxIterations) {
  		val xt = x*x - y*y + xc
  		val yt = 2*x*y + yc
  		x = xt
  		y = yt
  		i += 1
  	}
  	color(i)
  }                                               //> computePixel: (xc: Double, yc: Double, maxIterations: Int)Int
  
  def color(i: Int) = ???                         //> color: (i: Int)Nothing
  
}