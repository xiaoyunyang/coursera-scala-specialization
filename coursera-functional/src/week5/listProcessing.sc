package week5

object listProcessing {
  //More functions on list processing
  
  val xs = List(1,2,3,4,5)                        //> xs  : List[Int] = List(1, 2, 3, 4, 5)
  //The number of elements of xs
  xs.length                                       //> res0: Int = 5
  xs.last //takes the last elem                   //> res1: Int = 5
  xs.init //takes everything but the last elem    //> res2: List[Int] = List(1, 2, 3, 4)
  List(1).init                                    //> res3: List[Int] = List()
  
  xs take 2 //take 2 prefixes of the list         //> res4: List[Int] = List(1, 2)
  
  xs drop 2 //take anything but the first two prefixes
                                                  //> res5: List[Int] = List(3, 4, 5)
  xs(2) //xs apply n                              //> res6: Int = 3

	/** Creating new lists */
	xs ++ List(6,7)                           //> res7: List[Int] = List(1, 2, 3, 4, 5, 6, 7)
	xs.reverse                                //> res8: List[Int] = List(5, 4, 3, 2, 1)
	
	//like updating the array, but this creates a new list
	xs updated (2,-3)                         //> res9: List[Int] = List(1, 2, -3, 4, 5)

	/** Finding elements */
	xs indexOf 3                              //> res10: Int = 2
	xs indexOf -3 //gives -1 if xs does not contain -3
                                                  //> res11: Int = -1
	xs contains 3                             //> res12: Boolean = true
	xs contains -3                            //> res13: Boolean = false
	
	/** Implement last
	 * last returns a new list consisting of just the last element of an input List
	*/
	def last[T](xs: List[T]): T = xs match {
		case List() => throw new Error("last of empty list")
		case List(x) => x
		case y :: ys => last(ys)
	}                                         //> last: [T](xs: List[T])T
	
	last(List(1,2,3))                         //> res14: Int = 3
	last(List(1))                             //> res15: Int = 1
	/** Implement init
	 * init returns a new list consisting of everything in the input list except
	 * the last element
	 */
	def init[T](xs: List[T]): List[T] = xs match {
		case Nil => throw new Error("init of empty list")
		case List(x) => List()
		case y :: ys => y :: init(ys)
	}                                         //> init: [T](xs: List[T])List[T]
	init(List(1,2,3))                         //> res16: List[Int] = List(1, 2)
	init(List(1))                             //> res17: List[Int] = List()
	
	/** Implement Concat
	 * contatenate two lists
	 * complexity is O(n_xs)
	*/
	def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
		case Nil => ys
		case h :: t => h :: concat(t, ys)
	}                                         //> concat: [T](xs: List[T], ys: List[T])List[T]
	concat(List(1,2), List(3,4))              //> res18: List[Int] = List(1, 2, 3, 4)
 	
 	/** Implement Reverse
	 * complexity is O(n*n) - There is a better way
	 */
	def reverse[T](xs: List[T]): List[T] = xs match {
		case Nil => List()
		case h :: t => reverse(t) ++ List(h)
	}                                         //> reverse: [T](xs: List[T])List[T]
	reverse(List(1,2,3))                      //> res19: List[Int] = List(3, 2, 1)
	
	/** RemoveAt
	 * remove the nth element of a list xs. If n is out of bounds, return xs itself
	 */
	def removeAt[T](n: Int, xs: List[T]): List[T] = xs match {
		case Nil => List()
		case h :: Nil => {
			if(n == 0) List()
			else xs
		}
		case h :: t => {
			if(n == 0) t
			else h :: removeAt(n-1, t)
		}
	}                                         //> removeAt: [T](n: Int, xs: List[T])List[T]

	def removeAt2[T](n: Int, xs: List[T]): List[T] = {
		(xs take n) ::: (xs drop n+1)
	}                                         //> removeAt2: [T](n: Int, xs: List[T])List[T]
	
	removeAt(-1, List('a', 'b', 'c', 'd'))    //> res20: List[Char] = List(a, b, c, d)
	removeAt(0, List('a', 'b', 'c', 'd'))     //> res21: List[Char] = List(b, c, d)
	removeAt(1, List('a', 'b', 'c', 'd'))     //> res22: List[Char] = List(a, c, d)
	removeAt(3, List('a', 'b', 'c', 'd'))     //> res23: List[Char] = List(a, b, c)
	removeAt(4, List('a', 'b', 'c', 'd'))     //> res24: List[Char] = List(a, b, c, d)
	
	
	removeAt2(-1, List('a', 'b', 'c', 'd'))   //> res25: List[Char] = List(a, b, c, d)
	removeAt2(0, List('a', 'b', 'c', 'd'))    //> res26: List[Char] = List(b, c, d)
	removeAt2(1, List('a', 'b', 'c', 'd'))    //> res27: List[Char] = List(a, c, d)
	removeAt2(3, List('a', 'b', 'c', 'd'))    //> res28: List[Char] = List(a, b, c)
	removeAt2(4, List('a', 'b', 'c', 'd'))    //> res29: List[Char] = List(a, b, c, d)
	
	/** flatten
	 * 	flatten a list structure.
	 */
	def flatten(xs: List[Any]): List[Any] = xs match {
		case Nil => Nil
		case (h: List[Any]) :: t => flatten(h) ++ flatten(t)
		case (h: Any) :: t => h :: flatten(t)
	}                                         //> flatten: (xs: List[Any])List[Any]

	flatten(List(List(1,1), 2, List(3, List(5, 8))))
                                                  //> res30: List[Any] = List(1, 1, 2, 3, 5, 8)
	
	
}