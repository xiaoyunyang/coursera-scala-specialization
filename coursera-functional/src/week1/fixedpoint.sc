package week1
import math.abs

object fixedpoint {
  val tolerance = 0.0001                          //> tolerance  : Double = 1.0E-4
  def isCloseEnough(x: Double, y: Double) =
  	abs((x-y) / x) / x < tolerance            //> isCloseEnough: (x: Double, y: Double)Boolean
  
  //fixed point estimates
  def fixedPoint(f: Double => Double)(firstGuess: Double): Double = {
  	def iterate(guess: Double): Double = {
  		println("guess: "+guess)
  		val next = f(guess)
  		if(isCloseEnough(guess, next)) next
  		else iterate(next)
  	}
  	iterate(firstGuess)
  }                                               //> fixedPoint: (f: Double => Double)(firstGuess: Double)Double
  fixedPoint(x => 1 + x/2)(1)                     //> guess: 1.0
                                                  //| guess: 1.5
                                                  //| guess: 1.75
                                                  //| guess: 1.875
                                                  //| guess: 1.9375
                                                  //| guess: 1.96875
                                                  //| guess: 1.984375
                                                  //| guess: 1.9921875
                                                  //| guess: 1.99609375
                                                  //| guess: 1.998046875
                                                  //| guess: 1.9990234375
                                                  //| guess: 1.99951171875
                                                  //| res0: Double = 1.999755859375
  
 	//if sqrt(x) = number of y such that y*y = x
 	//then sqrt(x) = number of y such that y=x/y
 	//consequently, sqrt(x) is the fixed point of function (y => x/y)
 	
 	//def sqrt(x: Double) = fixedPoint(y => x/y)(1) //<- this is going to oscillate between 1 and 2
 	
 	//one way to control such oscillation is to prevent the estimation from varying too much.
 	//This is done by averaging successive values of the original sequence
 	def sqrt(x: Double) = fixedPoint(y => (y + x/y)/2)(1)
                                                  //> sqrt: (x: Double)Double
 	sqrt(9)                                   //> guess: 1.0
                                                  //| guess: 5.0
                                                  //| guess: 3.4
                                                  //| guess: 3.023529411764706
                                                  //| guess: 3.00009155413138
                                                  //| res1: Double = 3.000000001396984
  
  def averageDamp(f: Double => Double)(x: Double) = (x + f(x))/2
                                                  //> averageDamp: (f: Double => Double)(x: Double)Double
  
	//averageDamp is a function that takes a function to another function. Because when averageDamp was
	//fed the definition of f, i.e., (y => x/y) below, we never fed the value on which that f gets applied.
	//averageDamp below never gets evaluated. It just gets passed to fixedPoint, at which point the final value
	//is evaluated
  def sqrt2(x: Double) = fixedPoint(averageDamp(y  => x/y))(1)
                                                  //> sqrt2: (x: Double)Double
  sqrt2(9)                                        //> guess: 1.0
                                                  //| guess: 5.0
                                                  //| guess: 3.4
                                                  //| guess: 3.023529411764706
                                                  //| guess: 3.00009155413138
                                                  //| res2: Double = 3.000000001396984
}