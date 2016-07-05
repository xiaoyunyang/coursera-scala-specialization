package week4

/*
 * Pattern Matching Decomposition Implementation - 
 * pattern matching automates the deconstruction, which is precisely what
 * decomposition needs to do.
 */
trait Expr3 {
  def eval: Int = this match {
    case Number3(n) => n
    case Sum3(e1, e2) => e1.eval + e2.eval
    case Prod3(e1, e2) => e1.eval * e2.eval 
  }
  def show: String = this match {
    case Number3(n) => ""+n
    case Sum3(e1, e2) => e1.show + " + " + e2.show
    case Prod3(Sum3(e1, e2), Sum3(e3, e4)) => "("+e1.show + " + " + e2.show + ")"+" * " + "("+e3.show + " + " + e4.show + ")"
    case Prod3(Sum3(e1, e2), e3) => "("+e1.show + " + " + e2.show + ")"+" * "+ e3.show
    case Prod3(e1, Sum3(e2, e3)) => e1.show + " * " + "("+e2.show + " + " + e3.show + ")"
    case Prod3(e1, e2) => e1.show + " * " + e2.show
    case Var3(s) => s
  }

}
case class Number3(n: Int) extends Expr3
case class Sum3(e1: Expr3, e2: Expr3) extends Expr3
case class Var3(x: String) extends Expr3
case class Prod3(e1: Expr3, e2: Expr3) extends Expr3

/* 
 * Scala defines the following companion Objects automatically
object Number3 {
  def apply(n: Int) = new Number3(n)
}
object Sum3 {
  def apply(e1: Expr3, e2: Expr3) = new Sum3(e1, e2)
}

 * So you can write Number3(1) instead of new Number3(1)
 */
object testExpr3 {
  def eval(e: Expr3): Int = e match {
    case Number3(n) => n
    case Sum3(e1, e2) => eval(e1) + eval(e2)
  }
  def show(e: Expr3): String = e match {
    case Number3(n) => ""+n
    case Sum3(e1, e2) => show(e1) + " + " + show(e2)
  }
  def main(args: Array[String]) = {
    val expr1 = Sum3(Number3(1), Number3(2))
    val expr2 = Sum3(Number3(3), Number3(4))
    val expr3 = Sum3(expr1, expr2)
    val expr4 = Sum3(Prod3(Number3(2), Var3("x")), Var3("y"))
    val expr5 = Prod3(Sum3(Number3(2), Var3("x")), Var3("y"))
    val expr6 = Prod3(Var3("y"), Sum3(Number3(2), Var3("x")))
    val expr7 = Prod3(expr1, expr2)
    println("Externally Defined eval and show:")
    println(show(expr1)+ " = "+ eval(expr1)) 
    println(show(expr2)+ " = "+ eval(expr2)) 
    println(show(expr3)+ " = "+ eval(expr3)) 
    println("Trait defined eval and show: ")
    println(expr1.show+ " = "+ expr1.eval) 
    println(expr2.show+ " = "+ expr2.eval) 
    println(expr3.show+ " = "+ expr3.eval) 
    
    println(expr4.show)
    println(expr5.show)
    println(expr6.show)
    println(expr7.show)

  }

  
}