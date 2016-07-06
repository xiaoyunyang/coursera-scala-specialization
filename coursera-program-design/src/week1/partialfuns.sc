package week1

object funs {

  /** Partial matches **/
  
  val f: String => String = { case "ping" => "pong" }
                                                  //> f  : String => String = <function1>
  f("ping")                                       //> res0: String = pong
  //The following will give a match error because we don't have a case of "abc"
  //f("abc")
  
  val f1: PartialFunction[String, String] = { case "ping" => "pong" }
                                                  //> f1  : PartialFunction[String,String] = <function1>
  f1.isDefinedAt("ping")                          //> res1: Boolean = true
  f1.isDefinedAt("pong")                          //> res2: Boolean = false
  f1("ping")                                      //> res3: String = pong
  
  List("ping", "abc", "pong").map(a =>
  	if(f1.isDefinedAt(a)) f1(a) else "404"
  )                                               //> res4: List[String] = List(pong, 404, 404)
  

	val f2: PartialFunction[List[Int], String] = {
		case Nil => "one"
		case x :: y :: rest => "two"
	}                                         //> f2  : PartialFunction[List[Int],String] = <function1>
	
	f2.isDefinedAt(List(1, 2, 3))             //> res5: Boolean = true
	
	val g: PartialFunction[List[Int], String] = {
		case Nil => "one"
		case x :: rest => rest match {
			case Nil => "two"
		}
	}                                         //> g  : PartialFunction[List[Int],String] = <function1>
	
	g.isDefinedAt(List(1, 2, 3))              //> res6: Boolean = true
	//below code gives you a match error isDefined doesn't protect you from match error
	//g(List(1,2, 3))

  
  val f3: (Int => String) = List("a", "b", "c")   //> f3  : Int => String = List(a, b, c)
  f3(2)                                           //> res7: String = c
  


}