package week4

/* 
 * Object-Oriented Decomposition implementation - 
 * Problem with this solution is if we want to add another function def to 
 * Expr2, e.g., show, we have to create implementations of show in all classes
 * that implement the Expr2. 
 * Another problem is if we want to simplify the expression, say: 
 * a * b + a * c -> a * (b + c), this is a non-local simplification, which 
 * cannot be encapsulated in the method of a single object
 */
trait Expr2 {
  def eval: Int
}
class Number2(n: Int) extends Expr2 {
  def eval: Int = n
}
class Sum2(e1: Expr2, e2: Expr2) extends Expr2 {
  def eval: Int = e1.eval + e2.eval
}