package week1

object monad {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(73); 
  println("Welcome to the Scala worksheet");$skip(82); val res$0 = 
  //map can be written as a flatMap and a unit: List()
  List(1,2) map (x => x+1);System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(37); val res$1 = 
  List(1,2) flatMap (x => List(x+1));System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(70); 

	/** Checking if Option satisfy the three monad laws: **/
	val x = 2;System.out.println("""x  : Int = """ + $show(x ));$skip(11); 
	val y = 3;System.out.println("""y  : Int = """ + $show(y ));$skip(11); 
	val z = 4;System.out.println("""z  : Int = """ + $show(z ));$skip(19); 
	val opt = Some(x);System.out.println("""opt  : week1.Some[Int] = """ + $show(opt ));$skip(30); 
	def f[T] = (x: T) => Some(y);System.out.println("""f: [T]=> T => week1.Some[Int]""");$skip(30); 
	def g[T] = (x: T) => Some(z);System.out.println("""g: [T]=> T => week1.Some[Int]""");$skip(56); val res$2 = 
	
	//Check if Left Unit Law holds:
	(Some(x) flatMap f);System.out.println("""res2: week1.Option[Int] = """ + $show(res$2));$skip(29); val res$3 = 
	(Some(x) flatMap f) == f(x);System.out.println("""res3: Boolean = """ + $show(res$3));$skip(64); val res$4 = 
	
	//Check if Right Unit law holds
	(opt flatMap (new Some(_)));System.out.println("""res4: week1.Option[Int] = """ + $show(res$4));$skip(36); val res$5 = 
	(opt flatMap (new Some(_))) == opt;System.out.println("""res5: Boolean = """ + $show(res$5));$skip(67); val res$6 = 
	
	//Check if the Associative Law holds
	(opt flatMap f flatMap g);System.out.println("""res6: week1.Option[Int] = """ + $show(res$6));$skip(68); val res$7 = 
	((opt flatMap f) flatMap g) == (opt flatMap (x => f(x) flatMap g));System.out.println("""res7: Boolean = """ + $show(res$7))}
}
/** Monad **/
trait M[T] {
	def flatMap[U](f: T => M[U]): M[U]
}
abstract class Monad {
	def unit[T](x: T): M[T]
}

/** Option **/
/* Option satisfies the three monad laws:
 * 1) Right Associativity: m flatMap f flatMap g == m flatMap (x => f(x) flatMap g)
 * 2) Left Unit: unit(x) flatMap f == f(x)
 * 3) Right Unit:  m flatMap unit = m
 */
abstract class Option[+T] {
	def flatMap[U](f: T => Option[U]): Option[U] = this match {
		case Some(x) => f(x)
		case None => None
	}
}
case class Some[T](get: T) extends Option[T]
case object None extends Option[Nothing]


/** Try **/
/* Try resembles Option but for the Failure case, it returns an Exception
 * Try is used to pass results of computation that can fail with an exception between
 * threads and computers
 */
abstract class Try[+T] {
	def flatMap[U](f: T => Try[U]): Try[U] = this match {
		case Success(x) => try f(x) catch { case NonFatal(ex) => Failure(ex) }
		case fail: Failure => fail
	}
	def map[U](f: T => U): Try[U] = this match {
		case Success(x) => Try(f(x))
		case fail: Failure => fail
	}

}
case class Success[T](x: T) extends Try[T]
case class Failure(ex: Exception) extends Try[Nothing]

object Try {
	def apply[T](expr: => T): Try[T] =
		try Success(expr)
		catch {
			case NonFatal(ex) => Failure(ex)
		}
		
}
 