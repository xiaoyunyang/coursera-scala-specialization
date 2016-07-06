package week1
import Generators._

object generator {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(73); val res$0 = 

	integers.generate;System.out.println("""res0: Int = """ + $show(res$0));$skip(19); val res$1 = 
	integers.generate;System.out.println("""res1: Int = """ + $show(res$1));$skip(19); val res$2 = 
	integers.generate;System.out.println("""res2: Int = """ + $show(res$2));$skip(19); val res$3 = 
	integers.generate;System.out.println("""res3: Int = """ + $show(res$3));$skip(18); val res$4 = 
	
	lists.generate;System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(18); val res$5 = 
	
	trees.generate;System.out.println("""res5: week1.Generators.Tree = """ + $show(res$5));$skip(17); val res$6 = 
  trees.generate;System.out.println("""res6: week1.Generators.Tree = """ + $show(res$6));$skip(17); val res$7 = 
  trees.generate;System.out.println("""res7: week1.Generators.Tree = """ + $show(res$7));$skip(17); val res$8 = 
  trees.generate;System.out.println("""res8: week1.Generators.Tree = """ + $show(res$8));$skip(45); val res$9 = 
  
  oneOf("red", "blue", "yellow").generate;System.out.println("""res9: String = """ + $show(res$9));$skip(27); val res$10 = 
	oneOf(1,2,3,4,5).generate;System.out.println("""res10: Int = """ + $show(res$10));$skip(410); 
  
  
  /** Random Test Function */
  //an important application of random number generator is application testing
  //ScalaCheck is a tool you can use for random testing
  def test[T](g: Generator[T], numTimes: Int = 100)(test: T => Boolean): Unit = {
  	for(i<- 0 until numTimes) {
  		val value = g.generate
  		assert(test(value), "test failed for "+value)
  	}
  	println("passed "+numTimes+" tests")
  };System.out.println("""test: [T](g: week1.Generators.Generator[T], numTimes: Int)(test: T => Boolean)Unit""");$skip(89); 
    
  test(pairs(lists, lists)) {
  	case (xs, ys) => (xs ++ ys).length > xs.length
  }}
  
  
}
