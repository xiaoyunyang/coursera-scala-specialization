package week3

import week2._
import week2.{Rational}

object classes_org {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(97); val res$0 = 
	
  new Rational(1,2);System.out.println("""res0: week2.Rational = """ + $show(res$0));$skip(48); 
  def error(msg: String) = throw new Error(msg);System.out.println("""error: (msg: String)Nothing""");$skip(60); 
  //error(s"Hello $name, you have an error")
  val x = null;System.out.println("""x  : Null = """ + $show(x ));$skip(23); 
  val y: String = null;System.out.println("""y  : String = """ + $show(y ));$skip(72); val res$1 = 
  //val z: Int = null //error: type mismatch
  
  if(true) 1 else false;System.out.println("""res1: AnyVal = """ + $show(res$1));$skip(51); 
  def fun(a: Boolean): AnyVal = if(a) 1 else false;System.out.println("""fun: (a: Boolean)AnyVal""");$skip(12); val res$2 = 
  fun(true);System.out.println("""res2: AnyVal = """ + $show(res$2));$skip(13); val res$3 = 
  fun(false);System.out.println("""res3: AnyVal = """ + $show(res$3))}
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
