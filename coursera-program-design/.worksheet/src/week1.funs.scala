package week1

object funs {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(112); 

  /** Partial matches **/
  
  val f: String => String = { case "ping" => "pong" };System.out.println("""f  : String => String = """ + $show(f ));$skip(12); val res$0 = 
  f("ping");System.out.println("""res0: String = """ + $show(res$0));$skip(166); 
  //The following will give a match error because we don't have a case of "abc"
  //f("abc")
  
  val f1: PartialFunction[String, String] = { case "ping" => "pong" };System.out.println("""f1  : PartialFunction[String,String] = """ + $show(f1 ));$skip(25); val res$1 = 
  f1.isDefinedAt("ping");System.out.println("""res1: Boolean = """ + $show(res$1));$skip(25); val res$2 = 
  f1.isDefinedAt("pong");System.out.println("""res2: Boolean = """ + $show(res$2));$skip(13); val res$3 = 
  f1("ping");System.out.println("""res3: String = """ + $show(res$3));$skip(88); val res$4 = 
  
  List("ping", "abc", "pong").map(a =>
  	if(f1.isDefinedAt(a)) f1(a) else "404"
  );System.out.println("""res4: List[String] = """ + $show(res$4));$skip(106); 
  

	val f2: PartialFunction[List[Int], String] = {
		case Nil => "one"
		case x :: y :: rest => "two"
	};System.out.println("""f2  : PartialFunction[List[Int],String] = """ + $show(f2 ));$skip(33); val res$5 = 
	
	f2.isDefinedAt(List(1, 2, 3));System.out.println("""res5: Boolean = """ + $show(res$5));$skip(130); 
	
	val g: PartialFunction[List[Int], String] = {
		case Nil => "one"
		case x :: rest => rest match {
			case Nil => "two"
		}
	};System.out.println("""g  : PartialFunction[List[Int],String] = """ + $show(g ));$skip(32); val res$6 = 
	
	g.isDefinedAt(List(1, 2, 3));System.out.println("""res6: Boolean = """ + $show(res$6));$skip(156); 
	//below code gives you a match error isDefined doesn't protect you from match error
	//g(List(1,2, 3))

  
  val f3: (Int => String) = List("a", "b", "c");System.out.println("""f3  : Int => String = """ + $show(f3 ));$skip(8); val res$7 = 
  f3(2);System.out.println("""res7: String = """ + $show(res$7))}
  


}
