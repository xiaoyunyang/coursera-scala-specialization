package week4

object collectionlist {

  val fruit: List[String] = List("apples", "oranges", "pears")
                                                  //> fruit  : List[String] = List(apples, oranges, pears)
  //merging two lists, use :::
  val fruit2: List[String] = List("apples", "oranges") ::: List("pears")
                                                  //> fruit2  : List[String] = List(apples, oranges, pears)
 	val myFruit = MyList("apples", "oranges", "pears")
                                                  //> myFruit  : week4.Cons[(String, String, String)] = List(apples,oranges,pears)
                                                  //| 
    
 	val nums: List[Int] = List(1,2,3,4)       //> nums  : List[Int] = List(1, 2, 3, 4)
 	val nums2 = MyList(1,2,3,4)               //> nums2  : week4.Cons[(Int, Int, Int, Int)] = List(1,2,3,4)
 	val diag3: List[List[Int]] = List(List(1,0,0), List(0,1,0), List(0,0,1))
                                                  //> diag3  : List[List[Int]] = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
                                                  //| 
  val diag32 = MyList(List(1,0,0), List(0,1,0), List(0,0,1))
                                                  //> diag32  : week4.Cons[(List[Int], List[Int], List[Int])] = List(List(1, 0, 0)
                                                  //| ,List(0, 1, 0),List(0, 0, 1))
                                                  
                                                  
    
 	val empty: List[Nothing] = List()         //> empty  : List[Nothing] = List()
 	val empty2 = MyList()                     //> empty2  : week4.MyList[Nothing] = List(Nil)
 	
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
  fruit.tail.head                                 //> res0: String = oranges
  
  //Insertion sort - worst case complexity is N*N
  def isort(xs: List[Int]): List[Int] = xs match {
  	case List() => List()
  	case y :: ys => insert(y, isort(ys))
  }                                               //> isort: (xs: List[Int])List[Int]
  def insert(y: Int, ys: List[Int]): List[Int] = ys match {
  	case List() => List(y)
  	case x :: xs => if(y <= x) y :: ys else x :: insert(y, xs)
  }                                               //> insert: (y: Int, ys: List[Int])List[Int]
  
  isort(List(1,2,4,1,1,4,3))                      //> res1: List[Int] = List(1, 1, 1, 2, 3, 4, 4)
}