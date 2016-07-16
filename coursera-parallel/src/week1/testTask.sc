package week1

import org.scalameter._
import common._

object testTask {
  //Testing benchmarking and the computation time of val, lazy val, and def
  
	def f[T] = (v: T) => v+"!"                //> f: [T]=> T => String
	def f2[T](v: => T) = v+"!"                //> f2: [T](v: => T)String
	
	//These guys have to be def beause if they were val, then they get evaluated the first time
	//they are declared. We want to measure the evaluation inside the scalameter
	def e1 = "hello".toList.map(f(_))         //> e1: => List[String]
	val e11 = "hello".toList.map(f(_))        //> e11  : List[String] = List(h!, e!, l!, l!, o!)
	lazy val e12 = "hello".toList.map(f(_))   //> e12: => List[String]
	
	def e13 = "hello".toList.map(f2(_))       //> e13: => List[String]
	val e14 = "hello".toList.map(f2(_))       //> e14  : List[String] = List(h!, e!, l!, l!, o!)
	
	def e2 = "world".toList.map(a => f(a))    //> e2: => List[String]
	def e3 = f("hello")                       //> e3: => String
	def e4 = f("world")                       //> e4: => String
	
	val (v1, v2) = parallel(e1, e2)           //> v1  : List[String] = List(h!, e!, l!, l!, o!)
                                                  //| v2  : List[String] = List(w!, o!, r!, l!, d!)
	  	
	//lazy val - first time slow, then subsequent evaluations are fast because of memoization
	//computation first time it's called below.
	val time12 = withWarmer(new Warmer.Default) measure { e12 }
                                                  //> time12  : Double = 0.001023
	val time12a = withWarmer(new Warmer.Default) measure { e12 }
                                                  //> time12a  : Double = 5.84E-4
	val time12b = withWarmer(new Warmer.Default) measure { e12 }
                                                  //> time12b  : Double = 6.32E-4
  
	//def - slow everytime because recomputes everytime you call it
	val time1 = withWarmer(new Warmer.Default) measure { e1 }
                                                  //> time1  : Double = 0.030508
	val time1a = withWarmer(new Warmer.Default) measure { e1 }
                                                  //> time1a  : Double = 0.03919
	val time1b = withWarmer(new Warmer.Default) measure { e1 }
                                                  //> time1b  : Double = 0.012284
	
	//val - everytime fast because computation is performed when the val was first declared above
	val time11 = withWarmer(new Warmer.Default) measure { e11 }
                                                  //> time11  : Double = 4.63E-4
	val time11a = withWarmer(new Warmer.Default) measure { e11 }
                                                  //> time11a  : Double = 4.43E-4
	val time11b = withWarmer(new Warmer.Default) measure { e11 }
                                                  //> time11b  : Double = 4.69E-4
	
	//def and by-name
	val time13 = withWarmer(new Warmer.Default) measure { e13 }
                                                  //> time13  : Double = 0.021346
	val time13a = withWarmer(new Warmer.Default) measure { e13 }
                                                  //> time13a  : Double = 0.015469
	val time13b = withWarmer(new Warmer.Default) measure { e13 }
                                                  //> time13b  : Double = 0.013951
	//val and by-name
	val time14 = withWarmer(new Warmer.Default) measure { e14 }
                                                  //> time14  : Double = 4.42E-4
	val time14a = withWarmer(new Warmer.Default) measure { e14 }
                                                  //> time14a  : Double = 4.43E-4
	val time14b = withWarmer(new Warmer.Default) measure { e14 }
                                                  //> time14b  : Double = 4.37E-4
                                                  	
	val time2 = withWarmer(new Warmer.Default) measure { e2 }
                                                  //> time2  : Double = 0.011453
	val time3 = withWarmer(new Warmer.Default) measure { e3 }
                                                  //> time3  : Double = 0.001304
	val time4 = withWarmer(new Warmer.Default) measure { e4 }
                                                  //> time4  : Double = 0.001208
	println(s"time1 = $time1 ms, time2 = $time2 ms")
                                                  //> time1 = 0.030508 ms, time2 = 0.011453 ms
	println(s"time3 = $time3 ms, time4 = $time4 ms")
                                                  //> time3 = 0.001304 ms, time4 = 0.001208 ms
	
	
	val ((part1, part2), (part3, part4)) = parallel(parallel(e1, e2),parallel(e3, e4))
                                                  //> part1  : List[String] = List(h!, e!, l!, l!, o!)
                                                  //| part2  : List[String] = List(w!, o!, r!, l!, d!)
                                                  //| part3  : String = hello!
                                                  //| part4  : String = world!
  //withWarmer(new Warmer.Default) measure { e4 }
	val (part5, part6, part7, part8) = parallel(e1, e2, e3, e4)
                                                  //> part5  : List[String] = List(h!, e!, l!, l!, o!)
                                                  //| part6  : List[String] = List(w!, o!, r!, l!, d!)
                                                  //| part7  : String = hello!
                                                  //| part8  : String = world!
	
	//val t1 = task {e1} //This means start computation e1 "in the background"
	//val t2 = task {e2}
	//val t3 = task {e3}
	//val t4 = task {e4}
	//val w1 = t1.join
	//val w2 = t2.join
	//val w3 = t3.join
	//val w4 = t4.join



}