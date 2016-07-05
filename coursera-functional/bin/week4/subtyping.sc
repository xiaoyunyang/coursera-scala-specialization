package week4
import week3.{IntSet, NonEmpty, Empty2, Empty}

object subtyping {

	def assertAllPos[S <: IntSet](r: S): S = ???
                                                  //> assertAllPos: [S <: week3.IntSet](r: S)S
	
	/** Demonstrates variance **/
	//given: NonEmpty <: IntSet
  val a: Array[NonEmpty] = Array(new NonEmpty(1, Empty2, Empty2))
                                                  //> a  : Array[week3.NonEmpty] = Array({.1.})
  //val b: Array[IntSet] = a //-> type error
  //b(0) = Empty
  
  val b1: Array[NonEmpty] = a                     //> b1  : Array[week3.NonEmpty] = Array({.1.})
  
  val s: NonEmpty = a(0)                          //> s  : week3.NonEmpty = {.1.}
 
 	val c: Array[IntSet] = Array(new NonEmpty(1, Empty2, Empty2))
                                                  //> c  : Array[week3.IntSet] = Array({.1.})
  val c1: Array[IntSet] = c                       //> c1  : Array[week3.IntSet] = Array({.1.})
	//val c2: Array[NonEmpty] = c //-> type error
	
	
	/** Covariance, Contravariance, or nonvariant? **/
	//Type A is a subtype of type B because type A gives you NonEmpty and type B gives you IntSet
	//Since NonEmpty <: IntSet, A <: B
	//We can pass the same argument to A as B because B takes NonEmpty, which is a subtype of
	//IntSet, which A takes in
	type A = IntSet => NonEmpty
	type B = NonEmpty => IntSet
	
	
	val x: List[String] = Nil2                //> x  : week4.List[String] = List(Nil)
	val e = List()                            //> e  : week4.List[Nothing] = List(Nil)
	val xs = List(1,2)                        //> xs  : week4.List[Int] = List(1, 2, Nil)
	xs.prepend(e)                             //> res0: week4.List[Any] = List(List(Nil), 1, 2, Nil)
	
	//f returns List[IntSet] because IntSet is a supertype of both NonEmpty and Empty
	//Scala type inference lifted the type of elem from Empty to IntSet because Empty
	//cannot be passed into prepend to return a NonEmpty.
	def f(xs: List[NonEmpty], x: Empty): List[IntSet] = xs prepend x
                                                  //> f: (xs: week4.List[week3.NonEmpty], x: week3.Empty)week4.List[week3.IntSet]
                                                  //| 
}