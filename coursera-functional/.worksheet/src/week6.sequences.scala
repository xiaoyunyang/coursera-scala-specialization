package week6

/** Other Collections that are Sequences like List
	* Vector, Range, Array, String
	* For Vectors, the fundamental operation is index, and the fundamental operation for
 * List is head and tail
*/
object sequences {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(512); 
  /** Vectors */
  /* Vectors (O(log_32(N))
   * vectors - which offers a evenly balanced access patterns than Lists
	 * (which is biased toward the head of the element)
	 * Good for bulk operations that are parallelizable. e.g., filter, map, fold
	*/
	val nums = Vector(1,2,3,-88);System.out.println("""nums  : scala.collection.immutable.Vector[Int] = """ + $show(nums ));$skip(46); 
	val people = Vector("Bob", "James", "Peter");System.out.println("""people  : scala.collection.immutable.Vector[String] = """ + $show(people ));$skip(21); val res$0 = 
	
	//Cons
	0 +: nums;System.out.println("""res0: scala.collection.immutable.Vector[Int] = """ + $show(res$0));$skip(11); val res$1 = 
	nums :+ 0;System.out.println("""res1: scala.collection.immutable.Vector[Int] = """ + $show(res$1));$skip(19); val res$2 = 
 	0 :: nums.toList;System.out.println("""res2: List[Int] = """ + $show(res$2));$skip(17); val res$3 = 
  nums +: people;System.out.println("""res3: scala.collection.immutable.Vector[java.io.Serializable] = """ + $show(res$3));$skip(31); val res$4 = 
  nums.toList :: people.toList;System.out.println("""res4: List[java.io.Serializable] = """ + $show(res$4));$skip(24); val res$5 = 
  nums +: people.toList;System.out.println("""res5: List[java.io.Serializable] = """ + $show(res$5));$skip(24); val res$6 = 
  nums.toList +: people;System.out.println("""res6: scala.collection.immutable.Vector[java.io.Serializable] = """ + $show(res$6));$skip(75); 
	/** Array - a sequence from the java universe*/
	val xs = Array(1,2,3,44);System.out.println("""xs  : Array[Int] = """ + $show(xs ));$skip(14); val res$7 = 
	xs map (_*2);System.out.println("""res7: Array[Int] = """ + $show(res$7));$skip(74); 

	/** String - a sequence from the java universe*/
	val s = "Hello World";System.out.println("""s  : String = """ + $show(s ));$skip(27); val res$8 = 
	s filter (c => c.isUpper);System.out.println("""res8: String = """ + $show(res$8));$skip(53); 

	/** Range - a sequence*/
	val r: Range = 1 until 5;System.out.println("""r  : Range = """ + $show(r ));$skip(23); 
	val t: Range = 1 to 5;System.out.println("""t  : Range = """ + $show(t ));$skip(14); val res$9 = 
	1 to 10 by 3;System.out.println("""res9: scala.collection.immutable.Range = """ + $show(res$9));$skip(14); val res$10 = 
	6 to 1 by -2;System.out.println("""res10: scala.collection.immutable.Range = """ + $show(res$10));$skip(79); val res$11 = 
	
	//More sequence operations exists,
	//exists and forall
	nums exists (_==3);System.out.println("""res11: Boolean = """ + $show(res$11));$skip(19); val res$12 = 
	nums forall (_>0);System.out.println("""res12: Boolean = """ + $show(res$12));$skip(41); val res$13 = 
	
	"Hello World" exists (c => c.isUpper);System.out.println("""res13: Boolean = """ + $show(res$13));$skip(39); val res$14 = 
	"Hello World" forall (c => c.isUpper);System.out.println("""res14: Boolean = """ + $show(res$14));$skip(36); val res$15 = 
	
	//zip and unzip
	nums zip people;System.out.println("""res15: scala.collection.immutable.Vector[(Int, String)] = """ + $show(res$15));$skip(39); val res$16 = 
  Vector((1,"Bob"),(2, "Peter")).unzip;System.out.println("""res16: (scala.collection.immutable.Vector[Int], scala.collection.immutable.Vector[String]) = """ + $show(res$16));$skip(27); val res$17 = 
  //sum and max
  nums.sum;System.out.println("""res17: Int = """ + $show(res$17));$skip(11); val res$18 = 
  nums.max;System.out.println("""res18: Int = """ + $show(res$18));$skip(56); val res$19 = 
	
	//flatMap
	"Hello World" flatMap (c => List('.', c));System.out.println("""res19: String = """ + $show(res$19));$skip(115); 
	
	/** Combinations */
	def rangePairs(m: Int, n: Int) = {
		(1 to m) flatMap (x => (1 to n) map (y => (x, y)))
	};System.out.println("""rangePairs: (m: Int, n: Int)scala.collection.immutable.IndexedSeq[(Int, Int)]""");$skip(17); val res$20 = 
	rangePairs(3,2);System.out.println("""res20: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$20));$skip(117); 
	def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
		(xs zip ys).map(xy => xy._1 * xy._2).sum
	};System.out.println("""scalarProduct: (xs: Vector[Double], ys: Vector[Double])Double""");$skip(195); 
	//An alternative way to write this is with a pattern matching function value
	def scalarProduct2(xs: Vector[Double], ys: Vector[Double]): Double = {
		(xs zip ys).map{case (x,y) => x*y }.sum
	};System.out.println("""scalarProduct2: (xs: Vector[Double], ys: Vector[Double])Double""");$skip(209); 
	
	//value conciseness over efficiency. Express this mathematically
	//a number is prime if the only divisor of n are 1 and n itself
	def isPrime(n: Int): Boolean = {
		(2 until n) forall (d => n % d != 0)
	};System.out.println("""isPrime: (n: Int)Boolean""")}

}
