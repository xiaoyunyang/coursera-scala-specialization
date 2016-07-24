package week3

import scala.collection._

/** TrieMap has the snapshot method, which makes a copy of the state
 *  At any moment, there are three graphs (1) the original graph, (2) graph.par
 *  and (3) snapshot graph (previous), which are created in constant time.
 */
object ParallelTrieMapGraphContraction {

  def main(args: Array[String]) {
    val graph = concurrent.TrieMap[Int, Int]() ++= (0 until 100000).map(i => (i, i + 1))
    graph(graph.size - 1) = 0
    
    val previous = graph.snapshot() //<- graph.snapshot() grabs the current state. The snapshot 
    //creates a copy of the graph
    
    for ((k, v) <- graph.par) graph(k) = previous(v)
    val violation = graph.find({ case (i, v) => v != (i + 2) % graph.size })
    println(s"violation: $violation")
  }

}
