package week4

object signal {
	type Position = (Int);import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(84); 
	def mousePosition = Signal(3);System.out.println("""mousePosition: => week4.Signal[Int]""");$skip(352); 
	

  /** Fundamental Signal Operation */
 	//given a rectangle defined by LL (lower left) corner and UR (upper right) corner,
 	//we check if the mousePos is inside the rectangle or not inside the rectangle
 	def inRectangle(LL: Position, UR: Position): Signal[Boolean] = {
 		Signal {
 			val pos = mousePosition()
 			LL <= pos && pos <= UR
 		}
  };System.out.println("""inRectangle: (LL: week4.signal.Position, UR: week4.signal.Position)week4.Signal[Boolean]""");$skip(50); val res$0 = 
  
  mousePosition();System.out.println("""res0: Int = """ + $show(res$0));$skip(71);  //the current mouse position
  val sig3 = Signal(3);System.out.println("""sig3  : week4.Signal[Int] = """ + $show(sig3 ));$skip(76); 	//the signal that is always 3 (constant signal)
  
  //time varying Signals
  
  /** Variable Signals */
  val sig = Var(3);System.out.println("""sig  : week4.Var[Int] = """ + $show(sig ));$skip(112); 
  
  //There are two ways to update a variable signal
  sig.update(5);$skip(44);  //from now on, sig returns 5 instead of 3
 	sig() = 5;$skip(190); 	//identical to above expression

	/* Key difference between a variable and a variable signal is that
	 * variables cannot guarantee linked members are automatically updated
	 * when one of them is updated.
	 */
	var a = 2;System.out.println("""a  : Int = """ + $show(a ));$skip(15); 
	var b = 2 * a;System.out.println("""b  : Int = """ + $show(b ));$skip(11); 
	a = a + 1;$skip(45); val res$1 = 
	b;System.out.println("""res1: Int = """ + $show(res$1));$skip(33);  //still 4. Not automatically updated to 9
  
  val aSig: Var[Int] = Var(2);System.out.println("""aSig  : week4.Var[Int] = """ + $show(aSig ));$skip(37); 
  val bSig: Var[Int] = Var(2*aSig());System.out.println("""bSig  : week4.Var[Int] = """ + $show(bSig ));$skip(56); 
  val bVal: Int = bSig();System.out.println("""bVal  : Int = """ + $show(bVal ));$skip(17);   //to "dereference" the signal
  aSig.update(3);$skip(22); val res$2 = 
  bSig();System.out.println("""res2: Int = """ + $show(res$2));$skip(180);   //We get 6!


	/** BankAccount Signal */
  def consolidated(accts: List[BankAccountSignal]): Signal[Int] = {
		Signal(accts.map(_.balance()).sum) //a signal of sum of all accounts' balance
	};System.out.println("""consolidated: (accts: List[week4.BankAccountSignal])week4.Signal[Int]""")}
	
	/*
	val aAcc, bAcc = new BankAccountSignal()
	val c = consolidated(List(aAcc, bAcc))
	*/
}
