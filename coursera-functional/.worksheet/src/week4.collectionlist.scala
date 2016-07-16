package week4

object collectionlist {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(102); 

  val fruit: List[String] = List("apples", "oranges", "pears");System.out.println("""fruit  : List[String] = """ + $show(fruit ));$skip(104); 
  //merging two lists, use :::
  val fruit2: List[String] = List("apples", "oranges") ::: List("pears");System.out.println("""fruit2  : List[String] = """ + $show(fruit2 ));$skip(53); 
 	val myFruit = MyList("apples", "oranges", "pears");System.out.println("""myFruit  : week4.MyList[String] = """ + $show(myFruit ));$skip(43); 
    
 	val nums: List[Int] = List(1,2,3,4);System.out.println("""nums  : List[Int] = """ + $show(nums ));$skip(30); 
 	val nums2 = MyList(1,2,3,4);System.out.println("""nums2  : week4.MyList[Int] = """ + $show(nums2 ));$skip(75); 
 	val diag3: List[List[Int]] = List(List(1,0,0), List(0,1,0), List(0,0,1));System.out.println("""diag3  : List[List[Int]] = """ + $show(diag3 ));$skip(61); 
  val diag32 = MyList(List(1,0,0), List(0,1,0), List(0,0,1));System.out.println("""diag32  : week4.MyList[List[Int]] = """ + $show(diag32 ));$skip(143); 
                                                  
                                                  
    
 	val empty: List[Nothing] = List();System.out.println("""empty  : List[Nothing] = """ + $show(empty ));$skip(24); 
 	val empty2 = MyList();System.out.println("""empty2  : week4.MyList[Nothing] = """ + $show(empty2 ));$skip(627); val res$0 = 
 	
 	/** list operations
 	 *
 	 */
 	/* fold vs. foldLeft vs. foldRight
 	 *
 	 * foldRight associates to the right (i.e., elements will accumulate from right to left
 	 * foldLeft associates to the left (i.e., an accumulator will be initialized and
 	 * elements will be accumulate from left to right)
 	 * fold, contrarary to foldRight and foldLeft, does not offer any guarantee
 	 * about the order in which the lements of the collection will be processed.
 	 * Use fold, with its lack of guaranteed processing order helps the parallel
 	 * collection implement folding in a parallel way.
 	 */
 	List(1,3,8).map(x => x*x);System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(45); val res$1 = 
 	List(1,3,8).foldRight(100)((s,x) => s + x);System.out.println("""res1: Int = """ + $show(res$1));$skip(40); val res$2 = 
 	List(1,3,8).fold(100)((s,x) => s + x);System.out.println("""res2: Int = """ + $show(res$2));$skip(44); val res$3 = 
 	List(1,3,8).foldLeft(100)((s,x) => s + x);System.out.println("""res3: Int = """ + $show(res$3));$skip(189); 
 	
 	/* reverse a list
 	 *
 	 */
 	//A function that reverses a list
 	def reverse[T](l: List[T]): List[T] = l match {
 		case Nil => Nil
 		case h :: t => reverse(t) ::: h :: Nil
 		
 	};System.out.println("""reverse: [T](l: List[T])List[T]""");$skip(73); val res$4 = 
 	List(1,2,3).foldLeft(Nil: List[Int])((t: List[Int], h: Int) => h :: t);System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(23); val res$5 = 
  reverse(List(1,2,3));System.out.println("""res5: List[Int] = """ + $show(res$5));$skip(115); val res$6 = 
 	
 	
 	//Scan is like recording the intermediate values of computing a fold
 	List(1,3,8).fold(100)((s,x) => s+x);System.out.println("""res6: Int = """ + $show(res$6));$skip(38); val res$7 = 
 	List(1,3,8).scan(100)((s,x) => s+x);System.out.println("""res7: List[Int] = """ + $show(res$7));$skip(46); val res$8 = 
 
 	List("a","b","c").fold("!")((x,y) => x+y);System.out.println("""res8: String = """ + $show(res$8));$skip(44); val res$9 = 
 	List("a","b","c").scan("!")((x,y) => x+y);System.out.println("""res9: List[String] = """ + $show(res$9));$skip(61); val res$10 = 

	//foldLeft
 	List("a","b","c").foldLeft("!")((x,y) => x+y);System.out.println("""res10: String = """ + $show(res$10));$skip(48); val res$11 = 
 	List("a","b","c").scanLeft("!")((x,y) => x+y);System.out.println("""res11: List[String] = """ + $show(res$11));$skip(48); val res$12 = 
  List("a","b","c").foldLeft("!")((x,y) => y+x);System.out.println("""res12: String = """ + $show(res$12));$skip(48); val res$13 = 
 	List("a","b","c").scanLeft("!")((x,y) => y+x);System.out.println("""res13: List[String] = """ + $show(res$13));$skip(63); val res$14 = 
  //foldRight
 	List("a","b","c").foldRight("!")((x,y) => x+y);System.out.println("""res14: String = """ + $show(res$14));$skip(49); val res$15 = 
 	List("a","b","c").scanRight("!")((x,y) => y+x);System.out.println("""res15: List[String] = """ + $show(res$15));$skip(49); val res$16 = 
  List("a","b","c").foldRight("!")((x,y) => y+x);System.out.println("""res16: String = """ + $show(res$16));$skip(49); val res$17 = 
 	List("a","b","c").scanRight("!")((x,y) => y+x);System.out.println("""res17: List[String] = """ + $show(res$17));$skip(231); 
  

 	/**  Syntactic Sugar for List constructor **/
 	//The :: is pronounced cons. x :: xs gives a new list with the first element x,
 	//followed by the elements of xs
 	
 	val fruit3 = "apples" :: ("oranges" :: ("pears" :: Nil));System.out.println("""fruit3  : List[String] = """ + $show(fruit3 ));$skip(19); 
  val empty3 = Nil;System.out.println("""empty3  : scala.collection.immutable.Nil.type = """ + $show(empty3 ));$skip(183); 
 	
 	//Convention: Operators ending in ":" associate tot he right. So that means
 	//A :: B :: C is interpreted as A :: (B :: C)
 	val fruit4 = "apple" :: "oranges" :: "pears" :: Nil;System.out.println("""fruit4  : List[String] = """ + $show(fruit4 ));$skip(107); 
  //double colon is the same as prepend operation
  val fruit5 = Nil.::("pears").::("oranges").::("apple");System.out.println("""fruit5  : List[String] = """ + $show(fruit5 ));$skip(55); val res$18 = 
  //get second element of List fruit
  fruit.tail.head;System.out.println("""res18: String = """ + $show(res$18));$skip(173); 
  
  //Insertion sort - worst case complexity is N*N
  def isort(xs: List[Int]): List[Int] = xs match {
  	case List() => List()
  	case y :: ys => insert(y, isort(ys))
  };System.out.println("""isort: (xs: List[Int])List[Int]""");$skip(152); 
  def insert(y: Int, ys: List[Int]): List[Int] = ys match {
  	case List() => List(y)
  	case x :: xs => if(y <= x) y :: ys else x :: insert(y, xs)
  };System.out.println("""insert: (y: Int, ys: List[Int])List[Int]""");$skip(32); val res$19 = 
  
  isort(List(1,2,4,1,1,4,3));System.out.println("""res19: List[Int] = """ + $show(res$19))}
}
