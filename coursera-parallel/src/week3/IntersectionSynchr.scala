package week3

import scala.collection._
import java.util.concurrent._
import scala.collection.convert.wrapAsScala._
import java.util.concurrent._
import org.scalameter._

/** 
 *  solution to the mutation without synchronization problem is to 
 *  use a concurrent collection, which can be mutated by multiple threads
 */

object IntersectionSynchr {
  def main(args: Array[String]) {
    
    def intersection(a: GenSet[Int], b: GenSet[Int]): Set[Int] = {
      val result = new ConcurrentSkipListSet[Int]()
      for(x <- a) if (b contains x) result += x //<- mutation without synchronization bad!
      result
    }    
    val seqRes = intersection((0 until 1000).toSet, (0 until 1000 by 4).toSet)
    val parRes = intersection((0 until 1000).par.toSet, (0 until 1000 by 4).par.toSet)
    
    log(s"Sequential result - ${seqRes.size}") 
    log(s"Parallel result - ${parRes.size}") //Now this is correct everytime
  }
}