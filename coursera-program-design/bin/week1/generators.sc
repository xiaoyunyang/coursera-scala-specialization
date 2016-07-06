package week1
import Generators._

object generator {

	integers.generate                         //> res0: Int = 371716363
	integers.generate                         //> res1: Int = -1372008951
	integers.generate                         //> res2: Int = -847572034
	integers.generate                         //> res3: Int = 802497385
	
	lists.generate                            //> res4: List[Int] = List()
	
	trees.generate                            //> res5: week1.Generators.Tree = Inner(Inner(Leaf(1981203468),Inner(Leaf(-65888
                                                  //| 5254),Leaf(-1546064496))),Leaf(1902078538))
  trees.generate                                  //> res6: week1.Generators.Tree = Inner(Inner(Inner(Inner(Leaf(233984758),Inner(
                                                  //| Leaf(264254883),Leaf(809535223))),Leaf(1603016675)),Leaf(1149678563)),Leaf(-
                                                  //| 468489912))
  trees.generate                                  //> res7: week1.Generators.Tree = Inner(Inner(Inner(Leaf(1751448559),Inner(Leaf(
                                                  //| 185356329),Inner(Inner(Leaf(-1843801414),Leaf(2109204259)),Inner(Leaf(161438
                                                  //| 6506),Leaf(607700201))))),Inner(Inner(Inner(Leaf(166280731),Inner(Leaf(-1984
                                                  //| 824132),Inner(Inner(Inner(Leaf(1005834971),Leaf(1280182582)),Leaf(642153955)
                                                  //| ),Leaf(652118778)))),Leaf(650691382)),Leaf(744343032))),Inner(Inner(Leaf(195
                                                  //| 4727156),Inner(Inner(Inner(Inner(Leaf(1922527216),Leaf(-1219476810)),Leaf(-1
                                                  //| 917252488)),Inner(Leaf(-2022769253),Inner(Inner(Inner(Leaf(1846655918),Leaf(
                                                  //| 936927489)),Inner(Leaf(-1633945586),Inner(Inner(Inner(Leaf(697918152),Leaf(7
                                                  //| 82196606)),Leaf(-1621751212)),Inner(Inner(Inner(Leaf(-790066740),Inner(Inner
                                                  //| (Leaf(-1031935667),Leaf(1345784654)),Leaf(-568935709))),Inner(Inner(Leaf(-12
                                                  //| 90087259),Leaf(-952748765)),Leaf(197991604))),Inner(Leaf(-126836480),Leaf(29
                                                  //| 9859398)))))),Inner(Leaf(-1446905591),Leaf(1949494813))))),Leaf(530700937)))
                                                  //| ,Leaf(-1841623495)))
  trees.generate                                  //> res8: week1.Generators.Tree = Inner(Leaf(236310275),Inner(Leaf(-927615215),L
                                                  //| eaf(697116785)))
  
  oneOf("red", "blue", "yellow").generate         //> res9: String = yellow
	oneOf(1,2,3,4,5).generate                 //> res10: Int = 3
  
  
  /** Random Test Function */
  //an important application of random number generator is application testing
  //ScalaCheck is a tool you can use for random testing
  def test[T](g: Generator[T], numTimes: Int = 100)(test: T => Boolean): Unit = {
  	for(i<- 0 until numTimes) {
  		val value = g.generate
  		assert(test(value), "test failed for "+value)
  	}
  	println("passed "+numTimes+" tests")
  }                                               //> test: [T](g: week1.Generators.Generator[T], numTimes: Int)(test: T => Boolea
                                                  //| n)Unit
    
  test(pairs(lists, lists)) {
  	case (xs, ys) => (xs ++ ys).length > xs.length
  }                                               //> java.lang.AssertionError: assertion failed: test failed for (List(1451891136
                                                  //| , 1910718399),List())
                                                  //| 	at scala.Predef$.assert(Predef.scala:165)
                                                  //| 	at week1.generator$$anonfun$main$1$$anonfun$test$1$1.apply$mcVI$sp(week1
                                                  //| .generator.scala:28)
                                                  //| 	at scala.collection.immutable.Range.foreach$mVc$sp(Range.scala:166)
                                                  //| 	at week1.generator$$anonfun$main$1.test$1(week1.generator.scala:26)
                                                  //| 	at week1.generator$$anonfun$main$1.apply$mcV$sp(week1.generator.scala:33
                                                  //| )
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at week1.generator$.main(week1.generator.scala:4)
                                                  //| 	at week1.generator.main(week1.generator.scala)
  
  
}