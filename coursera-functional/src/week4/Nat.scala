package week4

//Peano Numbers
abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor: Nat = new NonZero(this) //holds no matter if Zero or NonZero
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}
//Zero Natural Numbers
object Zero extends Nat {
  def isZero: Boolean = true
  def predecessor: Nat = throw new NoSuchElementException("Zero has no predecessor")

  def + (that: Nat): Nat = that
  def - (that: Nat): Nat = {
    if(that.isZero) this
    else throw new Error("Cannot substract to get negative")
  }
}
//NonZero Natural Numbers
class NonZero(n: Nat) extends Nat {
  def isZero: Boolean = false
  def predecessor: Nat = n

  def + (that: Nat): Nat =  new NonZero(this + that)
  def - (that: Nat): Nat = {
    if(that.isZero) this
    else n - that.predecessor //this is the same as (this - that) because n = this.predessor
  } 
}
