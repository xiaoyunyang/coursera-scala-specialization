package week1

object eval {
  //Scala Evaluation Strategy
  //Scala typically use call-by-value (strict/eager evaluation) because
  //call-by-value (lazy evaluation) is exponentially more efficient than call by name
  
  def loop: Int = loop                            //> loop: => Int
  
  def square(x: Double) = x*x                     //> square: (x: Double)Double
  def sumOfSquares(x: Double, y: Double) = square(x) + square(y)
                                                  //> sumOfSquares: (x: Double, y: Double)Double
  def lazySumOfSquares(x: Double, y: => Double) = square(x) + square(y)
                                                  //> lazySumOfSquares: (x: Double, y: => Double)Double
  
  
  /* call by name */
  //if a type of a function parameter starts with =>, it uses call-by-name
  //x is a call-by-value parameter. y is a call-by-name parameter
  def constOne(x: Int, y: => Int) = 1             //> constOne: (x: Int, y: => Int)Int
  //constOne(1+2, loop) //reduces to 1
  //constOne(loop, 1+2) //infinite cycle
  
}