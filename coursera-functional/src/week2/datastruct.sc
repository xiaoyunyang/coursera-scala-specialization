package week2

//Don't have to import Rational._ because it's in the same package
object datastruct {
  val x = new Rational(1, 3)                      //> x  : week2.Rational = 1/3
  val y = new Rational(5, 7)                      //> y  : week2.Rational = 5/7
  val z = new Rational(3, 2)                      //> z  : week2.Rational = 3/2
  
  //val strange = new Rational(1,0)
  val a = new Rational(2)                         //> a  : week2.Rational = 2/1
  val b = new Rational(2, 4)                      //> b  : week2.Rational = 1/2
  x.numer                                         //> res0: Int = 1
  x.denom                                         //> res1: Int = 3
  
  x.add(y)                                        //> res2: week2.Rational = 22/21
  y.add(x)                                        //> res3: week2.Rational = 22/21
  
  x.neg                                           //> res4: week2.Rational = -1/3
  x.sub(y)                                        //> res5: week2.Rational = -8/21
  x.sub(y).sub(z)                                 //> res6: week2.Rational = -79/42
  y.add(y)                                        //> res7: week2.Rational = 10/7
 
  x.less(y)                                       //> res8: Boolean = true
  x.max(y)                                        //> res9: week2.Rational = 5/7
  
  /**  Scala allows infix operators and symbolic identifiers **/
  x < y //writing less than using a symbolic identifier
                                                  //> res10: Boolean = true
  x max2 y //writing max2 infix                   //> res11: week2.Rational = 5/7
  x - y - z                                       //> res12: week2.Rational = -79/42
  y + x                                           //> res13: week2.Rational = 22/21
  val t = -x                                      //> t  : week2.Rational = -1/3
  val s = x.neg                                   //> s  : week2.Rational = -1/3
  x.numer                                         //> res14: Int = 1
  x.denom                                         //> res15: Int = 3
  t.numer                                         //> res16: Int = -1
  t.denom                                         //> res17: Int = 3
  s.numer                                         //> res18: Int = -1
  s.denom                                         //> res19: Int = 3
  
  /** Substitution of Scala classes */
  new Rational(1,2).numer                         //> res20: Int = 1
  /* Substitutions:
  		(1) First, 1 replaces x, 2 replaces y
  		(2) then new Rational(1,2) replaces this
  */
  new Rational(1,2).less(new Rational(2,3))       //> res21: Boolean = true
  /* Substitutions:
  		(1) First, 1 replaces x, 2 abd replaces y
  		(2) Then new Rational(2,3) replaces that
  		(3) Then new Rational(1,2) replaces this
  */
}