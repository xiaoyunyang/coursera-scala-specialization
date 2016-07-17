package week3

object loop {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(235); 
  def power(x: Double, exp: Int): Double = {
  	var r = 1.0
  	var i = exp
  	//the condition must be passed by name so it can be reiterated every time
  	while (i>0) {
  		r = r*x
  		i = i-1
  	}
  	r
  };System.out.println("""power: (x: Double, exp: Int)Double""");$skip(177); 
  
  /** A regular While loop */
  def WHILE(condition: => Boolean)(command: => Unit): Unit = {
  	if(condition) {
  		command
  		WHILE(condition)(command)
  	}
  	else ()
  };System.out.println("""WHILE: (condition: => Boolean)(command: => Unit)Unit""");$skip(14); 
  var cnt = 0;System.out.println("""cnt  : Int = """ + $show(cnt ));$skip(25); 
	WHILE(cnt>0)(cnt=cnt-1);$skip(6); val res$0 = 
  cnt;System.out.println("""res0: Int = """ + $show(res$0));$skip(171); 
  
  /** A do while loop */
  def REPEAT(command: => Unit)(condition: => Boolean): Unit = {
  	command
  	if(condition) () //return
  	else REPEAT(command)(condition)
  };System.out.println("""REPEAT: (command: => Unit)(condition: => Boolean)Unit""");$skip(34); 
  
  

	REPEAT(cnt=cnt+1)(cnt>=4);$skip(5); val res$1 = 
	cnt
  
  /** DO { command } UNTIL (condition) loop */
  class DO(command: => Unit) {
	  def WHILE(condition: => Boolean): Unit = {
	    command
	    if (condition) ()
	    else DO {command} WHILE (condition)
	  }
	}
	object DO {
	  def apply(command: => Unit) = new DO(command)
	};System.out.println("""res1: Int = """ + $show(res$1));$skip(290); 
  
  cnt = 0;$skip(42); 
	DO {
		cnt = cnt + 1
	} WHILE (cnt >= 4);$skip(6); val res$2 = 
 	cnt;System.out.println("""res2: Int = """ + $show(res$2));$skip(140); 
 	
 	/** For loop */
 	//like Scala's for-expressions and foreach on the scala collection Range
 	for(i <- 1 until 3) { println(i + " " ) };$skip(47); 
  (1 until 3) foreach (i => println(i + " " ));$skip(58); 
  
  for(i <- 1 until 3; j <- "abc") println(i + " " + j);$skip(71); 
  (1 until 3) foreach (i => "abc" foreach (j => println(i + " " + j)))}
 	
 	
 	
}
