package week3

object accounts {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(101); 
	/** Demonstrate Functions and State **/
	val acct = new BankAccount;System.out.println("""acct  : week3.BankAccount = """ + $show(acct ));$skip(19); 
	
	acct deposit 50;$skip(15); val res$0 = 
	acct.toString;System.out.println("""res0: String = """ + $show(res$0));$skip(219); val res$1 = 
	
	//acct is a stateful object because when we perform the same operation twice below, we
	//get back different answers. This is because the effect of the withdraw operation
	//depends on the history.
	acct withdraw 20;System.out.println("""res1: Int = """ + $show(res$1));$skip(18); val res$2 = 
	acct withdraw 20
	
	//below code throws insufficient funds java.lang.Error
	//acct withdraw 15

	//BankAccountProxy is a stateful object.
	class BankAccountProxy(ba: BankAccount) {
		def deposit(amount: Int): Unit = ba.deposit(amount)
		def withdraw(amount: Int): Int = ba.withdraw(amount)
	};System.out.println("""res2: Int = """ + $show(res$2));$skip(533); 
	
	/*** Identity and Change */
	//To prove that x and y are not operationally equivalent, we need to show that there
	//exists one test that can distinguish between them
	//Test operational equivalence
	val x, y = new BankAccount;System.out.println("""x  : week3.BankAccount = """ + $show(x ));System.out.println("""y  : week3.BankAccount = """ + $show(y ));$skip(162);  //x and y are not the same
  def f(a: BankAccount, b: BankAccount) = {
  	try {
  		a.deposit(30)
  		b.withdraw(20)
  	 }catch {
  	 	case e: Error => "Insufficient Funds Error"
  	 }
  };System.out.println("""f: (a: week3.BankAccount, b: week3.BankAccount)Any""");$skip(130); val res$3 = 
  
  //The test "f" can distinguish between x and y as shown below. Hence, x and y are not
  //operationally equivalent
  f(x, x);System.out.println("""res3: Any = """ + $show(res$3));$skip(10); val res$4 = 
  f(x, y);System.out.println("""res4: Any = """ + $show(res$4));$skip(147); 
 	
 	//still doesn't work with assignment - In general, whenever you have assignment.
 	//the program ceases to be valid
 	val a = new BankAccount;System.out.println("""a  : week3.BankAccount = """ + $show(a ));$skip(12); 
 	val b = a;System.out.println("""b  : week3.BankAccount = """ + $show(b ));$skip(10); val res$5 = 
 	f(a, a);System.out.println("""res5: Any = """ + $show(res$5));$skip(10); val res$6 = 
  f(a, b);System.out.println("""res6: Any = """ + $show(res$6))}
 	 	

/*
	/** Demonstrates Observer Pattern*/
	val f = new BankAccountObserver
	val g = new BankAccountObserver
	val h = new Consolidator(List(f,g))
	h.totalBalance
	f deposit 20
	h.totalBalance
	g deposit 30
	h.totalBalance

  def consolidated(accts: List[BankAccountSignal]): Signal[Int] =
  	Signal(accts.map(_.balance()).sum)
  val a = new BankAccountSignal()
  val b = new BankAccountSignal()
  val c = consolidated(List(a,b))
  c()
  a deposit 20
  c()
  b deposit 30
  c()
  
  val xchange = Signal(246.00)
  val inDollar = Signal(c()*xchange())
  inDollar()
  b withdraw 10
  inDollar()
 */
}
