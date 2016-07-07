package week2

trait MyStream[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: MyStream[T]
  
  def filter(p: T => Boolean): MyStream[T] = {
    if(isEmpty) this
    else if(p(head)) MyStream.cons(head, tail.filter(p))
    else tail.filter(p)
  }
}

object MyStream {
  def cons[T](hd: T, tl: => MyStream[T]) = new MyStream[T] {
    def isEmpty = false
    def head = hd
    
    def tail = tl //Because tl is a by-name parameter, it will not get 
    //evaluated until tl gets dereferenced, i.e., someone calls ".tail"
  }
  val empty = new MyStream[Nothing] {
    def isEmpty = true
    def head = throw new NoSuchElementException("empty.head")
    def tail = throw new NoSuchElementException("empty.tail")
  }
  
}
