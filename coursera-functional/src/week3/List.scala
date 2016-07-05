package week3

trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}
class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
  override def toString = "("+head+")"+tail
  //head and tail area already implemented by the the value parameters of the class
}
class Nil[T] extends List[T] {
  def isEmpty: Boolean = true
  //Nothing is a subtype of anything
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}
object ListProgram {
  def main(args: Array[String]) = {
    singleton[Int](1)
    singleton[Boolean](true)
    println("hello "+singleton[Int](1))
    val l = new Cons(3, new Cons(2, new Cons(1, new Nil)))
    println("0th of (3,2,1): "+nth(0, l))
    println("1st of (3,2,1): "+nth(1, l))
    println("2nd of (3,2,1): "+nth(2, l))
    println("3rd of (3,2,1): "+nth(3, l))
    
    
  }
  def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])
  
  //a function nth that takes an integer n and a list and selects
  //the nth element of the list
  def nth[A](n: Int, xs: List[A]): A = {
    if(xs.isEmpty) throw new IndexOutOfBoundsException("index out of bounds :(")
    else if (n==0) xs.head
    else nth(n-1, xs.tail)
  }
  

}