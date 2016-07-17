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
	import sim._;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(551); 
	val in1, in2, sum, carry = new Wire;System.out.println("""in1  : week3.digitalCircuit.sim.Wire = """ + $show(in1 ));System.out.println("""in2  : week3.digitalCircuit.sim.Wire = """ + $show(in2 ));System.out.println("""sum  : week3.digitalCircuit.sim.Wire = """ + $show(sum ));System.out.println("""carry  : week3.digitalCircuit.sim.Wire = """ + $show(carry ));$skip(35); 
	
	halfAdder(in1, in2, sum, carry);$skip(19); 
	probe("sum", sum);$skip(23); 
	probe("carry", carry);$skip(22); 
	
	in1 setSignal true;$skip(7); 
	run();$skip(21); 
  in2 setSignal true;$skip(8); 
  run();$skip(25); 
  
  in1 setSignal false;$skip(8); 
  run()}
	
	
}
