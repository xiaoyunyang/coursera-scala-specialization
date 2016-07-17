package week1

/** Option and Try */
object monad {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  //map can be written as a flatMap and a unit: List()
  List(1,2) map (x => x+1)                        //> res0: List[Int] = List(2, 3)
  List(1,2) flatMap (x => List(x+1))              //> res1: List[Int] = List(2, 3)

	/** Checking if Option satisfy the three monad laws: **/
	val x = 2                                 //> x  : Int = 2
	val y = 3                                 //> y  : Int = 3
	val z = 4                                 //> z  : Int = 4
	val opt = Some(x)                         //> opt  : week1.Some[Int] = Some(2)
	def f[T] = (x: T) => Some(y)              //> f: [T]=> T => week1.Some[Int]
	def g[T] = (x: T) => Some(z)              //> g: [T]=> T => week1.Some[Int]
	
	//Check if Left Unit Law holds:
	(Some(x) flatMap f)                       //> res2: week1.Option[Int] = Some(3)
	(Some(x) flatMap f) == f(x)               //> res3: Boolean = true
	
	//Check if Right Unit law holds
	(opt flatMap (new Some(_)))               //> res4: week1.Option[Int] = Some(2)
	(opt flatMap (new Some(_))) == opt        //> res5: Boolean = true
	
	//Check if the Associative Law holds
	(opt flatMap f flatMap g)                 //> res6: week1.Option[Int] = Some(4)
	((opt flatMap f) flatMap g) == (opt flatMap (x => f(x) flatMap g))
                                                  //> res7: Boolean = true

	def computeA = (x: Int) => Try(x+1)       //> computeA: => Int => week1.Try[Int]
	def computeB = (x: Int) => Try(1/x)       //> computeB: => Int => week1.Try[Int]
	//def computeAB = (x: Try[Int], y: Try[Int]) => Try(x+y)

	/** Composing Try **/
	for {
		a <- computeA(1)
		b <- computeB(1)
	} yield (a, b)                            //> res8: week1.Try[(Int, Int)] = Success((2,1))
	
	//Try is not a monad because the Left Unit law fails
	Try(1) flatMap computeB                   //> res9: week1.Try[Int] = Success(1)
  computeB(1)                                     //> res10: week1.Try[Int] = Success(1)
  
	val e = new Exception("a NonFatal error") //> e  : Exception = java.lang.Exception: a NonFatal error
	val nonFatal = new NonFatal(e)            //> nonFatal  : week1.NonFatal = week1.NonFatal
	Try(nonFatal)                             //> res11: week1.Try[week1.NonFatal] = Success(week1.NonFatal)
	new Failure(e)                            //> res12: week1.Failure = Failure(java.lang.Exception: a NonFatal error)

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
	def Try[A](a: => A): Option[A] = {
    try Some(a)
    catch { case e: Exception => None } //note this discards information about the error
  }
}
case class Some[T](get: T) extends Option[T]
case object None extends Option[Nothing]


/** Try **/
/* Try resembles Option but for the Failure case, it returns an Exception
 * Try is used to pass results of computation that can fail with an exception between
 * threads and computers.
 * Try is not a monad because the Left Unit Law fails;
 * Try(expr) flatMap f != f(expr)
 * the left-hand side will never raise a non-fatal exception whereas the right-hand side will
 * raise any exception thrown by expr or f
 */
abstract class Try[+T] {
	def flatMap[U](f: T => Try[U]): Try[U] = this match {
		case Success(x) => try f(x) catch { case NonFatal(ex) => Failure(ex) }
		case fail: Failure => fail
	}
	def map[U](f: T => U): Try[U] = this match {
		case Success(x) => Try(f(x))
		case fail: Failure => fail //failure gets propagated in the result
	}
}
case class Success[T](x: T) extends Try[T]
case class Failure(ex: Exception) extends Try[Nothing]

case class NonFatal(ex: Exception) extends Throwable

object Try {
	def apply[T](expr: => T): Try[T] =
		try Success(expr)
		catch {
			case NonFatal(ex) => Failure(ex)
		}
		
}
 