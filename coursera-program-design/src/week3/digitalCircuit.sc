package week3

/** Digital circuit simulators */
object digitalCircuit {
  /* Digital circuits are composed of wires, which transport
   * signals (true / false) that are transformed by components.
   * base components are:
   * 	Inverter - output is inverse of inputs
   * 	AND Gate - output is conjunciton of inputs
   *  OR Gate - output is disjunction of inputs
   * The components have a delay (e.g., output doesn't change instantaneously)
   *
   */
	object sim extends Circuits with Parameters
	import sim._
	val in1, in2, sum, carry = new Wire       //> in1  : week3.digitalCircuit.sim.Wire = week3.Gates$Wire@123772c4
                                                  //| in2  : week3.digitalCircuit.sim.Wire = week3.Gates$Wire@3498ed
                                                  //| sum  : week3.digitalCircuit.sim.Wire = week3.Gates$Wire@1a407d53
                                                  //| carry  : week3.digitalCircuit.sim.Wire = week3.Gates$Wire@3d8c7aca
	
	halfAdder(in1, in2, sum, carry)
	probe("sum", sum)                         //> sum 0 new-value = false
	probe("carry", carry)                     //> carry 0 new-value = false
	
	in1 setSignal true
	run()                                     //> *** simulation started, time = 0 ***
                                                  //| sum 8 new-value = true
  in2 setSignal true
  run()                                           //> *** simulation started, time = 8 ***
                                                  //| carry 11 new-value = true
                                                  //| sum 16 new-value = false
  
  in1 setSignal false
  run()                                           //> *** simulation started, time = 16 ***
                                                  //| carry 19 new-value = false
                                                  //| sum 24 new-value = true
	
	
}