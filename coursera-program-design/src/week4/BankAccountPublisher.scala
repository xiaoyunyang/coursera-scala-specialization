package week4

class BankAccountPublisher extends Publisher {
  //we publish everytime we change the state of the bank account
  private var balance = 0
  
  //accessor method
  def currentBalance = balance       
  
  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
    publish()            // <--
  }
  
  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      publish()          // <--
    } else throw new Error("insufficient funds")
  }

}
//consolidator is a subscriber to maintain the total balance of a list of account
class Consolidator(observed: List[BankAccountPublisher]) extends Subscriber {
  observed.foreach(_.subscribe(this)) //subscribe to all BankAccounts
  
  private var total: Int = _
  compute()
  
  private def compute() = {
    total = observed.map(_.currentBalance).sum
  }
  //everytime something changes, you compute
  def handler(pub: Publisher) = compute()
  def totalBalance = total
}