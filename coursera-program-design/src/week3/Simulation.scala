package week3

/** Class Hierarchy: Simulation > Gates (Wire, AND, OR, NOT) > 
 *  Circuits (HalfAdder, FullAdder) > MySimulation and Param trait.
 */
trait Simulation {
  type Action = () => Unit
  
  case class Event(time: Int, action: Action)
  
  private type Agenda = List[Event]
  private var agenda: Agenda = List()
  
  //to handle time
  private var curtime = 0
  def currentTime: Int = curtime
  
  def afterDelay(delay: Int)(block: => Unit): Unit = {   
    val item = Event(currentTime + delay, () => block)
    agenda = insert(agenda, item)
  }
  
  //Ensures the agenda is time-sorted
  private def insert(agenda: List[Event], item: Event): List[Event] = agenda match {
    case first :: rest if(first.time <= item.time) => first :: insert(rest, item)
    case _ => item :: agenda
  }
  def run(): Unit = {
    afterDelay(0) {
      println("*** simulation started, time = "+currentTime+" ***")
    }
    loop()
  }
  
  //The event handling loop remove successive elements from the agenda and performs
  //the associated actions
  private def loop(): Unit = agenda match {
    case first :: rest => 
      agenda = rest
      curtime = first.time
      first.action()
      loop()
    case Nil => 
  }
}


abstract class Gates extends Simulation {
  //abstract methods
  def InverterDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int
  
  class Wire {
    //represents the current val of the signal
    private var sigVal = false 
    
    //represents the actions attached to the current signal
    private var actions: List[Action] = List() 
    
    //returns the current value of signal transported by the wire
    def getSignal: Boolean = sigVal
    
    def setSignal(s: Boolean): Unit = {
      if(s!=sigVal) {
        sigVal = s
        actions foreach (_()) //same as for (a <- actions) a()
      }
    }
    
    //Attaches the specific procedures to the actions of the wire
    def addAction(a: Action): Unit = {
      actions = a :: actions
      a()
    }
  }  
  
  //The Logic Gates
  def inverter(input: Wire, output: Wire): Unit = {
    def inverterAction(): Unit = {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) { output setSignal !inputSig }
    }
    input addAction inverterAction
  }
  
  def andGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def andAction(): Unit = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      //perform whenever one of the two signal changes
      afterDelay(AndGateDelay) { output setSignal (in1Sig & in2Sig) }
    }
    in1 addAction andAction
    in2 addAction andAction
  }
  
  def orGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def orAction(): Unit = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      //perform whenever one of the two signal changes
      afterDelay(OrGateDelay) { output setSignal (in1Sig | in2Sig) }
    }
    in1 addAction orAction
    in2 addAction orAction
  }
  
  /* In this alternative orGate, the time to stabilize event is longer */
  //a | b == !(!a & !b)
  def orGateAlt(in1: Wire, in2: Wire, output: Wire): Unit = {
    val notIn1, notIn2, notOut = new Wire
    inverter(in1, notIn1)
    inverter(in2, notIn2)
    andGate(notIn1, notIn2, notOut)
    inverter(notOut, output)
  }
  
  /* Probe is attached to a wire */
  def probe(name: String, wire: Wire): Unit = {
    def probeAction(): Unit = {
      println(s"$name $currentTime new-value = ${wire.getSignal}")
    }
    wire addAction probeAction
  }
}


abstract class Circuits extends Gates {
  //half adder
  def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire): Unit = {
    val d, e = new Wire
    orGate(a, b, d)
    andGate(a, b, c)
    inverter(c, e)
    andGate(d, e, s)
  }
  
  //A full bit adder
  def fullAdder(a: Wire, b: Wire, cin: Wire, sum: Wire, cout: Wire): Unit = {
    val s, c1, c2 = new Wire
    halfAdder(b, cin, s, c1)
    halfAdder(a, s, sum, c2)
    orGate(c1, c2, cout)
  }
}

//fixes the technology specific constants
trait Parameters {
  def InverterDelay = 2
  def AndGateDelay = 3
  def OrGateDelay = 5
}
