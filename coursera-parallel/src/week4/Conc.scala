package week4

//In parallel programming, this data type is known as the conc-list
sealed trait Conc[@specialized(Int, Long, Float, Double) +T] {
  def level: Int
  def size: Int
  def left: Conc[T]
  def right: Conc[T]
  def normalized = this


}

/** The following invariants ensure tres are always balanced 
 *  (1) a <> node can never contain Empty as its subtree
 *  (2)the level difference between the left and right subtree of 
 *  a <>node is always 1 or less
 */
case class <>[+T](left: Conc[T], right: Conc[T]) extends Conc[T] {
  val level = 1 + math.max(left.level, right.level)
  val size = left.size + right.size
}
sealed trait Leaf[T] extends Conc[T] {
  def left = sys.error("Leaves do not have children.")
  def right = sys.error("Leaves do not have children.")
}

class Single[@specialized(Int, Long, Float, Double) T](val x: T) extends Leaf[T] {
  def level = 0
  def size = 1
  override def toString = s"Single($x)"
}

case object Empty extends Leaf[Nothing] {
  def level = 0
  def size = 0
}

//constant time Append
case class Append[+T](left: Conc[T], right: Conc[T]) extends Conc[T] {
  val level = 1 + math.max(left.level, right.level)
  val size = left.size + right.size
  
}

object Conc {
  def appendLeaf[T](xs: Conc[T], ys: Single[T]): Conc[T] = xs match {
    case Empty => ys
    case xs: Single[T] => new <>(xs,ys)
    case _ <> _ => new Append(xs, ys)
    case xs: Append[T] => append(xs, ys)
  }
  
  @annotation.tailrec 
  private def append[T](xs: Append[T], ys: Conc[T]): Conc[T] = {
    if (xs.right.level > ys.level) new Append(xs, ys)
    else {
      val zs = new <>(xs.right, ys)
      xs.left match {
        case ws @ Append(_, _) => append(ws, zs)
        case ws if ws.level <= zs.level => <>(ws, zs)
        case ws => new Append(ws, zs)
      }
    }
  }
  
  //concat
  def <>[T](xs: Conc[T], ys: Conc[T]) = {
    if (xs == Empty) ys
    else if (ys == Empty) xs
    else concat(xs, ys)
  }
  
  private def concat[T](xs: Conc[T], ys: Conc[T]): Conc[T] = {
    val diff = ys.level - xs.level
    
    if(diff >= -1 && diff <= 1) {
      new <>(xs, ys)
    } 
    
    //the xs tree is too tall
    else if(diff < -1) {
      //the xs tree is left leaning
      if(xs.left.level >= xs.right.level) {
        val nr = concat(xs.right, ys)
        new <>(xs.left, nr)
      } 
      //the xs tree is right leaning
      else {
        val nrr = concat(xs.right.right, ys)
        if(nrr.level == xs.level - 3) {
          val nl = xs.left
          val nr = new <>(xs.right.left, nrr)
          new <>(nl ,nr)
        } else {
          val nl = new <>(xs.left, xs.right.left)
          val nr = nrr
          new <>(nl, nr)
        }
          
      }
    } 
    
    //the ys tree is too tall
    else {
      //the ys tree is right leaning
      if (ys.right.level >= ys.left.level) {
        val nl = concat(xs, ys.left)
        new <>(nl, ys.right)
      } 
      //the ys tree is left leaning
      else {
        val nll = concat(xs, ys.left.left)
        if (nll.level == ys.level - 3) {
          val nl = new <>(nll, ys.left.right)
          val nr = ys.right
          new <>(nl, nr)
        } else {
          val nl = nll
          val nr = new <>(ys.left.right, ys.right)
          new <>(nl, nr)
        }
      }
    }
  }
  
  
  
}