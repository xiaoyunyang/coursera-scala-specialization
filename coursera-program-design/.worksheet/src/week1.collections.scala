package week1

object collections {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(60); val res$0 = 
  List(1,2,3).map(_ + 1);System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(38); val res$1 = 
  List(1,2,3).flatMap(x => List(x,x));System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(36); val res$2 = 
  List(1,2,3) zip List("a","b","c");System.out.println("""res2: List[(Int, String)] = """ + $show(res$2));$skip(33); val res$3 = 
  List(1,2,3,4).partition(_ < 3);System.out.println("""res3: (List[Int], List[Int]) = """ + $show(res$3));$skip(35); val res$4 = 
  List(1,2,3,4).filter(_ % 2 == 1);System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(139); 
  
  //For-expressions or for comprehension - useful for when you want to do nested loops

  //instead of
  def isOdd(n: Int) = n % 2 == 1;System.out.println("""isOdd: (n: Int)Boolean""");$skip(13); 
  val n = 10;System.out.println("""n  : Int = """ + $show(n ));$skip(96); val res$5 = 
  (1 until n) flatMap(i =>
  	(1 until i) withFilter (j => isOdd(i + j)) map
  		(j => (i, j)));System.out.println("""res5: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$5));$skip(134); val res$6 = 
  //with for expression
  for {
  	i <- 1 until n //a generator
  	j <- 1 until i //another generator
  	if isOdd(i+j)
  } yield(i,j);System.out.println("""res6: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$6));$skip(283); 

	/* Map, flatMap, and filter implemented using for expression
	 * but in reality, scala compiler translates for expressions in terms of
	 * map, flatMap, and a lazy variation of filter (withFilter)
	 */
	def mapFun[T, U](xs: List[T], f: T => U): List[U] =
		for(x <- xs) yield f(x);System.out.println("""mapFun: [T, U](xs: List[T], f: T => U)List[U]""");$skip(100); 
	
	def flatMap[T, U](xs: List[T], f: T => Iterable[U]): List[U] =
		for(x <- xs; y <- f(x)) yield y;System.out.println("""flatMap: [T, U](xs: List[T], f: T => Iterable[U])List[U]""");$skip(89); 
	
	def filter[T](xs: List[T], p: T => Boolean): List[T] =
		for(x <- xs if p(x)) yield x;System.out.println("""filter: [T](xs: List[T], p: T => Boolean)List[T]""")}



}
