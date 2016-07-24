package week4
import week3.Abstractions._
object combiners {
	/** Inefficient Combiner */
  trait MyCombiner[A, Repr] extends MyBuilder[A, Repr] {
  	//combine must be efficient - i.e. O(log n + log m) time, where
  	//n and m are the sizes of two input combiners
  	def combine(that: MyCombiner[A, Repr]): MyCombiner[A, Repr]
		
		
		//This combine is not efficient. steps = n+m+n+m = 2(n+m) -> O(n+m)
		//We cannot implement an efficient combine operation for Arrays
		def combineArr(xs: Array[Int], ys: Array[Int]): Array[Int] = {
			val r = new Array[Int](xs.length + ys.length) //n+m
			Array.copy(xs, 0, r, 0, xs.length) //n
			Array.copy(ys, 0, r, xs.length, ys.length) //m
			r
		}
		
  };import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(709); 
  println(1)}
}
