package parallel


object computation {
	
	
	def parallel[A, B](taskA: => A, taskB: => B): (A,B) = (taskA, taskB)
                                                  //> parallel: [A, B](taskA: => A, taskB: => B)(A, B)
 	//val myTask = new MyTask
  //def parallel[A, B](taskA: => A, taskB: => B): (A,B) = 	myTask.parallel(taskA, taskB)
 
  def raiseToPower(a: Int, p: Double): Int = Math.pow(math.abs(a),p).toInt
                                                  //> raiseToPower: (a: Int, p: Double)Int

  def sumSegment(a: Array[Int], p: Double, s: Int, t: Int): Int = {
  
  	def add(a: Array[Int], p: Double, s: Int, t: Int, sum: Int): Int = {
  		if(s >= t || s>=a.length) sum
  		else add(a, p, s+1, t, sum + raiseToPower(a(s), p))
  	}
  	
  	add(a, p, s, t, 0)
  }                                               //> sumSegment: (a: Array[Int], p: Double, s: Int, t: Int)Int
  
	def pNorm(a: Array[Int], p: Double): Int = raiseToPower(sumSegment(a, p, 0, a.length), 1/p)
                                                  //> pNorm: (a: Array[Int], p: Double)Int
		
  def pNormTwoPart(a: Array[Int], p: Double): Int = {
  	val m = a.length/2
  	val (sum1, sum2) = (sumSegment(a, p, 0, m), sumSegment(a, p, m, a.length))
  	
  	raiseToPower(sum1+sum2, 1/p)
  }                                               //> pNormTwoPart: (a: Array[Int], p: Double)Int
  
  
  val threshold = 0                               //> threshold  : Int = 0
  def segmentRec(a: Array[Int], p: Double, s: Int, t: Int): Int = {
  	if(t-s < threshold)
  		sumSegment(a,p,s,t)
  	else {
  		val m = s + (t-s)/2
  		val (sum1, sum2) = parallel(segmentRec(a,p,s,m), segmentRec(a,p,m,t))
  		
  		sum1+sum2
  	}
  }                                               //> segmentRec: (a: Array[Int], p: Double, s: Int, t: Int)Int
  
  def pNormTwoPartParallel(a: Array[Int], p: Double): Int = {
  	val m = a.length/2
  	val (sum1, sum2) = parallel(sumSegment(a, p, 0, m), sumSegment(a,p,m,a.length))
  	
  	raiseToPower(sum1+sum2, 1/p)
  }                                               //> pNormTwoPartParallel: (a: Array[Int], p: Double)Int
  
  def pNormFourPartParallel(a: Array[Int], p: Double): Int = {
	  val mid1 = a.length/4
	  val mid2 = a.length/2
	  val mid3 = a.length/2+a.length/4
	  
	  val((part1, part2), (part3, part4)) =
	  	parallel(
	  		parallel(sumSegment(a, p, 0, 0), sumSegment(a, p, mid1, mid2)),
	  		parallel(sumSegment(a, p, mid2, mid3), sumSegment(a, p, mid3, a.length)))
	  raiseToPower(part1+part2+part3+part4, 1/p)
  }                                               //> pNormFourPartParallel: (a: Array[Int], p: Double)Int
  
  
  
  
  //TEST
  val (a, p) = (Array(2,3), 1)                    //> a  : Array[Int] = Array(2, 3)
                                                  //| p  : Int = 1
  pNorm(a,p)                                      //> res0: Int = 5
  pNormTwoPart(a,p)                               //> res1: Int = 5
  pNormTwoPartParallel(a,p)                       //> res2: Int = 5
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
	}                                         //> sumSegment2: (a: Array[Int], p: Double, s: Int, t: Int)Int
	def power(x: Int, p: Double): Int = math.exp(p*math.log(math.abs(x))).toInt
                                                  //> power: (x: Int, p: Double)Int
  
	

	
}