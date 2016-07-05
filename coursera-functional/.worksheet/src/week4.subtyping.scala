package week4
import week3.{IntSet, NonEmpty, Empty2, Empty}

object subtyping {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(127); 

	def assertAllPos[S <: IntSet](r: S): S = ???;System.out.println("""assertAllPos: [S <: week3.IntSet](r: S)S""");$skip(128); 
	
	/** Demonstrates variance **/
	//given: NonEmpty <: IntSet
  val a: Array[NonEmpty] = Array(new NonEmpty(1, Empty2, Empty2));System.out.println("""a  : Array[week3.NonEmpty] = """ + $show(a ));$skip(95); 
  //val b: Array[IntSet] = a //-> type error
  //b(0) = Empty
  
  val b1: Array[NonEmpty] = a;System.out.println("""b1  : Array[week3.NonEmpty] = """ + $show(b1 ));$skip(28); 
  
  val s: NonEmpty = a(0);System.out.println("""s  : week3.NonEmpty = """ + $show(s ));$skip(66); 
 
 	val c: Array[IntSet] = Array(new NonEmpty(1, Empty2, Empty2));System.out.println("""c  : Array[week3.IntSet] = """ + $show(c ));$skip(28); 
  val c1: Array[IntSet] = c
	//val c2: Array[NonEmpty] = c //-> type error
	
	
	/** Covariance, Contravariance, or nonvariant? **/
	//Type A is a subtype of type B because type A gives you NonEmpty and type B gives you IntSet
	//Since NonEmpty <: IntSet, A <: B
	//We can pass the same argument to A as B because B takes NonEmpty, which is a subtype of
	//IntSet, which A takes in
	type A = IntSet => NonEmpty
	type B = NonEmpty => IntSet;System.out.println("""c1  : Array[week3.IntSet] = """ + $show(c1 ));$skip(443); 
	
	
	val x: List[String] = Nil2;System.out.println("""x  : week4.List[String] = """ + $show(x ));$skip(16); 
	val e = List();System.out.println("""e  : week4.List[Nothing] = """ + $show(e ));$skip(20); 
	val xs = List(1,2);System.out.println("""xs  : week4.List[Int] = """ + $show(xs ));$skip(15); val res$0 = 
	xs.prepend(e);System.out.println("""res0: week4.List[Any] = """ + $show(res$0));$skip(289); 
	
	//f returns List[IntSet] because IntSet is a supertype of both NonEmpty and Empty
	//Scala type inference lifted the type of elem from Empty to IntSet because Empty
	//cannot be passed into prepend to return a NonEmpty.
	def f(xs: List[NonEmpty], x: Empty): List[IntSet] = xs prepend x;System.out.println("""f: (xs: week4.List[week3.NonEmpty], x: week3.Empty)week4.List[week3.IntSet]""")}
}
