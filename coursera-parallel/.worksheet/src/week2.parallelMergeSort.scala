package week2

import org.scalameter._
import common._

object parallelMergeSort {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(507); 

 // a bit of reflection to access the private sort1 method, which takes an offset and an argument
  val sort1 = {
    val method = scala.util.Sorting.getClass.getDeclaredMethod("sort1", classOf[Array[Int]], classOf[Int], classOf[Int])
    method.setAccessible(true)
    (xs: Array[Int], offset: Int, len: Int) => {
      method.invoke(scala.util.Sorting, xs, offset.asInstanceOf[AnyRef], len.asInstanceOf[AnyRef])
    }
  };System.out.println("""sort1  : (Array[Int], Int, Int) => Object = """ + $show(sort1 ));$skip(103); 

  def quickSort(xs: Array[Int], offset: Int, length: Int): Unit = {
    sort1(xs, offset, length)
  };System.out.println("""quickSort: (xs: Array[Int], offset: Int, length: Int)Unit""");$skip(28); 

  var dummy: AnyRef = null;System.out.println("""dummy  : AnyRef = """ + $show(dummy ));$skip(2959); 

  def parMergeSort(xs: Array[Int], maxDepth: Int): Unit = {
    // 1) Allocate a helper array.
    // This step is a bottleneck, and takes:
    // - ~76x less time than a full quickSort without GCs (best time)
    // - ~46x less time than a full quickSort with GCs (average time)
    // Therefore:
    // - there is a almost no performance gain in executing allocation concurrently to the sort
    // - doing so would immensely complicate the algorithm
    val ys = new Array[Int](xs.length)
    dummy = ys

    // 2) Sort the elements.
    // The merge step has to do some copying, and is the main performance bottleneck of the algorithm.
    // This is due to the final merge call, which is a completely sequential pass.
    def merge(src: Array[Int], dst: Array[Int], from: Int, mid: Int, until: Int) {
      var left = from
      var right = mid
      var i = from
      while (left < mid && right < until) {
        while (left < mid && src(left) <= src(right)) {
          dst(i) = src(left)
          i += 1
          left += 1
        }
        while (right < until && src(right) <= src(left)) {
          dst(i) = src(right)
          i += 1
          right += 1
        }
      }
      while (left < mid) {
        dst(i) = src(left)
        i += 1
        left += 1
      }
      while (right < until) {
        dst(i) = src(right)
        i += 1
        right += 1
      }
    }
    // Without the merge step, the sort phase parallelizes almost linearly.
    // This is because the memory pressure is much lower than during copying in the third step.
    def sort(from: Int, until: Int, depth: Int): Unit = {
      if (depth == maxDepth) {
        quickSort(xs, from, until - from)
      } else {
        val mid = (from + until) / 2
        parallel(sort(mid, until, depth + 1), sort(from, mid, depth + 1))
        
        val flip = (maxDepth - depth) % 2 == 0
        val src = if (flip) ys else xs
        val dst = if (flip) xs else ys
        merge(src, dst, from, mid, until)
      }
    }
		sort(0, xs.length, 0)

    // 3) In parallel, copy the elements back into the source array.
    // Executed sequentially, this step takes:
    // - ~23x less time than a full quickSort without GCs (best time)
    // - ~16x less time than a full quickSort with GCs (average time)
    // There is a small potential gain in parallelizing copying.
    // However, most Intel processors have a dual-channel memory controller,
    // so parallel copying has very small performance benefits.
    def copy(src: Array[Int], target: Array[Int], from: Int, until: Int, depth: Int): Unit = {
      if (depth == maxDepth) {
        Array.copy(src, from, target, from, until - from)
      } else {
        val mid = from + (until - from) / 2
        val right = parallel (
          copy(src, target, mid, until, depth + 1),
          copy(src, target, from, mid, depth + 1)
        )
      }
    }
    if (maxDepth % 2 != 0) copy(ys, xs, 0, xs.length, 0)
  };System.out.println("""parMergeSort: (xs: Array[Int], maxDepth: Int)Unit""");$skip(189); 

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 60,
    Key.exec.benchRuns -> 60,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default);System.out.println("""standardConfig  : org.scalameter.MeasureBuilder[Unit,Double] = """ + $show(standardConfig ));$skip(123); 

  def initialize(xs: Array[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i % 100
      i += 1
    }
  };System.out.println("""initialize: (xs: Array[Int])Unit""");$skip(26); 
  
  val length = 1000000;System.out.println("""length  : Int = """ + $show(length ));$skip(18); 
	val maxDepth = 7;System.out.println("""maxDepth  : Int = """ + $show(maxDepth ));$skip(33); 
	val xs = new Array[Int](length);System.out.println("""xs  : Array[Int] = """ + $show(xs ));$skip(123); 
  
    
  val seqtime = standardConfig setUp {
      _ => initialize(xs)
    } measure {
      quickSort(xs, 0, xs.length)
    };System.out.println("""seqtime  : Double = """ + $show(seqtime ));$skip(55); 
    println(s"sequential sum time: $seqtime ms");$skip(117); 

    val partime = standardConfig setUp {
      _ => initialize(xs)
    } measure {
      parMergeSort(xs, maxDepth)
    };System.out.println("""partime  : Double = """ + $show(partime ));$skip(50); 
    println(s"fork/join time: $partime ms");$skip(46); 
    println(s"speedup: ${seqtime / partime}")}




}
