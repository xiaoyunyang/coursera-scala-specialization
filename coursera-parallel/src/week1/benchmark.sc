package parallel

import org.scalameter._

//Using ScalaMeter to benchmark
object benchmark {

	/** Measuring run time in ms **/
  //first program is interpreted, then part of the program are compiled into machine language
  measure {
  	(0 until 1000000).toArray
  }                                               //> res0: Double = 29.333518
  
  //JVM dynamic optimization kicks in
	measure {
  	(0 until 1000000).toArray
  }                                               //> res1: Double = 12.608655
  
  //Garbage collector kicks in
	measure {
  	(0 until 1000000).toArray
  }                                               //> res2: Double = 14.554395
  
 	measure {
  	(0 until 1000000).toArray
  }                                               //> res3: Double = 4.891004
  
  //... eventually reaches some steady staet
  
  /** Measuring steady state run time **/
  //Measuring steady-state program performance - the actual steady state is between an interval
  withWarmer(new Warmer.Default) measure { (0 until 1000000).toArray }
                                                  //> res4: Double = 4.696093
	withWarmer(new Warmer.Default) measure { (0 until 1000000).toArray }
                                                  //> res5: Double = 5.593077
	withWarmer(new Warmer.Default) measure { (0 until 1000000).toArray }
                                                  //> res6: Double = 4.720287
	withWarmer(new Warmer.Default) measure { (0 until 1000000).toArray }
                                                  //> res7: Double = 12.332659
	withWarmer(new Warmer.Default) measure { (0 until 1000000).toArray }
                                                  //> res8: Double = 4.641276
	withWarmer(new Warmer.Default) measure { (0 until 1000000).toArray }
                                                  //> res9: Double = 4.618206
                                                  
 	
 	val t = withWarmer(new Warmer.Default) measure { (0 until 1000000).toArray }
                                                  //> t  : Double = 5.478061
 	println(s"1M integer Array initialization time: $t ms")
                                                  //> 1M integer Array initialization time: 5.478061 ms
 	
 	                                               
  /** Measuring memory footprint **/
  //Memory footprint measure the total amount of memory occupied by the object returned
  
  //An array of 100K Ints
  withMeasurer(new Measurer.MemoryFootprint) measure { (0 until 100000).toArray }
                                                  //> res10: Double = 383.368
  
  //An array of 1M intergers - 4MB of memory, which is 4 bytes/int * 1e6 ints                                                  //An array of 100K Ints
  withMeasurer(new Measurer.MemoryFootprint) measure { (0 until 1000000).toArray }
                                                  //> res11: Double = 3911.72
  withMeasurer(new Measurer.MemoryFootprint) measure { (0 until 1000000).toArray }
                                                  //> res12: Double = 3999.544
  withMeasurer(new Measurer.MemoryFootprint) measure { (0 until 1000000).toArray }
                                                  //> res13: Double = 3999.544

	withMeasurer(new Measurer.Default) measure { (0 until 1000000).toArray }
                                                  //> res14: Double = 4.610257
	
	//Running time without Garbage Collector pauses
	withMeasurer(new Measurer.IgnoringGC) measure { (0 until 1000000).toArray }
                                                  //> res15: Double = 4.823993
	


}