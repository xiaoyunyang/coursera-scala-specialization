package week6

object nqueens {
  
  def queens(n: Int): Set[List[Int]] = {
    def placeQueens(k: Int): Set[List[Int]] = {
      if(k==0) Set(List())
      else {
        for {
          queens <- placeQueens(k-1)
          col <- 0 until n
          if isSafe(col, queens)
        } yield col :: queens
      }
    }
    placeQueens(n)
  }
  
  /** isSafe
   * Tests if a queen placed in an indicated column col is
   * secure amongst the other placed queens
   */
  def isSafe(col: Int, queens: List[Int]): Boolean = {
    val row = queens.length //the other queens are in rows 0 until queens.length-1
    val queensWithRow = (row - 1 to 0 by -1) zip queens
    queensWithRow forall {
      //Check that the queen is not in check with any of the diagonals
      case (r, c) => col != c && math.abs(col - c) != row-r
    }
  }
  
  def show(queens: List[Int]) = {
    val lines = for (col<-queens.reverse)
      yield Vector.fill(queens.length)("*").updated(col, "X ").mkString
    "\n" + (lines mkString "\n") //separate each line by new line
  }
}