package week6

/** For expressions handles nested sequences in combinatorial search problems */
object forExpressions {
	def isPrime(n: Int): Boolean = {
		(2 until n) forall (d => n % d != 0)
	}                                         //> isPrime: (n: Int)Boolean
	
  val n = 7                                       //> n  : Int = 7
  (1 until n) map (i =>
    (1 until i) map (j => (i, j))
  )                                               //> res0: scala.collection.immutable.IndexedSeq[scala.collection.immutable.Index
                                                  //| edSeq[(Int, Int)]] = Vector(Vector(), Vector((2,1)), Vector((3,1), (3,2)), V
                                                  //| ector((4,1), (4,2), (4,3)), Vector((5,1), (5,2), (5,3), (5,4)), Vector((6,1)
                                                  //| , (6,2), (6,3), (6,4), (6,5)))

	/** Flatten **/
  //we can combine all the sub-sequences using foldRight with ++ (the concatenation operation)
     
  val xss = Vector(Vector(1,2,3),Vector(4,5))     //> xss  : scala.collection.immutable.Vector[scala.collection.immutable.Vector[I
                                                  //| nt]] = Vector(Vector(1, 2, 3), Vector(4, 5))
  (xss foldRight Seq[Int]())(_ ++ _)              //> res1: Seq[Int] = Vector(1, 2, 3, 4, 5)
	xss.flatten                               //> res2: scala.collection.immutable.Vector[Int] = Vector(1, 2, 3, 4, 5)
	((1 until n) map (i =>
    (1 until i) map (j => (i, j))
  )).flatten                                      //> res3: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,1
                                                  //| ), (3,2), (4,1), (4,2), (4,3), (5,1), (5,2), (5,3), (5,4), (6,1), (6,2), (6,
                                                  //| 3), (6,4), (6,5))
  /** flatMap */
  //a flatmap is a map and a flatten
  (1 until n) flatMap (i =>
    (1 until i) map (j => (i, j))
  )                                               //> res4: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,1
                                                  //| ), (3,2), (4,1), (4,2), (4,3), (5,1), (5,2), (5,3), (5,4), (6,1), (6,2), (6,
                                                  //| 3), (6,4), (6,5))
  (1 until n) flatMap (i =>
    (1 until i) map (j => (i, j))
  ) filter (pair => isPrime(pair._1 + pair._2))   //> res5: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,2
                                                  //| ), (4,1), (4,3), (5,2), (6,1), (6,5))
	//It's fair to say the above expression makes most people's heads hurt
	
	/** For-Expression */
	case class Person(name: String, age: Int) {
		def apply(n: String, a: Int) = new Person(n, a)
	}
	val persons = List(Person("Cody", 3), Person("Harry", 18), Person("Mary", 25), Person("John", 32))
                                                  //> persons  : List[week6.forExpressions.Person] = List(Person(Cody,3), Person(
                                                  //| Harry,18), Person(Mary,25), Person(John,32))
                 
	persons filter (p => p.age > 20) map (p => p.name)
                                                  //> res6: List[String] = List(Mary, John)
  //equivalent for expression
	for(p <- persons if p.age > 20) yield p.name
                                                  //> res7: List[String] = List(Mary, John)
	
	/*
	  for ( s ) yield e
	  where s is a sequence of generators and filters, and e is an expression
	  whose value is returned by an iterator. A generator is in the form p <- e
		instead of ( s ), braces { s } can also be used, and then the sequence of generators and
		filters can be written on multiple lines without requiring semicolons
  */
  for {
  	i <- 1 until n
  	j <- 1 until i
  	if isPrime(i + j)
  } yield(i, j)                                   //> res8: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,
                                                  //| 2), (4,1), (4,3), (5,2), (6,1), (6,5))
  
  def scalarProduct(xs: List[Double], ys: List[Double]): Double = {
  	(for((x,y) <- xs zip ys) yield(x*y)).sum
  }                                               //> scalarProduct: (xs: List[Double], ys: List[Double])Double
}