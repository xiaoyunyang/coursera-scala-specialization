package week3

//sbt > RunMain week3.Conversion
import scala.collection._
import org.scalameter._

object Conversion {
  
  val standardConfig = config(
    Key.exec.minWarmupRuns -> 10,
    Key.exec.maxWarmupRuns -> 20,
    Key.exec.benchRuns -> 20,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  val array = Array.fill(10000000)("")
  val list = array.toList
  val vector = Vector.fill(10000000)("")

  def main(args: Array[String]) {
    val arraytime = standardConfig measure {
      array.par
    }
    println(s"array conversion time: $arraytime ms")
    val listtime = standardConfig measure {
      list.par
    }
    println(s"list conversion time: $listtime ms")
    println(s"difference: ${listtime / arraytime}")
    
    val vectortime = standardConfig measure {
      vector.par
    }
    println(s"vector conversion time: $vectortime ms")
    println(s"difference: ${vectortime / arraytime}")

  }
  
}
