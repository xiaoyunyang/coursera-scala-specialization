package week4

import Signal.caller
import scala.util.DynamicVariable

class Signal[T](expr: => T) {
  import Signal._
  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  update(expr)
  
  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }
  protected def computeValue(): Unit = {
    val newValue = caller.withValue(this)(myExpr())
    if (myValue != newValue) {
      myValue = newValue
      val obs = observers
      observers = Set()
      obs.foreach(_.computeValue())
    }
  }
  
  
  //cyclic signal s() = s() + 1
  def apply(): T = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition")
    myValue
  }
}
object NoSignal extends Signal[Nothing](???) {
  override def computeValue() = () //empty expression can't evalute NoSignal
}
object Signal {
  //private val caller = new StackableVariable[Signal[_]](NoSignal)    // <--global variable
  import scala.util.DynamicVariable
  private val caller = new DynamicVariable[Signal[_]](NoSignal)
  def apply[T](expr: => T) = new Signal(expr)
}

//Var is a sub-class of Signal
class Var[T](expr: => T) extends Signal[T](expr) {
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