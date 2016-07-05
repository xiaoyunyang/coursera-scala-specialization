package parallel

object transfer {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(54); 
	var uidCount = 0L
	
	class Account(private var amount: Int = 0) {
		def getAmount = println(this.amount)
		
		val x = new AnyRef {} //x is called a monitor
	  def getUniqueId(): Long = x.synchronized {
		  uidCount = uidCount+1
		  uidCount
	  }
		val uid = getUniqueId()
		
		private def lockAndTransfer(target: Account, n: Int) =
			this.synchronized {
				target.synchronized {
					this.amount -= n
					target.amount += n
				}
			}
		
		def transferDeadlock(target: Account, n: Int) =
			this.synchronized {
				target.synchronized {
					this.amount -= n
					target.amount += n
				}
			}
		def transfer(target: Account, n: Int) =
			if(this.uid < target.uid) this.lockAndTransfer(target, n)
			else target.lockAndTransfer(this, -n)
			
	};System.out.println("""uidCount  : Long = """ + $show(uidCount ));$skip(928); 
	

	def startThreadDeadlock(src: Account, targ: Account, n: Int) = {
		val t = new Thread {
			override def run() {
				for(i <- 0 until n) src.transferDeadlock(targ, 1)
			}
		}
		t.start()
		t
	};System.out.println("""startThreadDeadlock: (src: parallel.transfer.Account, targ: parallel.transfer.Account, n: Int)Thread""");$skip(317); 
	
	def startThread(src: Account, targ: Account, n: Int) = {
		val t = new Thread {
			override def run() {
				for(i <- 0 until n) {
					src.transfer(targ, 1)
					val srcid = src.uid
					val targid = targ.uid
					println(s"transfer #$i from Account #$srcid to Account #$targid")
				}
			}
		}
		t.start()
		t
	};System.out.println("""startThread: (src: parallel.transfer.Account, targ: parallel.transfer.Account, n: Int)Thread""");$skip(29); 
	val a1 = new Account(50000);System.out.println("""a1  : parallel.transfer.Account = """ + $show(a1 ));$skip(29); 
	val a2 = new Account(70000);System.out.println("""a2  : parallel.transfer.Account = """ + $show(a2 ));$skip(198); 
	
	/* The following demonstrates deadlock */
	//val t = startThreadDeadlock(a1, a2, 150000)
	//val s = startThreadDeadlock(a2, a1, 150000)

	//t.join()
	//s.join()
	
	val t = startThread(a1, a2, 3);System.out.println("""t  : Thread = """ + $show(t ));$skip(32); 
	val s = startThread(a2, a1, 5);System.out.println("""s  : Thread = """ + $show(s ));$skip(11); 

	t.join();$skip(10); 
	s.join()}
	
}
