package week2

import common._

object task {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(313); 
  def sumSegment(a: Array[Int], p: Double, s: Int, t: Int): Int = {
  
  	def add(a: Array[Int], p: Double, s: Int, t: Int, sum: Int): Int = {
  		if(s >= t || s>=a.length) sum
  		else add(a, p, s+1, t, sum + raiseToPower(a(s), p))
  	}
  	
  	add(a, p, s, t, 0)
  };System.out.println("""sumSegment: (a: Array[Int], p: Double, s: Int, t: Int)Int""");$skip(75); 
  def raiseToPower(a: Int, p: Double): Int = Math.pow(math.abs(a),p).toInt;System.out.println("""raiseToPower: (a: Int, p: Double)Int""");$skip(49); val res$0 = 
  parallel(
  task { sumSegment(a, p, 0, mid1) };System.out.println("""res0: <error> = """ + $show(res$0))}
}
