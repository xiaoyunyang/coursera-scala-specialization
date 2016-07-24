package week3

import scala.collection._

/** Anti-example: Never modify a parallel collection on 
 *  which a data-parallel operation is in progress
 */
object ParallelGraphContraction {

  
  def main(args: Array[String]) {
    val graph = mutable.Map[Int, Int]() ++= (0 until 100000).map(i => (i, i + 1))
    graph(graph.size - 1) = 0
    
    /* The following line of code has two errors
     * Error #1 modifying the same collection which is being traversed in parallel
     * Error #2 reading from the same collection which is being modified by some other 
     * iteration of the loop.
     * The mutable Map is not thread safe.
     *  Never write to a collection that is concurrently traversed
     *  Never read from a collection that is concurrently modified
     */
    for ((k, v) <- graph.par) graph(k) = graph(v) 
    
    val violation = graph.find({ case (i, v) => v != (i + 2) % graph.size })
    println(s"violation: $violation")
  }
}
