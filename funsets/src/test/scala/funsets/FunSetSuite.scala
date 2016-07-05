package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(-1000)
    val s5 = singletonSet(1000)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  test("interset contains elements shared by sets") {
    new TestSets {
      val s = intersect(s1, s1)
      val t = intersect(s1, s2)
      val u = intersect(s2, s2)
      assert(contains(s, 1), "Intersect 1")
      assert(!contains(t, 1), "Intersect 2")
      assert(!contains(t, 2), "Intersect 3")
      assert(contains(u, 2), "Intersect 4")
    }
  }
  test("diff gives all elements of the first set which are not in the second set") {
    new TestSets {
      val s = union(s1, s2)
      val t = union(s2, s3)
      val u = union(s1, s3)
      val test1 = diff(s, t)
      val test2 = diff(t, s)
      val test3 = diff(u, s)
      assert(contains(test1, 1), "Diff 1")
      assert(!contains(test1, 2), "Diff 2")
      assert(!contains(test1, 3), "Diff 3")
      assert(contains(test2, 3), "Diff 4")
      assert(!contains(test2, 2), "Diff 5")
      assert(!contains(test2, 1), "Diff 6")
      assert(contains(test3, 3), "Diff 7")
      assert(!contains(test3, 2), "Diff 8")
      assert(!contains(test3, 1), "Diff 9")
    }
  }
  test("filter lets through elements of a set which passes a predicate") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      val t = union(s2, s3)
      val test1 = filter(s, _ > 1)
      val test2 = filter(s, _ > 2)
      val test3 = filter(t, _ == 2)
      assert(!contains(test1, 1), "filter 1")
      assert(contains(test1, 2), "filter 2")
      assert(contains(test1, 3), "filter 3")
      assert(!contains(test2, 1), "filter 4")
      assert(!contains(test2, 2), "filter 5")
      assert(contains(test2, 3), "filter 6")
      assert(!contains(test3, 1), "filter 7")
      assert(contains(test3, 2), "filter 8")
      assert(!contains(test3, 3), "filter 9")
    }
  }
  test("forall tests whether all elements in a set passes a test") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      val t = union(union(s4, s5), s)
      val u = union(s2, union(s4, s5))
      assert(forall(s, _ < 1000), "forall 1")
      assert(forall(s, _ > 0), "forall 2")
      assert(!forall(s, _ > 3), "forall 3")
      assert(!forall(t, _ < 1000), "forall 4")
      assert(!forall(t, _ > 0), "forall 5")
      assert(forall(s1, _ == 1), "forall 6")
      assert(forall(union(s4,s4), _ == -1000), "forall 7")
      assert(forall(union(s5,s5), _ == 1000), "forall 8")
      assert(!forall(s, _ % 2 ==0), "forall 9")
      assert(forall(u, _ % 2 == 0), "forall 10")
    }
  }
  test("exists tests whether any element in a set passes a test") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      val t = union(union(s4, s5), s)
      val u = union(s2, union(s4, s5))
      assert(exists(s, _ == 3), "exists 1")
      assert(exists(s4, _ < 0), "exists 2")
      assert(!exists(s, _ == -1000), "exists 3")
      assert(exists(t, _ == -1000), "exists 4")
      assert(exists(s, _ % 2 == 0), "exists 5")
      assert(!exists(u, _ % 2 == 1), "exists 6")
    }
  }
  test("map transforms a set to another set") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      val t = union(union(s4, s5), s)
      val u = union(s2, union(s4, s5))
      assert(!contains(map(s, _ + 1), 1), "map 1")
      assert(contains(map(s, _ + 1), 2), "map 2")
      assert(contains(map(s, _ + 1), 3), "map 3")
      assert(contains(map(s, _ + 1), 4), "map 4")
      assert(!contains(map(s, _ + 1), 5), "map 5")

    }
  }


}
