package week3

object loop {
  def power(x: Double, exp: Int): Double = {
  	var r = 1.0
  	var i = exp
  	//the condition must be passed by name so it can be reiterated every time
  	while (i>0) {
  		r = r*x
  		i = i-1
  	}
  	r
  }                                               //> power: (x: Double, exp: Int)Double
  
  /** A regular While loop */
  def WHILE(condition: => Boolean)(command: => Unit): Unit = {
  	if(condition) {
  		command
  		WHILE(condition)(command)
  	}
  	else ()
  }                                               //> WHILE: (condition: => Boolean)(command: => Unit)Unit
  var cnt = 0                                     //> cnt  : Int = 0
	WHILE(cnt>0)(cnt=cnt-1)
  cnt                                             //> res0: Int = 0
  
  /** A do while loop */
  def REPEAT(command: => Unit)(condition: => Boolean): Unit = {
  	command
  	if(condition) () //return
  	else REPEAT(command)(condition)
  }                                               //> REPEAT: (command: => Unit)(condition: => Boolean)Unit
  
  

	REPEAT(cnt=cnt+1)(cnt>=4)
	cnt                                       //> res1: Int = 4
  
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
	}
  
  cnt = 0
	DO {
		cnt = cnt + 1
	} WHILE (cnt >= 4)
 	cnt                                       //> res2: Int = 4
 	
 	/** For loop */
 	//like Scala's for-expressions and foreach on the scala collection Range
 	for(i <- 1 until 3) { println(i + " " ) } //> 1 
                                                  //| 2 
  (1 until 3) foreach (i => println(i + " " ))    //> 1 
                                                  //| 2 
  
  for(i <- 1 until 3; j <- "abc") println(i + " " + j)
                                                  //> 1 a
                                                  //| 1 b
                                                  //| 1 c
                                                  //| 2 a
                                                  //| 2 b
                                                  //| 2 c
  (1 until 3) foreach (i => "abc" foreach (j => println(i + " " + j)))
                                                  //> 1 a
                                                  //| 1 b
                                                  //| 1 c
                                                  //| 2 a
                                                  //| 2 b
                                                  //| 2 c
 	
 	
 	
}