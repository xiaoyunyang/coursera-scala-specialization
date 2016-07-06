package week1

/** This is the complete generators example, the simplified version for the
 *  exam is in the other file.
 */
object Generators {

  trait Generator[+T] {
    self =>
    def generate: T
    def foreach[U](f: T => U) {
      f(generate)
    }
    def map[S](f: T => S): Generator[S] = new Generator[S] {
      def generate = f(self.generate)
    }
    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      def generate = f(self.generate).generate
    }
  }

  val integers = new Generator[Int] {
    def generate = scala.util.Random.nextInt()
  }

  val booleans = integers.map(_ >= 0)

  def choose(from: Int, to: Int) = new Generator[Int] {
    def generate = if (from == to) from else scala.util.Random.nextInt(to - from) + from
  }

  def single[T](x: T) = new Generator[T] {
    def generate = x
  }

  def pairs[T, U](t: Generator[T], u: Generator[U]): Generator[(T, U)] = for {
    x <- t
    y <- u
  } yield (x, y)

  val pairDesugared: Generator[(Int, Int)] = integers flatMap {
    x => integers map { y => (x, y) }
  }

  def test[T](r: Generator[T], noTimes: Int = 100)(test: T => Boolean) {
    for (_ <- 0 until noTimes) {
      val value = r.generate
      assert(test(value), "Test failed for: "+value)
    }
    println("Test passed "+noTimes+" times")
  }

  def triangles(width: Int): Generator[(Int, Int)] = for {
    x <- choose(0, width)
    y <- choose(0, x)
  } yield (x, y)

  def emptyLists = single(Nil) // have them implement these

  def nonEmptyLists = for {
    head <- integers
    tail <- lists
  } yield head :: tail

  def lists: Generator[List[Int]] = for {
    cutoff <- booleans
    list <- if (cutoff) emptyLists else nonEmptyLists
  } yield list

  trait Tree
  case class Inner(left: Tree, right: Tree) extends Tree
  case class Leaf(x: Int) extends Tree

  def leafs: Generator[Leaf] = for {
    x <- integers
  } yield Leaf(x)

  def inners: Generator[Inner] = for {
    l <- trees
    r <- trees
  } yield Inner(l, r)

  def trees: Generator[Tree] = for {
    cutoff <- booleans
    tree <- if (cutoff) leafs else inners
  } yield tree

  def main(args: Array[String]) {
    println(triangles(20).generate)
    println(lists.generate)
    println(trees.generate)
    test(pairs(lists, lists)) {
      case (xs, ys) => (xs ::: ys).length > xs.length
    }
  }

}
