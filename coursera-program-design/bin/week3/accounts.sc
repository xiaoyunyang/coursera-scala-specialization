package week3

object accounts {
	/** Demonstrate Functions and State **/
	val acct = new BankAccount                //> acct  : week3.BankAccount = Your balance is $0
	
	acct deposit 50
	acct.toString                             //> res0: String = Your balance is $50
	
	//acct is a stateful object because when we perform the same operation twice below, we
	//get back different answers. This is because the effect of the withdraw operation
	//depends on the history.
	acct withdraw 20                          //> res1: Int = 30
	acct withdraw 20                          //> res2: Int = 10
	
	//below code throws insufficient funds java.lang.Error
	//acct withdraw 15

	//BankAccountProxy is a stateful object.
	class BankAccountProxy(ba: BankAccount) {
		def deposit(amount: Int): Unit = ba.deposit(amount)
		def withdraw(amount: Int): Int = ba.withdraw(amount)
	}
	
	/*** Identity and Change */
	//To prove that x and y are not operationally equivalent, we need to show that there
	//exists one test that can distinguish between them
	//Test operational equivalence
	val x, y = new BankAccount //x and y are not the same
                                                  //> x  : week3.BankAccount = Your balance is $0
                                                  //| y  : week3.BankAccount = Your balance is $0
  def f(a: BankAccount, b: BankAccount) = {
  	try {
  		a.deposit(30)
  		b.withdraw(20)
  	 }catch {
  	 	case e: Error => "Insufficient Funds Error"
  	 }
  }                                               //> f: (a: week3.BankAccount, b: week3.BankAccount)Any
  
  //The test "f" can distinguish between x and y as shown below. Hence, x and y are not
  //operationally equivalent
  f(x, x)                                         //> res3: Any = 10
  f(x, y)                                         //> res4: Any = Insufficient Funds Error
 	
 	//still doesn't work with assignment - In general, whenever you have assignment.
 	//the program ceases to be valid
 	val a = new BankAccount                   //> a  : week3.BankAccount = Your balance is $0
 	val b = a                                 //> b  : week3.BankAccount = Your balance is $0
 	f(a, a)                                   //> res5: Any = 10
  f(a, b)                                         //> res6: Any = 20
 	 	

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