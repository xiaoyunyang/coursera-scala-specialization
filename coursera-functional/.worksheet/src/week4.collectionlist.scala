package week4

object collectionlist {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(102); 

  val fruit: List[String] = List("apples", "oranges", "pears");System.out.println("""fruit  : List[String] = """ + $show(fruit ));$skip(104); 
  //merging two lists, use :::
  val fruit2: List[String] = List("apples", "oranges") ::: List("pears");System.out.println("""fruit2  : List[String] = """ + $show(fruit2 ));$skip(53); 
 	val myFruit = MyList("apples", "oranges", "pears");System.out.println("""myFruit  : week4.Cons[(String, String, String)] = """ + $show(myFruit ));$skip(43); 
    
 	val nums: List[Int] = List(1,2,3,4);System.out.println("""nums  : List[Int] = """ + $show(nums ));$skip(30); 
 	val nums2 = MyList(1,2,3,4);System.out.println("""nums2  : week4.Cons[(Int, Int, Int, Int)] = """ + $show(nums2 ));$skip(75); 
 	val diag3: List[List[Int]] = List(List(1,0,0), List(0,1,0), List(0,0,1));System.out.println("""diag3  : List[List[Int]] = """ + $show(diag3 ));$skip(61); 
  val diag32 = MyList(List(1,0,0), List(0,1,0), List(0,0,1));System.out.println("""diag32  : week4.Cons[(List[Int], List[Int], List[Int])] = """ + $show(diag32 ));$skip(143); 
                                                  
                                                  
    
 	val empty: List[Nothing] = List();System.out.println("""empty  : List[Nothing] = """ + $show(empty ));$skip(24); 
 	val empty2 = MyList();System.out.println("""empty2  : week4.MyList[Nothing] = """ + $show(empty2 ));$skip(230); 
 	
 	/**  Syntactic Sugar for List constructor **/
 	//The :: is pronounced cons. x :: xs gives a new list with the first element x,
 	//followed by the elements of xs
 	
 	val fruit3 = "apples" :: ("oranges" :: ("pears" :: Nil));System.out.println("""fruit3  : List[String] = """ + $show(fruit3 ));$skip(19); 
  val empty3 = Nil;System.out.println("""empty3  : scala.collection.immutable.Nil.type = """ + $show(empty3 ));$skip(183); 
 	
 	//Convention: Operators ending in ":" associate tot he right. So that means
 	//A :: B :: C is interpreted as A :: (B :: C)
 	val fruit4 = "apple" :: "oranges" :: "pears" :: Nil;System.out.println("""fruit4  : List[String] = """ + $show(fruit4 ));$skip(107); 
  //double colon is the same as prepend operation
  val fruit5 = Nil.::("pears").::("oranges").::("apple");System.out.println("""fruit5  : List[String] = """ + $show(fruit5 ));$skip(55); val res$0 = 
  //get second element of List fruit
  fruit.tail.head;System.out.println("""res0: String = """ + $show(res$0));$skip(173); 
  
  //Insertion sort - worst case complexity is N*N
  def isort(xs: List[Int]): List[Int] = xs match {
  	case List() => List()
  	case y :: ys => insert(y, isort(ys))
  };System.out.println("""isort: (xs: List[Int])List[Int]""");$skip(152); 
  def insert(y: Int, ys: List[Int]): List[Int] = ys match {
  	case List() => List(y)
  	case x :: xs => if(y <= x) y :: ys else x :: insert(y, xs)
  };System.out.println("""insert: (y: Int, ys: List[Int])List[Int]""");$skip(32); val res$1 = 
  
  isort(List(1,2,4,1,1,4,3));System.out.println("""res1: List[Int] = """ + $show(res$1))}
}
