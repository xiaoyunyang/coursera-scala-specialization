package week2

import org.scalameter._
import common._

object parallelMergeSort {

 // a bit of reflection to access the private sort1 method, which takes an offset and an argument
  val sort1 = {
    val method = scala.util.Sorting.getClass.getDeclaredMethod("sort1", classOf[Array[Int]], classOf[Int], classOf[Int])
    method.setAccessible(true)
    (xs: Array[Int], offset: Int, len: Int) => {
      method.invoke(scala.util.Sorting, xs, offset.asInstanceOf[AnyRef], len.asInstanceOf[AnyRef])
    }
  }                                               //> sort1  : (Array[Int], Int, Int) => Object = <function3>

  def quickSort(xs: Array[Int], offset: Int, length: Int): Unit = {
    sort1(xs, offset, length)
  }                                               //> quickSort: (xs: Array[Int], offset: Int, length: Int)Unit

  var dummy: AnyRef = null                        //> dummy  : AnyRef = null

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
  }                                               //> parMergeSort: (xs: Array[Int], maxDepth: Int)Unit

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 60,
    Key.exec.benchRuns -> 60,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)                //> standardConfig  : org.scalameter.MeasureBuilder[Unit,Double] = org.scalamet
                                                  //| er.MeasureBuilder@527740a2

  def initialize(xs: Array[Int]) {
    var i = 0
    while (i < xs.length) {
      xs(i) = i % 100
      i += 1
    }
  }                                               //> initialize: (xs: Array[Int])Unit
  
  val length = 1000000                            //> length  : Int = 1000000
	val maxDepth = 7                          //> maxDepth  : Int = 7
	val xs = new Array[Int](length)           //> xs  : Array[Int] = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0
                                                  //| Output exceeds cutoff limit.
  
    
  val seqtime = standardConfig setUp {
      _ => initialize(xs)
    } measure {
      quickSort(xs, 0, xs.length)                 //> Starting warmup.
                                                  //| 0. warmup run running time: 39.131101 (covNoGC: NaN, covGC: NaN)
                                                  //| 1. warmup run running time: 16.930089 (covNoGC: 0.5600, covGC: 0.5600)
                                                  //| 2. warmup run running time: 17.451098 (covNoGC: 0.5171, covGC: 0.5171)
                                                  //| 3. warmup run running time: 16.639757 (covNoGC: 0.4910, covGC: 0.4910)
                                                  //| 4. warmup run running time: 16.250952 (covNoGC: 0.4694, covGC: 0.4694)
                                                  //| 5. warmup run running time: 16.868602 (covNoGC: 0.4436, covGC: 0.4436)
                                                  //| 6. warmup run running time: 16.27957 (covNoGC: 0.4251, covGC: 0.4251)
                                                  //| 7. warmup run running time: 29.910165 (covNoGC: 0.4061, covGC: 0.4061)
                                                  //| 8. warmup run running time: 19.36601 (covNoGC: 0.3846, covGC: 0.3846)
                                                  //| 9. warmup run running time: 16.187805 (covNoGC: 0.3784, covGC: 0.3784)
                                                  //| 10. warmup run running time: 16.576054 (covNoGC: 0.3700, covGC: 0.3700)
                                                  //| 11. warmup run running time: 16.479418 (covNoGC: 0.3622, covGC: 0.3622)
                                                  //| 12. warmup run running time: 16.968207 (covNoGC: 0.3530, covGC: 0.3
                                                  //| Output exceeds cutoff limit.
    }
    println(s"sequential sum time: $seqtime ms")  //> sequential sum time: 16.56193205 ms

    val partime = standardConfig setUp {
      _ => initialize(xs)
    } measure {
      parMergeSort(xs, maxDepth)                  //> Starting warmup.
                                                  //| 0. warmup run running time: 78.120126 (covNoGC: NaN, covGC: NaN)
                                                  //| 1. warmup run running time: 8.572197 (covNoGC: 1.1345, covGC: 1.1345)
                                                  //| 2. warmup run running time: 9.521993 (covNoGC: 1.2435, covGC: 1.2435)
                                                  //| 3. warmup run running time: 7.666594 (covNoGC: 1.3390, covGC: 1.3390)
                                                  //| 4. warmup run running time: 7.0983 (covNoGC: 1.4091, covGC: 1.4091)
                                                  //| 5. warmup run running time: 7.935461 (covNoGC: 1.4417, covGC: 1.4417)
                                                  //| 6. warmup run running time: 7.150822 (covNoGC: 1.4726, covGC: 1.4726)
                                                  //| 7. warmup run running time: 7.429269 (covNoGC: 1.4884, covGC: 1.4884)
                                                  //| 8. warmup run running time: 8.511786 (covNoGC: 1.4825, covGC: 1.4825)
                                                  //| 9. warmup run running time: 7.655346 (covNoGC: 1.4835, covGC: 1.4835)
                                                  //| 10. warmup run running time: 8.540591 (covNoGC: 1.4707, covGC: 1.4707)
                                                  //| 11. warmup run running time: 6.606085 (covNoGC: 1.4775, covGC: 1.4775)
                                                  //| 12. warmup run running time: 7.106891 (covNoGC: 1.4757, covGC: 1.4757)
                                                  //| 13
                                                  //| Output exceeds cutoff limit.
    }
    println(s"fork/join time: $partime ms")       //> fork/join time: 8.017398850000001 ms
    println(s"speedup: ${seqtime / partime}")     //> speedup: 2.0657487995623414




}