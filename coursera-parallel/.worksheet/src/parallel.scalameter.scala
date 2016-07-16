package parallel

import org.scalameter._

//Using ScalaMeter to benchmark
object scalameter {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(267); val res$0 = 

	/** Measuring run time in ms **/
  //first program is interpreted, then part of the program are compiled into machine language
  measure {
  	(0 until 100000).toArray
  };System.out.println("""res0: Double = """ + $show(res$0));$skip(84); val res$1 = 
  
  //JVM dynamic optimization kicks in
	measure {
  	(0 until 100000).toArray
  };System.out.println("""res1: Double = """ + $show(res$1));$skip(77); val res$2 = 
  
  //Garbage collector kicks in
	measure {
  	(0 until 100000).toArray
  };System.out.println("""res2: Double = """ + $show(res$2));$skip(47); val res$3 = 
  
 	measure {
  	(0 until 100000).toArray
  };System.out.println("""res3: Double = """ + $show(res$3));$skip(259); val res$4 = 
  
  //... eventually reaches some steady staet
  
  /** Measuring steady state run time **/
  //Measuring steady-state program performance - the actual steady state is between an interval
  withWarmer(new Warmer.Default) measure { (0 until 100000).toArray };System.out.println("""res4: Double = """ + $show(res$4));$skip(69); val res$5 = 
	withWarmer(new Warmer.Default) measure { (0 until 100000).toArray };System.out.println("""res5: Double = """ + $show(res$5));$skip(69); val res$6 = 
	withWarmer(new Warmer.Default) measure { (0 until 100000).toArray };System.out.println("""res6: Double = """ + $show(res$6));$skip(69); val res$7 = 
	withWarmer(new Warmer.Default) measure { (0 until 100000).toArray };System.out.println("""res7: Double = """ + $show(res$7));$skip(69); val res$8 = 
	withWarmer(new Warmer.Default) measure { (0 until 100000).toArray };System.out.println("""res8: Double = """ + $show(res$8));$skip(69); val res$9 = 
	withWarmer(new Warmer.Default) measure { (0 until 100000).toArray };System.out.println("""res9: Double = """ + $show(res$9));$skip(132); 
                                                  
 	
 	val t = withWarmer(new Warmer.Default) measure { (0 until 100000).toArray };System.out.println("""t  : Double = """ + $show(t ));$skip(47); 
 	println(s"Array initialization time: $t ms");$skip(289); val res$10 = 
 	
 	                                               
  /** Measuring memory footprint **/
  //Memory footprint measure the total amount of memory occupied by the object returned
  
  //An array of 100K Ints
  withMeasurer(new Measurer.MemoryFootprint) measure { (0 until 100000).toArray };System.out.println("""res10: Double = """ + $show(res$10));$skip(234); val res$11 = 
  //An array of 1M intergers - 4MB of memory, which is 4 bytes/int * 1e6 ints                                                  //An array of 100K Ints
  withMeasurer(new Measurer.MemoryFootprint) measure { (0 until 1000000).toArray };System.out.println("""res11: Double = """ + $show(res$11));$skip(83); val res$12 = 
  withMeasurer(new Measurer.MemoryFootprint) measure { (0 until 1000000).toArray };System.out.println("""res12: Double = """ + $show(res$12));$skip(83); val res$13 = 
  withMeasurer(new Measurer.MemoryFootprint) measure { (0 until 1000000).toArray };System.out.println("""res13: Double = """ + $show(res$13))}
}
