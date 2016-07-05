package week3

import week2._
import week2.{Rational}

object classes_org {
	
  new Rational(1,2)                               //> res0: week2.Rational = 1/2
  def error(msg: String) = throw new Error(msg)   //> error: (msg: String)Nothing
  //error(s"Hello $name, you have an error")
  val x = null                                    //> x  : Null = null
  val y: String = null                            //> y  : String = null
  //val z: Int = null //error: type mismatch
  
  if(true) 1 else false                           //> res1: AnyVal = 1
  def fun(a: Boolean): AnyVal = if(a) 1 else false//> fun: (a: Boolean)AnyVal
  fun(true)                                       //> res2: AnyVal = 1
  fun(false)                                      //> res3: AnyVal = false
}
trait Planar {
	def height: Int
	def width: Int
	def surface = height * width
}
trait Movable {
}
class Shape {
}

abstract class Square extends Shape with Planar with Movable {
//Square extends one superclass - Planar - with traits Planar and Movable

}