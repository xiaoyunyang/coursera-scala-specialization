package week2

//Don't have to import Rational._ because it's in the same package
object datastruct {
  val x = new Rational(1, 3)
  val y = new Rational(5, 7)
  val z = new Rational(3, 2)
  
  //val strange = new Rational(1,0)
  val a = new Rational(2)
  val b = new Rational(2, 4)
  x.numer
  x.denom
  
  x.add(y)
  y.add(x)
  
  x.neg
  x.sub(y)
  x.sub(y).sub(z)
  y.add(y)
 
  x.less(y)
  x.max(y)
  
  /**  Scala allows infix operators and symbolic identifiers **/
  x < y //writing less than using a symbolic identifier
  x max2 y //writing max2 infix
  x - y - z
  y + x
  val t = -x
  val s = x.neg
  x.numer
  x.denom
  t.numer
  t.denom
  s.numer
  s.denom
  
  /** Substitution of Scala classes */
  new Rational(1,2).numer
  /* Substitutions:
  		(1) First, 1 replaces x, 2 replaces y
  		(2) then new Rational(1,2) replaces this
  */
  new Rational(1,2).less(new Rational(2,3))
  /* Substitutions:
  		(1) First, 1 replaces x, 2 abd replaces y
  		(2) Then new Rational(2,3) replaces that
  		(3) Then new Rational(1,2) replaces this
  */
  
}