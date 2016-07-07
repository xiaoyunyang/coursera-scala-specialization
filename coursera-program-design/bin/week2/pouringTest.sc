package week2

object pouringTest {
  val problem = new Pouring(Vector(4, 9))         //> problem  : week2.Pouring = week2.Pouring@50c87b21
  problem.moves                                   //> res0: scala.collection.immutable.IndexedSeq[Product with Serializable with we
                                                  //| ek2.pouringTest.problem.Move] = Vector(Empty(0), Empty(1), Fill(0), Fill(1), 
                                                  //| Pour(0,1), Pour(1,0))
 
 
  problem.pathSets.take(1).toList                 //> res1: List[Set[week2.pouringTest.problem.PathOptimized]] = List(Set(--> Vect
                                                  //| or(0, 0)))
  problem.pathSets.take(2).toList                 //> res2: List[Set[week2.pouringTest.problem.PathOptimized]] = List(Set(--> Vect
                                                  //| or(0, 0)), Set(Fill(0)--> Vector(4, 0), Fill(1)--> Vector(0, 9)))
  problem.solution(6)                             //> res3: Stream[week2.pouringTest.problem.PathOptimized] = Stream(Fill(1) Pour(
                                                  //| 1,0) Empty(0) Pour(1,0) Empty(0) Pour(1,0) Fill(1) Pour(1,0)--> Vector(4, 6)
                                                  //| , ?)
  problem.solution(7)                             //> res4: Stream[week2.pouringTest.problem.PathOptimized] = Stream(Fill(0) Pour(
                                                  //| 0,1) Fill(0) Pour(0,1) Fill(0) Pour(0,1) Empty(1) Pour(0,1) Fill(0) Pour(0,1
                                                  //| )--> Vector(0, 7), ?)
  problem.solution(17)                            //> res5: Stream[week2.pouringTest.problem.PathOptimized] = Stream()



}