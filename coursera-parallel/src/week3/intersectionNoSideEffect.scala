package week3
import scala.collection._
import org.scalameter._

/** Use the correct combinator to solve the problem using
 *  pure functions - no side effect means full data-parallel
 *  operation
 *  
 */
object intersectionNoSideEffect {
  def main(args: Array[String]) {
    /* 
     * this program is correct for sequential set, but not for parallel sets
     */
    def intersection(a: GenSet[Int], b: GenSet[Int]): GenSet[Int] = {
      if(a.size < b.size) a.filter(b(_))
      else b.filter(a(_))
    }                                    
    
    val seqRes = intersection((0 until 1000).toSet, (0 until 1000 by 4).toSet)
    val parRes = intersection((0 until 1000).par.toSet, (0 until 1000 by 4).par.toSet)
    
    log(s"Sequential result - ${seqRes.size}")
    log(s"Parallel result - ${parRes.size}") 
  }
   
}