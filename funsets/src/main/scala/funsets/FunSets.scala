package funsets


/**
 * 2. Purely Functional Sets.
 */
object FunSets {
  /**
   * We represent a set by its characteristic function, i.e.
   * its `contains` predicate.
   */
  type Set = Int => Boolean //Set is a function which maps from Int to Boolean. Set has a function type

  /**
   * Indicates whether a set contains a given element.
   */
  def contains(s: Set, elem: Int): Boolean = s(elem)

  /**
   * Returns the set of the one given element.
   */
  def singletonSet(elem: Int): Set = (x => x==elem) 
  
  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   */
  def union(s: Set, t: Set): Set = {
    (elem: Int) => contains(s, elem) || contains(t, elem)
  }
  
  
  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` and `t`.
   */
  def intersect(s: Set, t: Set): Set = {
    (elem: Int) => contains(s, elem) && contains(t, elem)
  }
  
  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
  def diff(s: Set, t: Set): Set = {
    (elem: Int) => contains(s, elem) && !contains(t, elem)
  }
  
  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: Set, p: Int => Boolean): Set = {
    (elem: Int) => contains(s, elem) && p(elem)
  }
  

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: Set, p: Int => Boolean): Boolean = {
    @annotation.tailrec
    def iter(a: Int, acc: Boolean): Boolean = {
      if (a > bound) acc
      else if (contains(s, a)) iter(a+1, p(a) && acc)
      else iter(a+1, acc)
    }
    iter(-bound, true)
  }
  
  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: Set, p: Int => Boolean): Boolean = {
    def g = (elem: Int) => !p(elem)
    !forall(s, g) // equivalent to this solution: !forall(s, x => !p(x))
  }
  
  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   * Remember: a Set is a mapping from Int to Boolean!
   */
  def map(s: Set, f: Int => Int): Set = {
    (elem: Int) => exists(s, x => f(x) == elem)
  }
  
  /**
   * Displays the contents of a set
   */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: Set) {
    println(toString(s))
  }
}
