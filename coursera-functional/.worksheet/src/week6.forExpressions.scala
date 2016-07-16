package week6

/** For expressions handles nested sequences in combinatorial search problems */
object forExpressions {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(195); 
	def isPrime(n: Int): Boolean = {
		(2 until n) forall (d => n % d != 0)
	};System.out.println("""isPrime: (n: Int)Boolean""");$skip(14); 
	
  val n = 7;System.out.println("""n  : Int = """ + $show(n ));$skip(62); val res$0 = 
  (1 until n) map (i =>
    (1 until i) map (j => (i, j))
  );System.out.println("""res0: scala.collection.immutable.IndexedSeq[scala.collection.immutable.IndexedSeq[(Int, Int)]] = """ + $show(res$0));$skip(165); 

	/** Flatten **/
  //we can combine all the sub-sequences using foldRight with ++ (the concatenation operation)
     
  val xss = Vector(Vector(1,2,3),Vector(4,5));System.out.println("""xss  : scala.collection.immutable.Vector[scala.collection.immutable.Vector[Int]] = """ + $show(xss ));$skip(37); val res$1 = 
  (xss foldRight Seq[Int]())(_ ++ _);System.out.println("""res1: Seq[Int] = """ + $show(res$1));$skip(13); val res$2 = 
	xss.flatten;System.out.println("""res2: scala.collection.immutable.Vector[Int] = """ + $show(res$2));$skip(71); val res$3 = 
	((1 until n) map (i =>
    (1 until i) map (j => (i, j))
  )).flatten;System.out.println("""res3: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$3));$skip(120); val res$4 = 
  /** flatMap */
  //a flatmap is a map and a flatten
  (1 until n) flatMap (i =>
    (1 until i) map (j => (i, j))
  );System.out.println("""res4: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$4));$skip(110); val res$5 = 
  (1 until n) flatMap (i =>
    (1 until i) map (j => (i, j))
  ) filter (pair => isPrime(pair._1 + pair._2))
	//It's fair to say the above expression makes most people's heads hurt
	
	/** For-Expression */
	case class Person(name: String, age: Int) {
		def apply(n: String, a: Int) = new Person(n, a)
	};System.out.println("""res5: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$5));$skip(295); 
	val persons = List(Person("Cody", 3), Person("Harry", 18), Person("Mary", 25), Person("John", 32));System.out.println("""persons  : List[week6.forExpressions.Person] = """ + $show(persons ));$skip(70); val res$6 = 
                 
	persons filter (p => p.age > 20) map (p => p.name);System.out.println("""res6: List[String] = """ + $show(res$6));$skip(76); val res$7 = 
  //equivalent for expression
	for(p <- persons if p.age > 20) yield p.name;System.out.println("""res7: List[String] = """ + $show(res$7));$skip(428); val res$8 = 
	
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
  } yield(i, j);System.out.println("""res8: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$8));$skip(119); 
  
  def scalarProduct(xs: List[Double], ys: List[Double]): Double = {
  	(for((x,y) <- xs zip ys) yield(x*y)).sum
  };System.out.println("""scalarProduct: (xs: List[Double], ys: List[Double])Double""")}
}
