package week5

object listProcessing {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(106); 
  //More functions on list processing
  
  val xs = List(1,2,3,4,5);System.out.println("""xs  : List[Int] = """ + $show(xs ));$skip(45); val res$0 = 
  //The number of elements of xs
  xs.length;System.out.println("""res0: Int = """ + $show(res$0));$skip(32); val res$1 = 
  xs.last;System.out.println("""res1: Int = """ + $show(res$1));$skip(47); val res$2 =  //takes the last elem
  xs.init;System.out.println("""res2: List[Int] = """ + $show(res$2));$skip(15); val res$3 =  //takes everything but the last elem
  List(1).init;System.out.println("""res3: List[Int] = """ + $show(res$3));$skip(45); val res$4 = 
  
  xs take 2;System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(58); val res$5 =  //take 2 prefixes of the list
  
  xs drop 2;System.out.println("""res5: List[Int] = """ + $show(res$5));$skip(21); val res$6 =  //take anything but the first two prefixes
  xs(2);System.out.println("""res6: Int = """ + $show(res$6));$skip(45); val res$7 =  //xs apply n

	/** Creating new lists */
	xs ++ List(6,7);System.out.println("""res7: List[Int] = """ + $show(res$7));$skip(12); val res$8 = 
	xs.reverse;System.out.println("""res8: List[Int] = """ + $show(res$8));$skip(77); val res$9 = 
	
	//like updating the array, but this creates a new list
	xs updated (2,-3);System.out.println("""res9: List[Int] = """ + $show(res$9));$skip(40); val res$10 = 

	/** Finding elements */
	xs indexOf 3;System.out.println("""res10: Int = """ + $show(res$10));$skip(52); val res$11 = 
	xs indexOf -3;System.out.println("""res11: Int = """ + $show(res$11));$skip(15); val res$12 =  //gives -1 if xs does not contain -3
	xs contains 3;System.out.println("""res12: Boolean = """ + $show(res$12));$skip(16); val res$13 = 
	xs contains -3;System.out.println("""res13: Boolean = """ + $show(res$13));$skip(254); 
	
	/** Implement last
	 * last returns a new list consisting of just the last element of an input List
	*/
	def last[T](xs: List[T]): T = xs match {
		case List() => throw new Error("last of empty list")
		case List(x) => x
		case y :: ys => last(ys)
	};System.out.println("""last: [T](xs: List[T])T""");$skip(21); val res$14 = 
	
	last(List(1,2,3));System.out.println("""res14: Int = """ + $show(res$14));$skip(15); val res$15 = 
	last(List(1));System.out.println("""res15: Int = """ + $show(res$15));$skip(284); 
	/** Implement init
	 * init returns a new list consisting of everything in the input list except
	 * the last element
	 */
	def init[T](xs: List[T]): List[T] = xs match {
		case Nil => throw new Error("init of empty list")
		case List(x) => List()
		case y :: ys => y :: init(ys)
	};System.out.println("""init: [T](xs: List[T])List[T]""");$skip(19); val res$16 = 
	init(List(1,2,3));System.out.println("""res16: List[Int] = """ + $show(res$16));$skip(15); val res$17 = 
	init(List(1));System.out.println("""res17: List[Int] = """ + $show(res$17));$skip(199); 
	
	/** Implement Concat
	 * contatenate two lists
	 * complexity is O(n_xs)
	*/
	def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
		case Nil => ys
		case h :: t => h :: concat(t, ys)
	};System.out.println("""concat: [T](xs: List[T], ys: List[T])List[T]""");$skip(30); val res$18 = 
	concat(List(1,2), List(3,4));System.out.println("""res18: List[Int] = """ + $show(res$18));$skip(195); 
 	
 	/** Implement Reverse
	 * complexity is O(n*n) - There is a better way
	 */
	def reverse[T](xs: List[T]): List[T] = xs match {
		case Nil => List()
		case h :: t => reverse(t) ++ List(h)
	};System.out.println("""reverse: [T](xs: List[T])List[T]""");$skip(22); val res$19 = 
	reverse(List(1,2,3));System.out.println("""res19: List[Int] = """ + $show(res$19));$skip(312); 
	
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
	};System.out.println("""removeAt: [T](n: Int, xs: List[T])List[T]""");$skip(88); 

	def removeAt2[T](n: Int, xs: List[T]): List[T] = {
		(xs take n) ::: (xs drop n+1)
	};System.out.println("""removeAt2: [T](n: Int, xs: List[T])List[T]""");$skip(42); val res$20 = 
	
	removeAt(-1, List('a', 'b', 'c', 'd'));System.out.println("""res20: List[Char] = """ + $show(res$20));$skip(39); val res$21 = 
	removeAt(0, List('a', 'b', 'c', 'd'));System.out.println("""res21: List[Char] = """ + $show(res$21));$skip(39); val res$22 = 
	removeAt(1, List('a', 'b', 'c', 'd'));System.out.println("""res22: List[Char] = """ + $show(res$22));$skip(39); val res$23 = 
	removeAt(3, List('a', 'b', 'c', 'd'));System.out.println("""res23: List[Char] = """ + $show(res$23));$skip(39); val res$24 = 
	removeAt(4, List('a', 'b', 'c', 'd'));System.out.println("""res24: List[Char] = """ + $show(res$24));$skip(45); val res$25 = 
	
	
	removeAt2(-1, List('a', 'b', 'c', 'd'));System.out.println("""res25: List[Char] = """ + $show(res$25));$skip(40); val res$26 = 
	removeAt2(0, List('a', 'b', 'c', 'd'));System.out.println("""res26: List[Char] = """ + $show(res$26));$skip(40); val res$27 = 
	removeAt2(1, List('a', 'b', 'c', 'd'));System.out.println("""res27: List[Char] = """ + $show(res$27));$skip(40); val res$28 = 
	removeAt2(3, List('a', 'b', 'c', 'd'));System.out.println("""res28: List[Char] = """ + $show(res$28));$skip(40); val res$29 = 
	removeAt2(4, List('a', 'b', 'c', 'd'));System.out.println("""res29: List[Char] = """ + $show(res$29));$skip(219); 
	
	/** flatten
	 * 	flatten a list structure.
	 */
	def flatten(xs: List[Any]): List[Any] = xs match {
		case Nil => Nil
		case (h: List[Any]) :: t => flatten(h) ++ flatten(t)
		case (h: Any) :: t => h :: flatten(t)
	};System.out.println("""flatten: (xs: List[Any])List[Any]""");$skip(51); val res$30 = 

	flatten(List(List(1,1), 2, List(3, List(5, 8))));System.out.println("""res30: List[Any] = """ + $show(res$30))}
	
	
}
