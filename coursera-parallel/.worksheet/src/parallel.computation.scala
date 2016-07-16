package parallel


object computation {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(113); 
	
	
	def parallel[A, B](taskA: => A, taskB: => B): (A,B) = (taskA, taskB);System.out.println("""parallel: [A, B](taskA: => A, taskB: => B)(A, B)""");$skip(194); 
 	//val myTask = new MyTask
  //def parallel[A, B](taskA: => A, taskB: => B): (A,B) = 	myTask.parallel(taskA, taskB)
 
  def raiseToPower(a: Int, p: Double): Int = Math.pow(math.abs(a),p).toInt;System.out.println("""raiseToPower: (a: Int, p: Double)Int""");$skip(269); 

  def sumSegment(a: Array[Int], p: Double, s: Int, t: Int): Int = {
  
  	def add(a: Array[Int], p: Double, s: Int, t: Int, sum: Int): Int = {
  		if(s >= t || s>=a.length) sum
  		else add(a, p, s+1, t, sum + raiseToPower(a(s), p))
  	}
  	
  	add(a, p, s, t, 0)
  };System.out.println("""sumSegment: (a: Array[Int], p: Double, s: Int, t: Int)Int""");$skip(96); 
  
	def pNorm(a: Array[Int], p: Double): Int = raiseToPower(sumSegment(a, p, 0, a.length), 1/p);System.out.println("""pNorm: (a: Array[Int], p: Double)Int""");$skip(197); 
		
  def pNormTwoPart(a: Array[Int], p: Double): Int = {
  	val m = a.length/2
  	val (sum1, sum2) = (sumSegment(a, p, 0, m), sumSegment(a, p, m, a.length))
  	
  	raiseToPower(sum1+sum2, 1/p)
  };System.out.println("""pNormTwoPart: (a: Array[Int], p: Double)Int""");$skip(26); 
  
  
  val threshold = 0;System.out.println("""threshold  : Int = """ + $show(threshold ));$skip(251); 
  def segmentRec(a: Array[Int], p: Double, s: Int, t: Int): Int = {
  	if(t-s < threshold)
  		sumSegment(a,p,s,t)
  	else {
  		val m = s + (t-s)/2
  		val (sum1, sum2) = parallel(segmentRec(a,p,s,m), segmentRec(a,p,m,t))
  		
  		sum1+sum2
  	}
  };System.out.println("""segmentRec: (a: Array[Int], p: Double, s: Int, t: Int)Int""");$skip(210); 
  
  def pNormTwoPartParallel(a: Array[Int], p: Double): Int = {
  	val m = a.length/2
  	val (sum1, sum2) = parallel(sumSegment(a, p, 0, m), sumSegment(a,p,m,a.length))
  	
  	raiseToPower(sum1+sum2, 1/p)
  };System.out.println("""pNormTwoPartParallel: (a: Array[Int], p: Double)Int""");$skip(409); 
  
  def pNormFourPartParallel(a: Array[Int], p: Double): Int = {
	  val mid1 = a.length/4
	  val mid2 = a.length/2
	  val mid3 = a.length/2+a.length/4
	  
	  val((part1, part2), (part3, part4)) =
	  	parallel(
	  		parallel(sumSegment(a, p, 0, 0), sumSegment(a, p, mid1, mid2)),
	  		parallel(sumSegment(a, p, mid2, mid3), sumSegment(a, p, mid3, a.length)))
	  raiseToPower(part1+part2+part3+part4, 1/p)
  };System.out.println("""pNormFourPartParallel: (a: Array[Int], p: Double)Int""");$skip(52); 
  
  
  
  
  //TEST
  val (a, p) = (Array(2,3), 1);System.out.println("""a  : Array[Int] = """ + $show(a ));System.out.println("""p  : Int = """ + $show(p ));$skip(13); val res$0 = 
  pNorm(a,p);System.out.println("""res0: Int = """ + $show(res$0));$skip(20); val res$1 = 
  pNormTwoPart(a,p);System.out.println("""res1: Int = """ + $show(res$1));$skip(28); val res$2 = 
  pNormTwoPartParallel(a,p);System.out.println("""res2: Int = """ + $show(res$2));$skip(218); 
  //pNormFourPartParallel(a,p)
  
  
  /*** Bad ***/

	def sumSegment2(a: Array[Int], p: Double, s: Int, t: Int): Int = {
		var i=s;
		var sum: Int = 0
		while(i<t) {
			sum = sum + power(a(i),p)
			i +=1
		}
		sum
	};System.out.println("""sumSegment2: (a: Array[Int], p: Double, s: Int, t: Int)Int""");$skip(77); 
	def power(x: Int, p: Double): Int = math.exp(p*math.log(math.abs(x))).toInt;System.out.println("""power: (x: Int, p: Double)Int""")}
  
	

	
}
