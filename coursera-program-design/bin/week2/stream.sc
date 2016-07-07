package week2

object stream {
	def isOdd = (x: Int) => x % 2 == 1        //> isOdd: => Int => Boolean
	
	//gives the second odd number
	//This is very inefficient because it constructs all prime numbers between
	//1000 and 10000 in a list, but only ever looks at the first two elements of that list
	((1000 to 10000) filter isOdd)(1)         //> res0: Int = 1003

	/** Stream **/
	//A better solution is to use Streams. Streams are similar to lists, but their tail
	//is evaluated only on demand
	
	//Four ways to produce Streams. Notice the tail is a "?". This means the tail is not yet
	//evaluated
	val xs = Stream.cons(2, Stream.cons(3, Stream.empty))
                                                  //> xs  : Stream.Cons[Int] = Stream(2, ?)
	Stream(1,2,3)                             //> res1: scala.collection.immutable.Stream[Int] = Stream(1, ?)
	(1 to 3).toStream                         //> res2: scala.collection.immutable.Stream[Int] = Stream(1, ?)
	1 #:: xs                                  //> res3: scala.collection.immutable.Stream[Int] = Stream(1, ?)

	//This is analogous to the list creation
	val xs2 = List(1,2,3)                     //> xs2  : List[Int] = List(1, 2, 3)
	(1 to 5)                                  //> res4: scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5)
	0 :: xs2                                  //> res5: List[Int] = List(0, 1, 2, 3)

	//The streamRange function is isomorphic to the listRnage function (i.e., same structure)
	def streamRange(lo: Int, hi: Int): Stream[Int] = {
		if(lo >= hi) Stream.empty
		else Stream.cons(lo, streamRange(lo + 1, hi))
		
	}                                         //> streamRange: (lo: Int, hi: Int)Stream[Int]
	
	def listRange(lo: Int, hi: Int): List[Int] = {
		if(lo >= hi) Nil
		else lo :: listRange(lo + 1, hi)
	}                                         //> listRange: (lo: Int, hi: Int)List[Int]
	
	listRange(1, 10)                          //> res6: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
	streamRange(1, 10)                        //> res7: Stream[Int] = Stream(1, ?)
	
	
	((1000 to 10000).toStream filter isOdd)(1)//> res8: Int = 1003
	
	/** Quiz **/
	def streamRange2(lo: Int, hi: Int): Stream[Int] = {
		print(lo+" ") //<- a side effect
		if(lo >= hi) Stream.empty
		else Stream.cons(lo, streamRange2(lo + 1, hi))
	}                                         //> streamRange2: (lo: Int, hi: Int)Stream[Int]
	
	//The following will not print out "1" because the tail of the stream is unevaluated so it will only
	//print out the head (i.e. 1)
	streamRange2(1, 10).take(3)               //> 1 res9: scala.collection.immutable.Stream[Int] = Stream(1, ?)
	
	//The following will print out "1 2 3" as side effect because we are forcing all the tails of the
	//Stream to be evaluated by making it toList
	streamRange2(1, 10).take(3).toList        //> 1 2 3 res10: List[Int] = List(1, 2, 3)
	
}