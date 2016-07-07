package week2

object laziness {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(401); 
  
  //Laziness means do things as late as possible and never do them twice
  
  //Memoization - storing the result of the first evaluation and re-using the
  //stored result instead of recomputing. This optimization is sound, since in
  //a purely functional language, an expression produces the same result each time
  //it is evaluated
  

  val f = (x: Int) => x+1;System.out.println("""f  : Int => Int = """ + $show(f ));$skip(35); 
  lazy val fLazy = (x: Int) => x+1;System.out.println("""fLazy: => Int => Int""");$skip(401); 
 
 	/** lazy val vs. val vs. def **/
 	//If you run the following program, "xzyz" gets printed as a side effect
 	def expr = {
 		val x = { print("x"); 1} //val gets instantiated when you first define it
 		lazy val y = { print("y"); 2 } //lazy val gets instantiated only the first time you use it
 		def z = { print("z"); 3 } //def gets instantiated everytime you use it
 		z + y + x + z + y + x
 	};System.out.println("""expr: => Int""");$skip(10); val res$0 = 
 	
 	expr;System.out.println("""res0: Int = """ + $show(res$0));$skip(180); 

	/** Testing MyLazyStream **/
	def streamRange(lo: Int, hi: Int): MyLazyStream[Int] = {
		if(lo >= hi) MyLazyStream.empty
		else MyLazyStream.cons(lo, streamRange(lo + 1, hi))
	};System.out.println("""streamRange: (lo: Int, hi: Int)week2.MyLazyStream[Int]""");$skip(36); 
	def isOdd = (x: Int) => x % 2 == 1;System.out.println("""isOdd: => Int => Boolean""");$skip(49); val res$1 = 
	(streamRange(1000, 10000) filter isOdd) apply 1;System.out.println("""res1: Int = """ + $show(res$1));$skip(90); 
              
	/** Infinite Streams **/
	def from(n: Int): Stream[Int] = n #:: from(n+1);System.out.println("""from: (n: Int)Stream[Int]""");$skip(54); 
	def fromList(n: Int): List[Int] = n :: fromList(n+1);System.out.println("""fromList: (n: Int)List[Int]""");$skip(9); val res$2 = 
	from(1);System.out.println("""res2: Stream[Int] = """ + $show(res$2));$skip(77); 
	//The following gives a stack overflow
	//fromList(1)
	
	val nats = from(0);System.out.println("""nats  : Stream[Int] = """ + $show(nats ));$skip(28); 
	val m4s = nats map (_ * 4);System.out.println("""m4s  : scala.collection.immutable.Stream[Int] = """ + $show(m4s ));$skip(22); val res$3 = 
	(m4s take 10).toList;System.out.println("""res3: List[Int] = """ + $show(res$3));$skip(436); 


	/*
	 * The Sieve of Eratosthenes:
	 */
	//starts with all integers from 2, the first prime number. Eliminate all multiples of 2.
	//The first element of the resulting list is 3, a prime number.
	//Eliminate all multiples of 3
	//Iterate forever. At each step, the number in the list is a prime number and we eliminate
	//all its multiples
	def sieve(s: Stream[Int]): Stream[Int] =
		s.head #:: sieve(s.tail filter (_ % s.head != 0));System.out.println("""sieve: (s: Stream[Int])Stream[Int]""");$skip(30); 

	val primes = sieve(from(2));System.out.println("""primes  : Stream[Int] = """ + $show(primes ));$skip(24); val res$4 = 
	primes.take(10).toList;System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(281); 

	/*
	 * square root:
	 */
	def sqrtStream(x: Double): Stream[Double] = {
		def improve(guess: Double) = (guess + x / guess) / 2
		lazy val guesses: Stream[Double] = 1 #:: (guesses map improve) //this won't end up in an infinite recursion because tail is call-by-name
		guesses
	};System.out.println("""sqrtStream: (x: Double)Stream[Double]""");$skip(85); val res$5 = 
	
	//A converging infinite sequence. converges to 2.0
	sqrtStream(4).take(10).toList;System.out.println("""res5: List[Double] = """ + $show(res$5));$skip(93); 
  def isGoodEnough(guess: Double, x: Double) =
  	math.abs((guess * guess - x) / x) < 0.0001;System.out.println("""isGoodEnough: (guess: Double, x: Double)Boolean""");$skip(59); val res$6 = 

	sqrtStream(4).filter(isGoodEnough(_, 4)).take(10).toList;System.out.println("""res6: List[Double] = """ + $show(res$6));$skip(232); 
                                                  
  //Two ways to express the infinite stream of multiples of N. The first way is faster
  //because it does not generate unnecessary numbers which are filtered out later
  val N = 3;System.out.println("""N  : Int = """ + $show(N ))}
  //val xs = from(1) map(_ * N) //res: 1->3, 2->6, 3->9, ...
  //val ys = from(1) filter(_ % N == 0) //res: 1->false, 2->false, 3->true, 4->false, 5->false, 6->true
                                                  
}
