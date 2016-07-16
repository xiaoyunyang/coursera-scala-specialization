package week6
import nqueens._


/** Sets are unordered, do not have duplicate elements, and the fundamental operation for Set
 * is "contains".
 */
object Sets {
  val fruit = Set("apple", "banana", "pear")      //> fruit  : scala.collection.immutable.Set[String] = Set(apple, banana, pear)
  val s = (1 to 6).toSet                          //> s  : scala.collection.immutable.Set[Int] = Set(5, 1, 6, 2, 3, 4)
  
  s map (_ + 2) // Set(3,4,5,6,7,8)               //> res0: scala.collection.immutable.Set[Int] = Set(5, 6, 7, 3, 8, 4)
  fruit filter (_.startsWith("app"))              //> res1: scala.collection.immutable.Set[String] = Set(apple)
  s.nonEmpty                                      //> res2: Boolean = true
  

	/** Test nqueens */
	queens(4)                                 //> res3: Set[List[Int]] = Set(List(1, 3, 0, 2), List(2, 0, 3, 1))
  queens(4) map show                              //> res4: scala.collection.immutable.Set[String] = Set("
                                                  //| **X *
                                                  //| X ***
                                                  //| ***X 
                                                  //| *X **", "
                                                  //| *X **
                                                  //| ***X 
                                                  //| X ***
                                                  //| **X *")
  (queens(4) map show) mkString "\n"              //> res5: String = "
                                                  //| **X *
                                                  //| X ***
                                                  //| ***X 
                                                  //| *X **
                                                  //| 
                                                  //| *X **
                                                  //| ***X 
                                                  //| X ***
                                                  //| **X *"
  (queens(8) take 3 map show) mkString "\n"       //> res6: String = "
                                                  //| *****X **
                                                  //| ***X ****
                                                  //| *X ******
                                                  //| *******X 
                                                  //| ****X ***
                                                  //| ******X *
                                                  //| X *******
                                                  //| **X *****
                                                  //| 
                                                  //| ****X ***
                                                  //| ******X *
                                                  //| *X ******
                                                  //| ***X ****
                                                  //| *******X 
                                                  //| X *******
                                                  //| **X *****
                                                  //| *****X **
                                                  //| 
                                                  //| *****X **
                                                  //| **X *****
                                                  //| ******X *
                                                  //| ***X ****
                                                  //| X *******
                                                  //| *******X 
                                                  //| *X ******
                                                  //| ****X ***"
  
}