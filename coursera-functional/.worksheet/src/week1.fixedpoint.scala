package week1
import math.abs

object fixedpoint {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(75); 
  val tolerance = 0.0001;System.out.println("""tolerance  : Double = """ + $show(tolerance ));$skip(78); 
  def isCloseEnough(x: Double, y: Double) =
  	abs((x-y) / x) / x < tolerance;System.out.println("""isCloseEnough: (x: Double, y: Double)Boolean""");$skip(289); 
  
  //fixed point estimates
  def fixedPoint(f: Double => Double)(firstGuess: Double): Double = {
  	def iterate(guess: Double): Double = {
  		println("guess: "+guess)
  		val next = f(guess)
  		if(isCloseEnough(guess, next)) next
  		else iterate(next)
  	}
  	iterate(firstGuess)
  };System.out.println("""fixedPoint: (f: Double => Double)(firstGuess: Double)Double""");$skip(30); val res$0 = 
  fixedPoint(x => 1 + x/2)(1);System.out.println("""res0: Double = """ + $show(res$0));$skip(490); 
  
 	//if sqrt(x) = number of y such that y*y = x
 	//then sqrt(x) = number of y such that y=x/y
 	//consequently, sqrt(x) is the fixed point of function (y => x/y)
 	
 	//def sqrt(x: Double) = fixedPoint(y => x/y)(1) //<- this is going to oscillate between 1 and 2
 	
 	//one way to control such oscillation is to prevent the estimation from varying too much.
 	//This is done by averaging successive values of the original sequence
 	def sqrt(x: Double) = fixedPoint(y => (y + x/y)/2)(1);System.out.println("""sqrt: (x: Double)Double""");$skip(10); val res$1 = 
 	sqrt(9);System.out.println("""res1: Double = """ + $show(res$1));$skip(68); 
  
  def averageDamp(f: Double => Double)(x: Double) = (x + f(x))/2;System.out.println("""averageDamp: (f: Double => Double)(x: Double)Double""");$skip(397); 
  
	//averageDamp is a function that takes a function to another function. Because when averageDamp was
	//fed the definition of f, i.e., (y => x/y) below, we never fed the value on which that f gets applied.
	//averageDamp below never gets evaluated. It just gets passed to fixedPoint, at which point the final value
	//is evaluated
  def sqrt2(x: Double) = fixedPoint(averageDamp(y  => x/y))(1);System.out.println("""sqrt2: (x: Double)Double""");$skip(11); val res$2 = 
  sqrt2(9);System.out.println("""res2: Double = """ + $show(res$2))}
}
