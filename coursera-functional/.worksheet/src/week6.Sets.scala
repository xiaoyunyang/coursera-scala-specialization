package week6
import nqueens._


/** Sets are unordered, do not have duplicate elements, and the fundamental operation for Set
 * is "contains".
 */
object Sets {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(207); 
  val fruit = Set("apple", "banana", "pear");System.out.println("""fruit  : scala.collection.immutable.Set[String] = """ + $show(fruit ));$skip(25); 
  val s = (1 to 6).toSet;System.out.println("""s  : scala.collection.immutable.Set[Int] = """ + $show(s ));$skip(39); val res$0 = 
  
  s map (_ + 2);System.out.println("""res0: scala.collection.immutable.Set[Int] = """ + $show(res$0));$skip(37); val res$1 =  // Set(3,4,5,6,7,8)
  fruit filter (_.startsWith("app"));System.out.println("""res1: scala.collection.immutable.Set[String] = """ + $show(res$1));$skip(13); val res$2 = 
  s.nonEmpty;System.out.println("""res2: Boolean = """ + $show(res$2));$skip(36); val res$3 = 
  

	/** Test nqueens */
	queens(4);System.out.println("""res3: Set[List[Int]] = """ + $show(res$3));$skip(21); val res$4 = 
  queens(4) map show;System.out.println("""res4: scala.collection.immutable.Set[String] = """ + $show(res$4));$skip(37); val res$5 = 
  (queens(4) map show) mkString "\n";System.out.println("""res5: String = """ + $show(res$5));$skip(44); val res$6 = 
  (queens(8) take 3 map show) mkString "\n";System.out.println("""res6: String = """ + $show(res$6))}
  
}
