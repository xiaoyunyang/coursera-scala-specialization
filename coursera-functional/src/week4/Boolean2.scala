package week4

abstract class Boolean2 {
  
  //same as: if(cond) t else e
  def ifThenElse[T](t: => T, e: => T): T

  def && (x: => Boolean2): Boolean2 = ifThenElse(x, False) //If x is true, then return x. Else, return false
  def || (x: => Boolean2): Boolean2 = ifThenElse(True, x) //If x is true, return true. Else, return x
  def unary_! : Boolean2 = ifThenElse(False, True) //if x is true, return false. Else, return true
  
  def == (x: Boolean2): Boolean2 = ifThenElse(x, x.unary_!) //if x is true, return x. Else, return the negation of x
  def != (x: Boolean2): Boolean2 = ifThenElse(x.unary_!, x) //if x is true, return the negation of x. Else, return x
  
  def < (x: Boolean2): Boolean2 = ifThenElse(False, x)
}

object True extends Boolean2 {
  def ifThenElse[T](t: => T, e: => T) = t
}
object False extends Boolean2 {
  def ifThenElse[T](t: => T, e: => T) = e
}

