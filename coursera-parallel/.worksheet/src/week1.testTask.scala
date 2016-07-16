package week1

import org.scalameter._
import common._

object testTask {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(180); 
  //Testing benchmarking and the computation time of val, lazy val, and def
  
	def f[T] = (v: T) => v+"!";System.out.println("""f: [T]=> T => String""");$skip(28); 
	def f2[T](v: => T) = v+"!";System.out.println("""f2: [T](v: => T)String""");$skip(208); 
	
	//These guys have to be def beause if they were val, then they get evaluated the first time
	//they are declared. We want to measure the evaluation inside the scalameter
	def e1 = "hello".toList.map(f(_));System.out.println("""e1: => List[String]""");$skip(36); 
	val e11 = "hello".toList.map(f(_));System.out.println("""e11  : List[String] = """ + $show(e11 ));$skip(41); 
	lazy val e12 = "hello".toList.map(f(_));System.out.println("""e12: => List[String]""");$skip(39); 
	
	def e13 = "hello".toList.map(f2(_));System.out.println("""e13: => List[String]""");$skip(37); 
	val e14 = "hello".toList.map(f2(_));System.out.println("""e14  : List[String] = """ + $show(e14 ));$skip(42); 
	
	def e2 = "world".toList.map(a => f(a));System.out.println("""e2: => List[String]""");$skip(21); 
	def e3 = f("hello");System.out.println("""e3: => String""");$skip(21); 
	def e4 = f("world");System.out.println("""e4: => String""");$skip(35); 
	
	val (v1, v2) = parallel(e1, e2);System.out.println("""v1  : List[String] = """ + $show(v1 ));System.out.println("""v2  : List[String] = """ + $show(v2 ));$skip(202); 
	  	
	//lazy val - first time slow, then subsequent evaluations are fast because of memoization
	//computation first time it's called below.
	val time12 = withWarmer(new Warmer.Default) measure { e12 };System.out.println("""time12  : Double = """ + $show(time12 ));$skip(62); 
	val time12a = withWarmer(new Warmer.Default) measure { e12 };System.out.println("""time12a  : Double = """ + $show(time12a ));$skip(62); 
	val time12b = withWarmer(new Warmer.Default) measure { e12 };System.out.println("""time12b  : Double = """ + $show(time12b ));$skip(127); 
  
	//def - slow everytime because recomputes everytime you call it
	val time1 = withWarmer(new Warmer.Default) measure { e1 };System.out.println("""time1  : Double = """ + $show(time1 ));$skip(60); 
	val time1a = withWarmer(new Warmer.Default) measure { e1 };System.out.println("""time1a  : Double = """ + $show(time1a ));$skip(60); 
	val time1b = withWarmer(new Warmer.Default) measure { e1 };System.out.println("""time1b  : Double = """ + $show(time1b ));$skip(158); 
	
	//val - everytime fast because computation is performed when the val was first declared above
	val time11 = withWarmer(new Warmer.Default) measure { e11 };System.out.println("""time11  : Double = """ + $show(time11 ));$skip(62); 
	val time11a = withWarmer(new Warmer.Default) measure { e11 };System.out.println("""time11a  : Double = """ + $show(time11a ));$skip(62); 
	val time11b = withWarmer(new Warmer.Default) measure { e11 };System.out.println("""time11b  : Double = """ + $show(time11b ));$skip(82); 
	
	//def and by-name
	val time13 = withWarmer(new Warmer.Default) measure { e13 };System.out.println("""time13  : Double = """ + $show(time13 ));$skip(62); 
	val time13a = withWarmer(new Warmer.Default) measure { e13 };System.out.println("""time13a  : Double = """ + $show(time13a ));$skip(62); 
	val time13b = withWarmer(new Warmer.Default) measure { e13 };System.out.println("""time13b  : Double = """ + $show(time13b ));$skip(80); 
	//val and by-name
	val time14 = withWarmer(new Warmer.Default) measure { e14 };System.out.println("""time14  : Double = """ + $show(time14 ));$skip(62); 
	val time14a = withWarmer(new Warmer.Default) measure { e14 };System.out.println("""time14a  : Double = """ + $show(time14a ));$skip(62); 
	val time14b = withWarmer(new Warmer.Default) measure { e14 };System.out.println("""time14b  : Double = """ + $show(time14b ));$skip(111); 
                                                  	
	val time2 = withWarmer(new Warmer.Default) measure { e2 };System.out.println("""time2  : Double = """ + $show(time2 ));$skip(59); 
	val time3 = withWarmer(new Warmer.Default) measure { e3 };System.out.println("""time3  : Double = """ + $show(time3 ));$skip(59); 
	val time4 = withWarmer(new Warmer.Default) measure { e4 };System.out.println("""time4  : Double = """ + $show(time4 ));$skip(50); 
	println(s"time1 = $time1 ms, time2 = $time2 ms");$skip(50); 
	println(s"time3 = $time3 ms, time4 = $time4 ms");$skip(88); 
	
	
	val ((part1, part2), (part3, part4)) = parallel(parallel(e1, e2),parallel(e3, e4));System.out.println("""part1  : List[String] = """ + $show(part1 ));System.out.println("""part2  : List[String] = """ + $show(part2 ));System.out.println("""part3  : String = """ + $show(part3 ));System.out.println("""part4  : String = """ + $show(part4 ));$skip(111); 
  //withWarmer(new Warmer.Default) measure { e4 }
	val (part5, part6, part7, part8) = parallel(e1, e2, e3, e4);System.out.println("""part5  : List[String] = """ + $show(part5 ));System.out.println("""part6  : List[String] = """ + $show(part6 ));System.out.println("""part7  : String = """ + $show(part7 ));System.out.println("""part8  : String = """ + $show(part8 ))}
	
	//val t1 = task {e1} //This means start computation e1 "in the background"
	//val t2 = task {e2}
	//val t3 = task {e3}
	//val t4 = task {e4}
	//val w1 = t1.join
	//val w2 = t2.join
	//val w3 = t3.join
	//val w4 = t4.join



}
