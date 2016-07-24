package week3
import common._

/** Data Parallel Abstractions
 * iterators
 * splitters
 * builders
 * combiners
 */
object abstractions {
  /** Iterator
   * for doing something sequentially
   * each iterator has methods next and hasNex.
   * The iterator contract: next can only be called if hasNext
   * returns true
   */
  abstract trait MyIterator[A] {
  	def next(): A
  	def hasNext: Boolean
  	
  	def foldLeft[B](z: B)(f: (B,A) => B): B = {
  		var result = z
  	
  		while(hasNext) {
  			result = f(result, next())
  		}
  	 	result
  	}
  }
  
  /** Splitter
   * for splitting big task into many smaller children tasks
   * Each splitter has methods split and remaining.
   * The Splitter Contract:
   * after calling split, the original splitter is left in
   * an undefined state. The resulting splitter traverse disjoint
   * subsets of the original splitter. Remaining is an estimate on
   * the number of remaining elements. split is an efficient method
   * O(log n) or better.
   *
   */
  
  trait MySplitter[A] extends MyIterator[A] {
  	def split: Seq[MySplitter[A]]
  	def remaining: Int
  	val threshold = 200
  	
  	def fold(z: A)(f: (A, A) => A): A = {
  		if(remaining < threshold) foldLeft(z)(f)
  		else {
  		
  			//The child calls fold in parallel
  			val children = for {
  				child <- split
  			} yield task { child.fold(z)(f) }

  			children.map(_.join()).foldLeft(z)(f)
  		}
  	}
  }
  
  /** Builders
   * for creating new collections
   * The Builder Contract:
   * calling result returns a collection of type Repr, containing the
   * elements that were previously added with +=
   * calling result leaves the Builder in an undefined state
   */
  //Repr denotes the type of collection
  trait Repr {
  	def map: Repr
  }
  trait MyBuilder[A, Repr] {
  	def +=(elem: A): MyBuilder[A, Repr] //adds the items into the collection sequentially
  	def result: Repr //obtains the resulting sequence
  	
  	def newBuilder: MyBuilder[A, Repr] //for every collection
  	
  	def filter(p: A => Boolean): Repr = {
  		val b = newBuilder
  		???
  		/*
  		for {
  			x <- this
  			if p(x)
  		} yield( b += x )
  		*/
  		b.result
  	}
  	
  }
  
  /** Combiner
   * parallel counterpart of the Builder
   * The Combiner is a Builder, with an extra method called combine
   * Combiner Contract:
   * calling comine returns a new combiner that contains elements of
   * input combiners. Calling combine leaves both original Combiner
   * in an undefined state
   * combine is an efficient method - O(log n) or better
   */
  trait MyCombiner[A, Repr] extends MyBuilder[A, Repr] {
  	//combine must be efficient - i.e. O(log n + log m) time, where
  	//n and m are the sizes of two input combiners
  	def combine(that: MyCombiner[A, Repr]): MyCombiner[A, Repr]
		
		//This combine is not efficient. steps = n+m+n+m = 2(n+m) -> O(n+m)
		def combineA(xs: Array[Int], ys: Array[Int]): Array[Int] = {
			val r = new Array[Int](xs.length + ys.length) //n+m
			Array.copy(xs, 0, r, 0, xs.length) //n
			Array.copy(ys, 0, r, xs.length, ys.length) //m
			r
		}
		
		def filter(p: A => Boolean): Repr = {
			???
		}
  };import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(3160); 
	
  println(1)}

   
   
}
