package week2

trait MyLazyStream[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: MyLazyStream[T]
  
  def filter(p: T => Boolean): MyLazyStream[T] = {
    if(isEmpty) this
    else if(p(head)) MyLazyStream.cons(head, tail.filter(p))
    else tail.filter(p)
  }
  def apply(n: Int): T = {
    if(n==0) head
    else tail.apply(n-1)
  }
}

object MyLazyStream {
  def cons[T](hd: T, tl: => MyLazyStream[T]) = new MyLazyStream[T] {
    def isEmpty = false
    def head = hd
    
    lazy val tail = tl //Because tl is a by-name parameter, it will not get 
    //evaluated until tl gets dereferenced, i.e., someone calls ".tail"
    //Additionally, lazy will not get unnecessary recomputation because 
    //we made it a lazy val
  }
  val empty = new MyLazyStream[Nothing] {
    def isEmpty = true
    def head = throw new NoSuchElementException("empty.head")
    def tail = throw new NoSuchElementException("empty.tail")
  }
  
}