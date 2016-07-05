package week2

class Rational(x: Int, y: Int) { //the constructor
  require(y!=0, "denominator must be nonzero")
  //to guard against users creating illegal Rational with a zero denominator
  
  def this(x: Int) = this(x,1) //a secondary constructor
  
  private def gcd(a: Int, b: Int): Int = if(b==0) a else gcd(b, a % b)
  private def abs(a: Int) = if(a>=0) a else -a
  private val g = abs(gcd(x,y))
  
  //We want to simplify Ints before we perform operations because the limit of ints
  //are just about 2 billion. We don't want to get into arithemetic overflows early
  val numer = x / g
  val denom = y / g
  
  //ADDITION
  def add(that: Rational): Rational = {
    new Rational(
      this.numer*that.denom + that.numer*this.denom, 
      this.denom*that.denom
    )
  }
  def + (that: Rational): Rational = {
    new Rational(
      this.numer*that.denom + that.numer*this.denom, 
      this.denom*that.denom
    )
  }
  //NEGATE
  def neg: Rational = new Rational(-numer, denom)
  def unary_- :Rational = new Rational(-numer, denom) //prefix operation 
  
  //SUBTRACTION
  def sub(that: Rational): Rational = add(that.neg) 
  
  def - (that: Rational): Rational = this + -that
  

  //COMPARISON
  def less(that: Rational): Boolean = {
    this.numer*that.denom < that.numer*this.denom
  }
  def < (that: Rational): Boolean = {
    this.numer*that.denom < that.numer*this.denom
  }
  
  //MAX
  def max(that: Rational): Rational = {
    if(this.less(that)) that else this
  }
  
  def max2(that: Rational): Rational = {
    if(this < that) that else this
  }
  //A toString method of a class will always be called whenever we println
  override def toString = numer + "/" + denom
}