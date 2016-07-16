package parallel

object transfer {
	var uidCount = 0L                         //> uidCount  : Long = 0
	
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
			
	}
	

	def startThreadDeadlock(src: Account, targ: Account, n: Int) = {
		val t = new Thread {
			override def run() {
				for(i <- 0 until n) src.transferDeadlock(targ, 1)
			}
		}
		t.start()
		t
	}                                         //> startThreadDeadlock: (src: parallel.transfer.Account, targ: parallel.transfe
                                                  //| r.Account, n: Int)Thread
	
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
	}                                         //> startThread: (src: parallel.transfer.Account, targ: parallel.transfer.Accou
                                                  //| nt, n: Int)Thread
	val a1 = new Account(50000)               //> a1  : parallel.transfer.Account = parallel.transfer$$anonfun$main$1$Account
                                                  //| $2@3d8c7aca
	val a2 = new Account(70000)               //> a2  : parallel.transfer.Account = parallel.transfer$$anonfun$main$1$Account
                                                  //| $2@5ebec15
	
	/* The following demonstrates deadlock */
	//val t = startThreadDeadlock(a1, a2, 150000)
	//val s = startThreadDeadlock(a2, a1, 150000)

	//t.join()
	//s.join()
	
	val t = startThread(a1, a2, 3)            //> t  : Thread = Thread[Thread-0,5,main]
	val s = startThread(a2, a1, 5)            //> s  : Thread = Thread[Thread-1,5,main]

	t.join()                                  //> transfer #0 from Account #1 to Account #2
                                                  //| transfer #0 from Account #2 to Account #1
                                                  //| transfer #1 from Account #1 to Account #2
                                                  //| transfer #1 from Account #2 to Account #1
                                                  //| transfer #2 from Account #1 to Account #2
                                                  //| transfer #2 from Account #2 to Account #1
	s.join()                                  //> transfer #4 from Account #2 to Account #1
	
}