package week2

object laziness {
  
  //Laziness means do things as late as possible and never do them twice
  
  //Memoization - storing the result of the first evaluation and re-using the
  //stored result instead of recomputing. This optimization is sound, since in
  //a purely functional language, an expression produces the same result each time
  //it is evaluated
  

  val f = (x: Int) => x+1                         //> f  : Int => Int = <function1>
  lazy val fLazy = (x: Int) => x+1                //> fLazy: => Int => Int
 
 	/** lazy val vs. val vs. def **/
 	//If you run the following program, "xzyz" gets printed as a side effect
 	def expr = {
 		val x = { print("x"); 1} //val gets instantiated when you first define it
 		lazy val y = { print("y"); 2 } //lazy val gets instantiated only the first time you use it
 		def z = { print("z"); 3 } //def gets instantiated everytime you use it
 		z + y + x + z + y + x
 	}                                         //> expr: => Int
 	
 	expr                                      //> xzyzres0: Int = 12

	/** Testing MyLazyStream **/
	def streamRange(lo: Int, hi: Int): MyLazyStream[Int] = {
		if(lo >= hi) MyLazyStream.empty
		else MyLazyStream.cons(lo, streamRange(lo + 1, hi))
	}                                         //> streamRange: (lo: Int, hi: Int)week2.MyLazyStream[Int]
	def isOdd = (x: Int) => x % 2 == 1        //> isOdd: => Int => Boolean
	(streamRange(1000, 10000) filter isOdd) apply 1
                                                  //> res1: Int = 1003
              
	/** Infinite Streams **/
	def from(n: Int): Stream[Int] = n #:: from(n+1)
                                                  //> from: (n: Int)Stream[Int]
	def fromList(n: Int): List[Int] = n :: fromList(n+1)
                                                  //> fromList: (n: Int)List[Int]
	from(1)                                   //> res2: Stream[Int] = Stream(1, ?)
	//The following gives a stack overflow
	//fromList(1)
	
	val nats = from(0)                        //> nats  : Stream[Int] = Stream(0, ?)
	val m4s = nats map (_ * 4)                //> m4s  : scala.collection.immutable.Stream[Int] = Stream(0, ?)
	(m4s take 10).toList                      //> res3: List[Int] = List(0, 4, 8, 12, 16, 20, 24, 28, 32, 36)


	/*
	 * The Sieve of Eratosthenes:
	 */
	//starts with all integers from 2, the first prime number. Eliminate all multiples of 2.
	//The first element of the resulting list is 3, a prime number.
	//Eliminate all multiples of 3
	//Iterate forever. At each step, the number in the list is a prime number and we eliminate
	//all its multiples
	def sieve(s: Stream[Int]): Stream[Int] =
		s.head #:: sieve(s.tail filter (_ % s.head != 0))
                                                  //> sieve: (s: Stream[Int])Stream[Int]

	val primes = sieve(from(2))               //> primes  : Stream[Int] = Stream(2, ?)
	primes.take(10).toList                    //> res4: List[Int] = List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)

	/*
	 * square root:
	 */
	def sqrtStream(x: Double): Stream[Double] = {
		def improve(guess: Double) = (guess + x / guess) / 2
		lazy val guesses: Stream[Double] = 1 #:: (guesses map improve) //this won't end up in an infinite recursion because tail is call-by-name
		guesses
	}                                         //> sqrtStream: (x: Double)Stream[Double]
	
	//A converging infinite sequence. converges to 2.0
	sqrtStream(4).take(10).toList             //> res5: List[Double] = List(1.0, 2.5, 2.05, 2.000609756097561, 2.000000092922
                                                  //| 2947, 2.000000000000002, 2.0, 2.0, 2.0, 2.0)
  def isGoodEnough(guess: Double, x: Double) =
  	math.abs((guess * guess - x) / x) < 0.0001//> isGoodEnough: (guess: Double, x: Double)Boolean

	sqrtStream(4).filter(isGoodEnough(_, 4)).take(10).toList
                                                  //> res6: List[Double] = List(2.0000000929222947, 2.000000000000002, 2.0, 2.0, 
                                                  //| 2.0, 2.0, 2.0, 2.0, 2.0, 2.0)
                                                  
  //Two ways to express the infinite stream of multiples of N. The first way is faster
  //because it does not generate unnecessary numbers which are filtered out later
  val N = 3                                       //> N  : Int = 3
  //val xs = from(1) map(_ * N) //res: 1->3, 2->6, 3->9, ...
  //val ys = from(1) filter(_ % N == 0) //res: 1->false, 2->false, 3->true, 4->false, 5->false, 6->true
                                                  
}