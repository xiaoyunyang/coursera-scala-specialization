package week4

object collectionlist {

  val fruit: List[String] = List("apples", "oranges", "pears")
                                                  //> fruit  : List[String] = List(apples, oranges, pears)
  //merging two lists, use :::
  val fruit2: List[String] = List("apples", "oranges") ::: List("pears")
                                                  //> fruit2  : List[String] = List(apples, oranges, pears)
 	val myFruit = MyList("apples", "oranges", "pears")
                                                  //> myFruit  : week4.MyList[String] = List(apples, oranges, pears)
    
 	val nums: List[Int] = List(1,2,3,4)       //> nums  : List[Int] = List(1, 2, 3, 4)
 	val nums2 = MyList(1,2,3,4)               //> nums2  : week4.MyList[Int] = List(1, 2, 3, 4)
 	val diag3: List[List[Int]] = List(List(1,0,0), List(0,1,0), List(0,0,1))
                                                  //> diag3  : List[List[Int]] = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
                                                  //| 
  val diag32 = MyList(List(1,0,0), List(0,1,0), List(0,0,1))
                                                  //> diag32  : week4.MyList[List[Int]] = List(List(1, 0, 0), List(0, 1, 0), List(
                                                  //| 0, 0, 1))
                                                  
                                                  
    
 	val empty: List[Nothing] = List()         //> empty  : List[Nothing] = List()
 	val empty2 = MyList()                     //> empty2  : week4.MyList[Nothing] = Nil
 	
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
 	List(1,3,8).map(x => x*x)                 //> res0: List[Int] = List(1, 9, 64)
 	List(1,3,8).foldRight(100)((s,x) => s + x)//> res1: Int = 112
 	List(1,3,8).fold(100)((s,x) => s + x)     //> res2: Int = 112
 	List(1,3,8).foldLeft(100)((s,x) => s + x) //> res3: Int = 112
 	
 	/* reverse a list
 	 *
 	 */
 	//A function that reverses a list
 	def reverse[T](l: List[T]): List[T] = l match {
 		case Nil => Nil
 		case h :: t => reverse(t) ::: h :: Nil
 		
 	}                                         //> reverse: [T](l: List[T])List[T]
 	List(1,2,3).foldLeft(Nil: List[Int])((t: List[Int], h: Int) => h :: t)
                                                  //> res4: List[Int] = List(3, 2, 1)
  reverse(List(1,2,3))                            //> res5: List[Int] = List(3, 2, 1)
 	
 	
 	//Scan is like recording the intermediate values of computing a fold
 	List(1,3,8).fold(100)((s,x) => s+x)       //> res6: Int = 112
 	List(1,3,8).scan(100)((s,x) => s+x)       //> res7: List[Int] = List(100, 101, 104, 112)
 
 	List("a","b","c").fold("!")((x,y) => x+y) //> res8: String = !abc
 	List("a","b","c").scan("!")((x,y) => x+y) //> res9: List[String] = List(!, !a, !ab, !abc)

	//foldLeft
 	List("a","b","c").foldLeft("!")((x,y) => x+y)
                                                  //> res10: String = !abc
 	List("a","b","c").scanLeft("!")((x,y) => x+y)
                                                  //> res11: List[String] = List(!, !a, !ab, !abc)
  List("a","b","c").foldLeft("!")((x,y) => y+x)   //> res12: String = cba!
 	List("a","b","c").scanLeft("!")((x,y) => y+x)
                                                  //> res13: List[String] = List(!, a!, ba!, cba!)
  //foldRight
 	List("a","b","c").foldRight("!")((x,y) => x+y)
                                                  //> res14: String = abc!
 	List("a","b","c").scanRight("!")((x,y) => y+x)
                                                  //> res15: List[String] = List(!cba, !cb, !c, !)
  List("a","b","c").foldRight("!")((x,y) => y+x)  //> res16: String = !cba
 	List("a","b","c").scanRight("!")((x,y) => y+x)
                                                  //> res17: List[String] = List(!cba, !cb, !c, !)
  

 	/**  Syntactic Sugar for List constructor **/
 	//The :: is pronounced cons. x :: xs gives a new list with the first element x,
 	//followed by the elements of xs
 	
 	val fruit3 = "apples" :: ("oranges" :: ("pears" :: Nil))
                                                  //> fruit3  : List[String] = List(apples, oranges, pears)
  val empty3 = Nil                                //> empty3  : scala.collection.immutable.Nil.type = List()
 	
 	//Convention: Operators ending in ":" associate tot he right. So that means
 	//A :: B :: C is interpreted as A :: (B :: C)
 	val fruit4 = "apple" :: "oranges" :: "pears" :: Nil
                                                  //> fruit4  : List[String] = List(apple, oranges, pears)
  //double colon is the same as prepend operation
  val fruit5 = Nil.::("pears").::("oranges").::("apple")
                                                  //> fruit5  : List[String] = List(apple, oranges, pears)
  //get second element of List fruit
  fruit.tail.head                                 //> res18: String = oranges
  
  //Insertion sort - worst case complexity is N*N
  def isort(xs: List[Int]): List[Int] = xs match {
  	case List() => List()
  	case y :: ys => insert(y, isort(ys))
  }                                               //> isort: (xs: List[Int])List[Int]
  def insert(y: Int, ys: List[Int]): List[Int] = ys match {
  	case List() => List(y)
  	case x :: xs => if(y <= x) y :: ys else x :: insert(y, xs)
  }                                               //> insert: (y: Int, ys: List[Int])List[Int]
  
  isort(List(1,2,4,1,1,4,3))                      //> res19: List[Int] = List(1, 1, 1, 2, 3, 4, 4)
}