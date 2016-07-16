package week6

class Poly(val terms0: Map[Int, Double]) {
  //auxiliary constructor
  //the * means it's a repeated variable
  def this(bindings: (Int, Double)*) = this(bindings.toMap)
  
  val terms = terms0 withDefaultValue(0.0)
  
  def + (other: Poly) = new Poly(terms ++ (other.terms map adjust))
  
  //another version of the + operator that uses foldLeft instead of ++
  //the one with foldLeft is more efficient
  def addViaFold(other: Poly) = new Poly((other.terms foldLeft terms)(addTerm))
  
  def addTerm(terms: Map[Int, Double], term: (Int, Double)): Map[Int, Double] = {
    val (exp, coeff) = term
    terms + (exp -> (coeff + terms(exp)))
  }
  
  def adjust0(term: (Int, Double)): (Int, Double) = {
    val (exp, coeff) = term
    terms get exp match {
      case Some(coeff1) => exp -> (coeff + coeff1)
      case None => exp -> coeff
    }
  }
  //adjust with default value - much simplified
  def adjust(term: (Int, Double)): (Int, Double) = {
    val (exp, coeff) = term
    exp -> (coeff + terms(exp))
  }
  
  override def toString = (
    for( (exp, coeff) <- terms.toList.sorted.reverse) 
      yield coeff + "x^" + exp
    ) mkString " + "
}

 
