package week4

/* Naiive implementation - quadratic method proliferation with
 * each class implementation
 */
trait Expr {
  //Classification methods
  def isNumber: Boolean
  def isSum: Boolean

  //Accessor methods
  def numValue: Int
  def leftOp: Expr
  def rightOp: Expr
}

class Number(n: Int) extends Expr {
  def isNumber: Boolean = true
  def isSum: Boolean = false
  def numValue: Int = n
  def leftOp: Expr = throw new Error("Number.leftOp")
  def rightOp: Expr = throw new Error("Number.rightOp")
}
class Sum(e1: Expr, e2: Expr) extends Expr {
  def isNumber: Boolean = false
  def isSum: Boolean = true
  def numValue: Int = throw new Error("Sum.numValue")
  def leftOp: Expr = e1
  def rightOp: Expr = e2
}

/*
 * As you increase more class implementation, you increase the number
 * of method calls quadratically. This is bad news.
class Prod(e1: Expr, e2: Expr) extends Expr
class Var(x: String) extends Expr
*/

object testExpr {
  //Problem: Writing all these classification and accessor functions 
  //quickly becomes tedious
  def eval(e: Expr): Int = {
    if(e.isNumber) e.numValue
    else if(e.isSum) eval(e.leftOp) + eval(e.rightOp)
    else throw new Error("Unknown expression")
  }
}