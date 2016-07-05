package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1 - Computes the elements of pascal's triangle by recursion
   * takes a column c and a row r, counting from 0 and returns the number at that 
   * spot in the triangle. For example, pascal(0,2)=1,pascal(1,2)=2 and pascal(1,3)=3.
   */
    def pascal(c: Int, r: Int): Int = 
      if(c<0 || r<0) 0
      else if(c==0 || r==0 || c==r) 1 
      else pascal(c-1, r-1) + pascal(c,r-1)
  
  /**
   * Exercise 2 - Write a recursive function which verifies the balancing of parentheses 
   * in a string, which we represent as a List[Char] not a String.
   * Three useful functions: 
   * chars.isEmpty: Boolean returns whether a list is empty
   * chars.head: Char returns the first element of the list
   * chars.tail: List[Char] returns the list without the first element
   */

   def balance(chars: List[Char]): Boolean = {
     def check(c: List[Char], tally: Int): Boolean = {
       if(tally<0) false
       else c match {
         case Nil => tally==0
         case '(' :: t => check(t,tally+1)
         case ')' :: t => check(t, tally-1)
         case h :: t => check(t, tally) 
       }
     }
     check(chars, 0)
  }

  /**
   * Exercise 3 - Write a recursive function that counts how many different ways you can make change for an amount
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if(money==0) 1
      else if(coins.isEmpty || money<0) 0
      else countChange(money-coins.head, coins) + countChange(money, coins.tail) //either you use coin to pay or you don’t use coin to pay
    }
    
}
