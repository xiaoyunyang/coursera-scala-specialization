package week1

object collections {
  List(1,2,3).map(_ + 1)                          //> res0: List[Int] = List(2, 3, 4)
  List(1,2,3).flatMap(x => List(x,x))             //> res1: List[Int] = List(1, 1, 2, 2, 3, 3)
  List(1,2,3) zip List("a","b","c")               //> res2: List[(Int, String)] = List((1,a), (2,b), (3,c))
  List(1,2,3,4).partition(_ < 3)                  //> res3: (List[Int], List[Int]) = (List(1, 2),List(3, 4))
  List(1,2,3,4).filter(_ % 2 == 1)                //> res4: List[Int] = List(1, 3)
  
  //For-expressions or for comprehension - useful for when you want to do nested loops

  //instead of
  def isOdd(n: Int) = n % 2 == 1                  //> isOdd: (n: Int)Boolean
  val n = 10                                      //> n  : Int = 10
  (1 until n) flatMap(i =>
  	(1 until i) withFilter (j => isOdd(i + j)) map
  		(j => (i, j)))                    //> res5: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,2
                                                  //| ), (4,1), (4,3), (5,2), (5,4), (6,1), (6,3), (6,5), (7,2), (7,4), (7,6), (8,
                                                  //| 1), (8,3), (8,5), (8,7), (9,2), (9,4), (9,6), (9,8))
  //with for expression
  for {
  	i <- 1 until n //a generator
  	j <- 1 until i //another generator
  	if isOdd(i+j)
  } yield(i,j)                                    //> res6: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,2
                                                  //| ), (4,1), (4,3), (5,2), (5,4), (6,1), (6,3), (6,5), (7,2), (7,4), (7,6), (8,
                                                  //| 1), (8,3), (8,5), (8,7), (9,2), (9,4), (9,6), (9,8))

	/* Map, flatMap, and filter implemented using for expression
	 * but in reality, scala compiler translates for expressions in terms of
	 * map, flatMap, and a lazy variation of filter (withFilter)
	 */
	def mapFun[T, U](xs: List[T], f: T => U): List[U] =
		for(x <- xs) yield f(x)           //> mapFun: [T, U](xs: List[T], f: T => U)List[U]
	
	def flatMap[T, U](xs: List[T], f: T => Iterable[U]): List[U] =
		for(x <- xs; y <- f(x)) yield y   //> flatMap: [T, U](xs: List[T], f: T => Iterable[U])List[U]
	
	def filter[T](xs: List[T], p: T => Boolean): List[T] =
		for(x <- xs if p(x)) yield x      //> filter: [T](xs: List[T], p: T => Boolean)List[T]



}