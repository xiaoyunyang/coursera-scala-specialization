package week2

object pouringTest {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(77); 
  val problem = new Pouring(Vector(4, 9));System.out.println("""problem  : week2.Pouring = """ + $show(problem ));$skip(16); val res$0 = 
  problem.moves;System.out.println("""res0: scala.collection.immutable.IndexedSeq[Product with Serializable with week2.pouringTest.problem.Move] = """ + $show(res$0));$skip(38); val res$1 = 
 
 
  problem.pathSets.take(1).toList;System.out.println("""res1: List[Set[week2.pouringTest.problem.PathOptimized]] = """ + $show(res$1));$skip(34); val res$2 = 
  problem.pathSets.take(2).toList;System.out.println("""res2: List[Set[week2.pouringTest.problem.PathOptimized]] = """ + $show(res$2));$skip(22); val res$3 = 
  problem.solution(6);System.out.println("""res3: Stream[week2.pouringTest.problem.PathOptimized] = """ + $show(res$3));$skip(22); val res$4 = 
  problem.solution(7);System.out.println("""res4: Stream[week2.pouringTest.problem.PathOptimized] = """ + $show(res$4));$skip(23); val res$5 = 
  problem.solution(17);System.out.println("""res5: Stream[week2.pouringTest.problem.PathOptimized] = """ + $show(res$5))}



}
