package week3

import scala.collection._
import org.scalameter._

/** Demonstrates how mutation causes non-determinism in parallel programs 
 *  If you are going to use data parallelism, make sure to synchronize!
 *  Avoid mutations to the same memory locations wihout proper synchronization.
 */
object IntersectionWrong {
  def main(args: Array[String]) {
    /* 
     * this program is correct for sequential set, but not for parallel sets
     */
    def intersection(a: GenSet[Int], b: GenSet[Int]): Set[Int] = {
      val result = mutable.Set[Int]()
      for(x <- a) if (b contains x) result += x //<- mutation without synchronization bad!
      result
    }                                    
    //The reason this works is because both a sequential set collection and a parallel set
    //collection are a subtype of generic collection
    val seqRes = intersection((0 until 1000).toSet, (0 until 1000 by 4).toSet)
    val parRes = intersection((0 until 1000).par.toSet, (0 until 1000 by 4).par.toSet)
    
    log(s"Sequential result - ${seqRes.size}")
    
    //every time you run the program, this returns a different number!
     
    log(s"Parallel result - ${parRes.size}") 
  }
        
}
