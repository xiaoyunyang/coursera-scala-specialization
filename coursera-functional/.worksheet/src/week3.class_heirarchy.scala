package week3

object class_heirarchy {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(153); 
	//However, you can't instantiate abstract classes
	//new IntSet
  val t1 = new NonEmpty(3, new Empty, new Empty);System.out.println("""t1  : week3.NonEmpty = """ + $show(t1 ));$skip(21); 
  val t2 = t1 incl 4;System.out.println("""t2  : week3.IntSet = """ + $show(t2 ));$skip(14); val res$0 = 
  t1 union t2;System.out.println("""res0: week3.IntSet = """ + $show(res$0));$skip(207); val res$1 = 
  
  //OO language (including Scala) implements dynamic method dispatch
  //Dynamic dispatch of methods is analogous to cals to higher-order functions
  
  //sub: [1/x][Empty/this] false
  Empty2 contains 1;System.out.println("""res1: Boolean = """ + $show(res$1));$skip(108); val res$2 = 
  
  //sub: [7/elem][7/x][new NonEmpty(7,Empty, Empty)/this]
  (new NonEmpty(7, Empty2, Empty2)) contains 7;System.out.println("""res2: Boolean = """ + $show(res$2));$skip(53); val res$3 = 
  (new NonEmpty(7, new Empty, new Empty)) contains 7;System.out.println("""res3: Boolean = """ + $show(res$3))}
  
}


abstract class Base {
	def foo = 1
	def bar: Int
}

class Sub extends Base {
	override def foo = 2
	def bar = 3
}
