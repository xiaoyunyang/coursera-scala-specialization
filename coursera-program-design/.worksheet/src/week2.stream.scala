package week2

object stream {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(66); 
	def isOdd = (x: Int) => x % 2 == 1;System.out.println("""isOdd: => Int => Boolean""");$skip(232); val res$0 = 
	
	//gives the second odd number
	//This is very inefficient because it constructs all prime numbers between
	//1000 and 10000 in a list, but only ever looks at the first two elements of that list
	((1000 to 10000) filter isOdd)(1);System.out.println("""res0: Int = """ + $show(res$0));$skip(293); 

	/** Stream **/
	//A better solution is to use Streams. Streams are similar to lists, but their tail
	//is evaluated only on demand
	
	//Four ways to produce Streams. Notice the tail is a "?". This means the tail is not yet
	//evaluated
	val xs = Stream.cons(2, Stream.cons(3, Stream.empty));System.out.println("""xs  : Stream.Cons[Int] = """ + $show(xs ));$skip(15); val res$1 = 
	Stream(1,2,3);System.out.println("""res1: scala.collection.immutable.Stream[Int] = """ + $show(res$1));$skip(19); val res$2 = 
	(1 to 3).toStream;System.out.println("""res2: scala.collection.immutable.Stream[Int] = """ + $show(res$2));$skip(10); val res$3 = 
	1 #:: xs;System.out.println("""res3: scala.collection.immutable.Stream[Int] = """ + $show(res$3));$skip(66); 

	//This is analogous to the list creation
	val xs2 = List(1,2,3);System.out.println("""xs2  : List[Int] = """ + $show(xs2 ));$skip(10); val res$4 = 
	(1 to 5);System.out.println("""res4: scala.collection.immutable.Range.Inclusive = """ + $show(res$4));$skip(10); val res$5 = 
	0 :: xs2;System.out.println("""res5: List[Int] = """ + $show(res$5));$skip(226); 

	//The streamRange function is isomorphic to the listRnage function (i.e., same structure)
	def streamRange(lo: Int, hi: Int): Stream[Int] = {
		if(lo >= hi) Stream.empty
		else Stream.cons(lo, streamRange(lo + 1, hi))
		
	};System.out.println("""streamRange: (lo: Int, hi: Int)Stream[Int]""");$skip(107); 
	
	def listRange(lo: Int, hi: Int): List[Int] = {
		if(lo >= hi) Nil
		else lo :: listRange(lo + 1, hi)
	};System.out.println("""listRange: (lo: Int, hi: Int)List[Int]""");$skip(20); val res$6 = 
	
	listRange(1, 10);System.out.println("""res6: List[Int] = """ + $show(res$6));$skip(20); val res$7 = 
	streamRange(1, 10);System.out.println("""res7: Stream[Int] = """ + $show(res$7));$skip(48); val res$8 = 
	
	
	((1000 to 10000).toStream filter isOdd)(1);System.out.println("""res8: Int = """ + $show(res$8));$skip(184); 
	
	/** Quiz **/
	def streamRange2(lo: Int, hi: Int): Stream[Int] = {
		print(lo+" ") //<- a side effect
		if(lo >= hi) Stream.empty
		else Stream.cons(lo, streamRange2(lo + 1, hi))
	};System.out.println("""streamRange2: (lo: Int, hi: Int)Stream[Int]""");$skip(164); val res$9 = 
	
	//The following will not print out "1" because the tail of the stream is unevaluated so it will only
	//print out the head (i.e. 1)
	streamRange2(1, 10).take(3);System.out.println("""res9: scala.collection.immutable.Stream[Int] = """ + $show(res$9));$skip(183); val res$10 = 
	
	//The following will print out "1 2 3" as side effect because we are forcing all the tails of the
	//Stream to be evaluated by making it toList
	streamRange2(1, 10).take(3).toList;System.out.println("""res10: List[Int] = """ + $show(res$10))}
	
}
