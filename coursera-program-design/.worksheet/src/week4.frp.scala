package week4

object frp {
	type Position = (Int);import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(81); 
	def mousePosition = Signal(3);System.out.println("""mousePosition: => week4.Signal[Int]""");$skip(352); 
	

  /** Fundamental Signal Operation */
 	//given a rectangle defined by LL (lower left) corner and UR (upper right) corner,
 	//we check if the mousePos is inside the rectangle or not inside the rectangle
 	def inRectangle(LL: Position, UR: Position): Signal[Boolean] = {
 		Signal {
 			val pos = mousePosition()
 			LL <= pos && pos <= UR
 		}
  };System.out.println("""inRectangle: (LL: week4.frp.Position, UR: week4.frp.Position)week4.Signal[Boolean]""");$skip(50); val res$0 = 
  
  mousePosition();System.out.println("""res0: Int = """ + $show(res$0));$skip(71);  //the current mouse position
  val sig3 = Signal(3);System.out.println("""sig3  : week4.Signal[Int] = """ + $show(sig3 ));$skip(56); 	//the signal that is always 3 (constant signal)

	/** Constant Signals */
  val constSignal = Signal(1);System.out.println("""constSignal  : week4.Signal[Int] = """ + $show(constSignal ));$skip(113); 
  
  /** Variable Signals */
  //time varying Signals
  
  /* simple assignment and update */
	
  val x = Var(1);System.out.println("""x  : week4.Var[Int] = """ + $show(x ));$skip(24); 
  val y = Signal(x()*2);System.out.println("""y  : week4.Signal[Int] = """ + $show(y ));$skip(6); val res$1 = 
  x();System.out.println("""res1: Int = """ + $show(res$1));$skip(5); val res$2 = 
	y();System.out.println("""res2: Int = """ + $show(res$2));$skip(18); 
	val x1 = x() + 1;System.out.println("""x1  : Int = """ + $show(x1 ));$skip(10); 
	x() = x1;$skip(5); val res$3 = 
	x();System.out.println("""res3: Int = """ + $show(res$3));$skip(5); val res$4 = 
	y();System.out.println("""res4: Int = """ + $show(res$4));$skip(22); 

	
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
	a = a + 1;$skip(45); val res$5 = 
	b;System.out.println("""res5: Int = """ + $show(res$5));$skip(33);  //still 4. Not automatically updated to 9
  
  val aSig: Var[Int] = Var(2);System.out.println("""aSig  : week4.Var[Int] = """ + $show(aSig ));$skip(37); 
  val bSig: Var[Int] = Var(2*aSig());System.out.println("""bSig  : week4.Var[Int] = """ + $show(bSig ));$skip(56); 
  val bVal: Int = bSig();System.out.println("""bVal  : Int = """ + $show(bVal ));$skip(17);   //to "dereference" the signal
  aSig.update(3);$skip(22); val res$6 = 
  bSig();System.out.println("""res6: Int = """ + $show(res$6));$skip(180);   //We get 6!


	/** BankAccount Signal */
  def consolidated(accts: List[BankAccountSignal]): Signal[Int] = {
		Signal(accts.map(_.balance()).sum) //a signal of sum of all accounts' balance
	};System.out.println("""consolidated: (accts: List[week4.BankAccountSignal])week4.Signal[Int]""");$skip(46); 
	
	
	val aAcc, bAcc = new BankAccountSignal();System.out.println("""aAcc  : week4.BankAccountSignal = """ + $show(aAcc ));System.out.println("""bAcc  : week4.BankAccountSignal = """ + $show(bAcc ));$skip(40); 
	val c = consolidated(List(aAcc, bAcc));System.out.println("""c  : week4.Signal[Int] = """ + $show(c ));$skip(5); val res$7 = 
	c();System.out.println("""res7: Int = """ + $show(res$7));$skip(17); 
	aAcc deposit 20;$skip(5); val res$8 = 
	c();System.out.println("""res8: Int = """ + $show(res$8));$skip(17); 
	bAcc deposit 30;$skip(5); val res$9 = 
	c();System.out.println("""res9: Int = """ + $show(res$9));$skip(30); 
	val xchange = Signal(246.00);System.out.println("""xchange  : week4.Signal[Double] = """ + $show(xchange ));$skip(38); 
	val inDollar = Signal(c()*xchange());System.out.println("""inDollar  : week4.Signal[Double] = """ + $show(inDollar ));$skip(12); val res$10 = 
	inDollar();System.out.println("""res10: Double = """ + $show(res$10));$skip(18); 
	bAcc withdraw 10;$skip(12); val res$11 = 
	inDollar();System.out.println("""res11: Double = """ + $show(res$11));$skip(48); 
	
	/** Caveat with Signals */
	val num = Var(1);System.out.println("""num  : week4.Var[Int] = """ + $show(num ));$skip(31); 
	val twice = Signal(num() * 2);System.out.println("""twice  : week4.Signal[Int] = """ + $show(twice ));$skip(11); 
	num() = 2;$skip(9); val res$12 = 
	twice();System.out.println("""res12: Int = """ + $show(res$12));$skip(19); 
	var num2 = Var(1);System.out.println("""num2  : week4.Var[Int] = """ + $show(num2 ));$skip(33); 
	val twice2 = Signal(num2() * 2);System.out.println("""twice2  : week4.Signal[Int] = """ + $show(twice2 ));$skip(15); 
	num2 = Var(2);$skip(10); val res$13 = 
	twice2();System.out.println("""res13: Int = """ + $show(res$13))}
}
