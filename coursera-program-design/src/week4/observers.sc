package week4

object observers {
  val a,b = new BankAccountPublisher              //> a  : week4.BankAccountPublisher = week4.BankAccountPublisher@380fb434
                                                  //| b  : week4.BankAccountPublisher = week4.BankAccountPublisher@3b81a1bc
  //The Consolidator that takes bankAccounts a and b and always maintain their total balance
  val c = new Consolidator(List(a,b))             //> c  : week4.Consolidator = week4.Consolidator@3dd3bcd
 	c.totalBalance                            //> res0: Int = 0
  
  a deposit 20
  c.totalBalance                                  //> res1: Int = 20
  b deposit 30
  c.totalBalance                                  //> res2: Int = 50
  
  a withdraw 20
  c.totalBalance                                  //> res3: Int = 30

}