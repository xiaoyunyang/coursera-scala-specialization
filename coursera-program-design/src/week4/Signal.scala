package week4

import Signal.caller
import scala.util.DynamicVariable

class Signal[T](expr: => T) {
  import Signal._
  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  update(expr)
  
  //update method gets called during the initialization of the Signal or
  //someone calls an update operation on a Var, or the value of a dependent
  ///signal changes. 
  //"protected" means only subclasses of Signal has access to this method
  //clients of the Signal cannot. This means clients of Signal cannot call update
  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }
  protected def computeValue(): Unit = {
    val newValue = caller.withValue(this)(myExpr())
    //re-evaluating the callers
    if (myValue != newValue) {
      myValue = newValue
      val obs = observers
      observers = Set() //clear the observers set
      obs.foreach(_.computeValue()) //add the updated observer into the observer sets
    }
  }
  
  
  //catches cyclic signal s() = s() + 1, If you don't add this assert, then infinite 
  //recursion and stackoverflow
  def apply(): T = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition")
    myValue
  }
}
object NoSignal extends Signal[Nothing](???) {
  //below code disables computeValue because we can't evaluate an expression of type Nothing
  override def computeValue() = () //empty expression can't evaluate NoSignal
}
object Signal {

  import scala.util.DynamicVariable
  
  //private val caller = new StackableVariable[Signal[_]](NoSignal)    // <--global variable
  
  /* global variable caller! Global variables in concurrency is bad idea (results in 
   * race conditions). One way to do that is to use synchronization, which comes
   * with its own problems (use of threads could create deadlock). We replace the 
   * "new StackableVariable" below by "new DynamicVariable" in 
   * scala.util.DynamicVariable to replace global state by thread-local state (each
   * thread accesses a separate copy of a variable.
   * 
   */
  private val caller = new DynamicVariable[Signal[_]](NoSignal)
  
  def apply[T](expr: => T) = new Signal(expr)
}

//Var is a sub-class of Signal
class Var[T](expr: => T) extends Signal[T](expr) {
  //client of Var can call update. That's why here, the keyword "protected" is overridden.
  override def update(expr: => T): Unit = super.update(expr)
}
object Var {
  def apply[T](expr: => T) = new Var(expr) //constructor
}

  //global variables - don't use if doing concurrency
class StackableVariable[T](init: T) {
  private var values: List[T] = List(init)
  def value: T = values.head
  def withValue[R](newValue: T)(op: => R): R = {
    values = newValue :: values
    try op finally values = values.tail
  }
}