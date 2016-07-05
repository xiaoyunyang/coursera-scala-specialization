package week3

object class_hierarchy {
	//However, you can't instantiate abstract classes
	//new IntSet
  val t1 = new NonEmpty(3, new Empty, new Empty)  //> t1  : week3.NonEmpty = {.3.}
  val t2 = t1 incl 4                              //> t2  : week3.IntSet = {.3{.4.}}
  t1 union t2                                     //> res0: week3.IntSet = {.3{.4.}}
  
  //OO language (including Scala) implements dynamic method dispatch
  //Dynamic dispatch of methods is analogous to cals to higher-order functions
  
  //sub: [1/x][Empty/this] false
  Empty2 contains 1                               //> res1: Boolean = false
  
  //sub: [7/elem][7/x][new NonEmpty(7,Empty, Empty)/this]
  (new NonEmpty(7, Empty2, Empty2)) contains 7    //> res2: Boolean = true
  (new NonEmpty(7, new Empty, new Empty)) contains 7
                                                  //> res3: Boolean = true
  
}


abstract class Base {
	def foo = 1
	def bar: Int
}

class Sub extends Base {
	override def foo = 2
	def bar = 3
}