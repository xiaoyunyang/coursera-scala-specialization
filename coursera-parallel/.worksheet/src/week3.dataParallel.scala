package week3
import org.scalameter._
import scala.collection._
/** Data parallel definition */
object dataParallel {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(397); val res$0 = 
  /** Parallel for-loop */
  //adding .par to the range will make the range parallel
  
  //Scala collections can be converted to parallel collections by
  //invoking the .par method. Subsequent data parallel operations
  //are executed in parallel
  //ParRange
  (1 until 4).par;System.out.println("""res0: scala.collection.parallel.immutable.ParRange = """ + $show(res$0));$skip(18); val res$1 = 
  List(1,2,3).par;System.out.println("""res1: scala.collection.parallel.immutable.ParSeq[Int] = """ + $show(res$1));$skip(17); val res$2 = 
  Set(1,2,3).par;System.out.println("""res2: scala.collection.parallel.ParSet[Int] = """ + $show(res$2));$skip(42); val res$3 = 
  Map('I' -> 1, 'V' -> 5, 'C' -> 100).par;System.out.println("""res3: scala.collection.parallel.ParMap[Char,Int] = """ + $show(res$3));$skip(176); 
  
  /*
   * initialize array example
   */
 	def initializeArray(xs: Array[Int])(v: Int): Unit = {
  	for(i <- (0 until xs.length).par) {
  		xs(i) = v //SIDE EFFECT
  	}
  };System.out.println("""initializeArray: (xs: Array[Int])(v: Int)Unit""");$skip(221); val res$4 = 
  
  /*
   * operation on collections with data parallelelism
   */
  //a program that checks for palindrome
  (1 until 1000).par
  						  .filter(n => n % 3 == 0)
  						  .count(n => n.toString == n.toString.reverse);System.out.println("""res4: Int = """ + $show(res$4));$skip(505); 
 
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
  };System.out.println("""sum: (xs: Array[Int])Int""");$skip(65); 
  def parSum(xs: Array[Int]): Int = {
  	xs.par.fold(0)(_+_)
  };System.out.println("""parSum: (xs: Array[Int])Int""");$skip(25); val res$5 = 
  parSum(Array(3,0,1,1));System.out.println("""res5: Int = """ + $show(res$5));$skip(83); val res$6 = 
 
 
 
 	withWarmer(new Warmer.Default) measure {
  	sum((0 until 1000000).toArray)
  };System.out.println("""res6: Double = """ + $show(res$6));$skip(84); val res$7 = 
  withWarmer(new Warmer.Default) measure {
  	parSum((0 until 1000000).toArray)
  };System.out.println("""res7: Double = """ + $show(res$7));$skip(229); 
  
  /*
   * max
   */
  def max(xs: Array[Int]): Int = {
  	//instead of math.max, you can use (x, y) => if(x > y) x else y
  	xs.par.fold(Int.MinValue)(math.max) //<- math.max is associative. This is important for fold
  };System.out.println("""max: (xs: Array[Int])Int""");$skip(26); val res$8 = 
 	max(Array(0,-4, 12, 4));System.out.println("""res8: Int = """ + $show(res$8));$skip(301); 
  
  
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

  };System.out.println("""play: (a: String, b: String)String""");$skip(300); val res$9 = 
  
  //The data parallel schedule is allowed to organize the reduction tree differently:
  //play(play("play", "rock"), play("paper", "scissors")) == "scissors"
  //play("play", play("rock", play("paper", "scissors"))) == "paper"
  
  Array("paper", "rock", "pattern", "scissors").par.fold("")(play);System.out.println("""res9: String = """ + $show(res$9));$skip(67); val res$10 = 
  Array("paper", "rock", "pattern", "scissors").par.fold("")(play);System.out.println("""res10: String = """ + $show(res$10));$skip(67); val res$11 = 
  Array("paper", "rock", "pattern", "scissors").par.fold("")(play);System.out.println("""res11: String = """ + $show(res$11));$skip(67); val res$12 = 
  Array("paper", "rock", "pattern", "scissors").par.fold("")(play);System.out.println("""res12: String = """ + $show(res$12));$skip(110); 
  
  def isVowel(c: Char): Boolean =
  	List('a','e','i','o','u') exists (a => a == Character.toLowerCase(c));System.out.println("""isVowel: (c: Char)Boolean""");$skip(422); val res$13 = 
  
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
  	if(isVowel(c)) count+1 else count);System.out.println("""res13: Int = """ + $show(res$13));$skip(54); val res$14 = 
  
  
  
  "hellOWorldA".toList map (c => isVowel(c));System.out.println("""res14: List[Boolean] = """ + $show(res$14));$skip(491); val res$15 = 
  
  
  
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
  );System.out.println("""res15: Int = """ + $show(res$15));$skip(334); 
  
  
  
  /** Writing Parallelism-Agnostic Code
   * Generic collection trait allow us to write code that is
   * unaware of parallelism
   */
  def largestPalindrome(xs: GenSeq[Int]): Int = {
  	xs.aggregate(Int.MinValue)((largest, n) =>
  		if(n > largest && n.toString == n.toString.reverse) n else largest,
  		math.max
  	)
  };System.out.println("""largestPalindrome: (xs: scala.collection.GenSeq[Int])Int""");$skip(43); val res$16 = 
  largestPalindrome((0 until 123).toArray);System.out.println("""res16: Int = """ + $show(res$16));$skip(425); 
  
  
  
  
  
  
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
  };System.out.println("""computePixel: (xc: Double, yc: Double, maxIterations: Int)Int""");$skip(29); 
  
  def color(i: Int) = ???;System.out.println("""color: (i: Int)Nothing""")}
  
}
