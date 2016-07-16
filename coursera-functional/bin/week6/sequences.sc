package week6

/** Other Collections that are Sequences like List
	* Vector, Range, Array, String
	* For Vectors, the fundamental operation is index, and the fundamental operation for
 * List is head and tail
*/
object sequences {
  /** Vectors */
  /* Vectors (O(log_32(N))
   * vectors - which offers a evenly balanced access patterns than Lists
	 * (which is biased toward the head of the element)
	 * Good for bulk operations that are parallelizable. e.g., filter, map, fold
	*/
	val nums = Vector(1,2,3,-88)              //> nums  : scala.collection.immutable.Vector[Int] = Vector(1, 2, 3, -88)
	val people = Vector("Bob", "James", "Peter")
                                                  //> people  : scala.collection.immutable.Vector[String] = Vector(Bob, James, Pet
                                                  //| er)
	
	//Cons
	0 +: nums                                 //> res0: scala.collection.immutable.Vector[Int] = Vector(0, 1, 2, 3, -88)
	nums :+ 0                                 //> res1: scala.collection.immutable.Vector[Int] = Vector(1, 2, 3, -88, 0)
 	0 :: nums.toList                          //> res2: List[Int] = List(0, 1, 2, 3, -88)
  nums +: people                                  //> res3: scala.collection.immutable.Vector[java.io.Serializable] = Vector(Vecto
                                                  //| r(1, 2, 3, -88), Bob, James, Peter)
  nums.toList :: people.toList                    //> res4: List[java.io.Serializable] = List(List(1, 2, 3, -88), Bob, James, Pete
                                                  //| r)
  nums +: people.toList                           //> res5: List[java.io.Serializable] = List(Vector(1, 2, 3, -88), Bob, James, Pe
                                                  //| ter)
  nums.toList +: people                           //> res6: scala.collection.immutable.Vector[java.io.Serializable] = Vector(List(
                                                  //| 1, 2, 3, -88), Bob, James, Peter)
	/** Array - a sequence from the java universe*/
	val xs = Array(1,2,3,44)                  //> xs  : Array[Int] = Array(1, 2, 3, 44)
	xs map (_*2)                              //> res7: Array[Int] = Array(2, 4, 6, 88)

	/** String - a sequence from the java universe*/
	val s = "Hello World"                     //> s  : String = Hello World
	s filter (c => c.isUpper)                 //> res8: String = HW

	/** Range - a sequence*/
	val r: Range = 1 until 5                  //> r  : Range = Range(1, 2, 3, 4)
	val t: Range = 1 to 5                     //> t  : Range = Range(1, 2, 3, 4, 5)
	1 to 10 by 3                              //> res9: scala.collection.immutable.Range = Range(1, 4, 7, 10)
	6 to 1 by -2                              //> res10: scala.collection.immutable.Range = Range(6, 4, 2)
	
	//More sequence operations exists,
	//exists and forall
	nums exists (_==3)                        //> res11: Boolean = true
	nums forall (_>0)                         //> res12: Boolean = false
	
	"Hello World" exists (c => c.isUpper)     //> res13: Boolean = true
	"Hello World" forall (c => c.isUpper)     //> res14: Boolean = false
	
	//zip and unzip
	nums zip people                           //> res15: scala.collection.immutable.Vector[(Int, String)] = Vector((1,Bob), (
                                                  //| 2,James), (3,Peter))
  Vector((1,"Bob"),(2, "Peter")).unzip            //> res16: (scala.collection.immutable.Vector[Int], scala.collection.immutable.
                                                  //| Vector[String]) = (Vector(1, 2),Vector(Bob, Peter))
  //sum and max
  nums.sum                                        //> res17: Int = -82
  nums.max                                        //> res18: Int = 3
	
	//flatMap
	"Hello World" flatMap (c => List('.', c)) //> res19: String = .H.e.l.l.o. .W.o.r.l.d
	
	/** Combinations */
	def rangePairs(m: Int, n: Int) = {
		(1 to m) flatMap (x => (1 to n) map (y => (x, y)))
	}                                         //> rangePairs: (m: Int, n: Int)scala.collection.immutable.IndexedSeq[(Int, Int
                                                  //| )]
	rangePairs(3,2)                           //> res20: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((1,1), (1
                                                  //| ,2), (2,1), (2,2), (3,1), (3,2))
	def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double = {
		(xs zip ys).map(xy => xy._1 * xy._2).sum
	}                                         //> scalarProduct: (xs: Vector[Double], ys: Vector[Double])Double
	//An alternative way to write this is with a pattern matching function value
	def scalarProduct2(xs: Vector[Double], ys: Vector[Double]): Double = {
		(xs zip ys).map{case (x,y) => x*y }.sum
	}                                         //> scalarProduct2: (xs: Vector[Double], ys: Vector[Double])Double
	
	//value conciseness over efficiency. Express this mathematically
	//a number is prime if the only divisor of n are 1 and n itself
	def isPrime(n: Int): Boolean = {
		(2 until n) forall (d => n % d != 0)
	}                                         //> isPrime: (n: Int)Boolean

}