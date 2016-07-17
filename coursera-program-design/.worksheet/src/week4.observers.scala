package week4

object observers {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(70); 
  val a,b = new BankAccountPublisher;System.out.println("""a  : week4.BankAccountPublisher = """ + $show(a ));System.out.println("""b  : week4.BankAccountPublisher = """ + $show(b ));$skip(131); 
  //The Consolidator that takes bankAccounts a and b and always maintain their total balance
  val c = new Consolidator(List(a,b));System.out.println("""c  : week4.Consolidator = """ + $show(c ));$skip(17); val res$0 = 
 	c.totalBalance;System.out.println("""res0: Int = """ + $show(res$0));$skip(18); 
  
  a deposit 20;$skip(17); val res$1 = 
  c.totalBalance;System.out.println("""res1: Int = """ + $show(res$1));$skip(15); 
  b deposit 30;$skip(17); val res$2 = 
  c.totalBalance;System.out.println("""res2: Int = """ + $show(res$2));$skip(19); 
  
  a withdraw 20;$skip(17); val res$3 = 
  c.totalBalance;System.out.println("""res3: Int = """ + $show(res$3))}

}
