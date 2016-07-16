package parallel

object parallelJVM {

	val a = new Thread()                      //> a  : Thread = Thread[Thread-0,5,main]
	a.start()
	/*****   What is a Thread *****/
	class HelloThread extends Thread {
		override def run() {
			println("Hello World!") //what the thread does
		}
	}
	class HelloThread2 extends Thread {
		override def run() {
			println("Hello")
			println("World!")
		}
	}
	val t = new HelloThread()                 //> t  : parallel.parallelJVM.HelloThread = Thread[Thread-1,5,main]
	val s1 = new HelloThread2()               //> s1  : parallel.parallelJVM.HelloThread2 = Thread[Thread-2,5,main]
	val s2 = new HelloThread2()               //> s2  : parallel.parallelJVM.HelloThread2 = Thread[Thread-3,5,main]
	t.start()
	s1.start()
	s2.start()
	//If you don't call join on t, s1, s2 threads,
	//these computation will not return to the main thread and you don't get the results
	t.join()                                  //> Hello World!
	s1.join()                                 //> Hello
                                                  //| Hello
                                                  //| World!
	s2.join()                                 //> World!
  
  /****** Asynchrnously generate unique IDs *******/
  //threads are inherently asynchronous
  var uidCountAsync = 0L                          //> uidCountAsync  : Long = 0

  def getUniqueIdAsync(): Long = {
	  uidCountAsync = uidCountAsync+1
	  uidCountAsync
  }                                               //> getUniqueIdAsync: ()Long
  
  def startThreadAsync() = {
  	val t = new Thread {
  		override def run() {
  			val uids = for (i <- 0 until 10) yield getUniqueIdAsync()
  			println(uids)
  		}
  	}
  	t.start()
  	t
  }                                               //> startThreadAsync: ()Thread
  val asyncId1 = startThreadAsync()               //> asyncId1  : Thread = Thread[Thread-4,5,main]
  val asyncId2 = startThreadAsync()               //> asyncId2  : Thread = Thread[Thread-5,5,main]
	
	//both threads could output 1 if asyncId2 executes before asyncId1 finishes execution
	//because they are asynchronous and both mutates var uidCountAsync
	asyncId1.join()  //outputs: Vector(1, 2, 4, 6, 8, 10, 12, 14, 16, 18)
                                                  //> Vector(2, 4, 6, 8, 10, 12, 14, 16, 18, 19)
                                                  //| Vector(1, 3, 5, 7, 9, 10, 11, 13, 15, 17)
	asyncId2.join()  //outputs: Vector(1, 3, 5, 7, 9, 11, 13, 15, 17, 19)
	 
	/****** Synchrnously generate unique IDs *******/
	
	var uidCountSync = 0L                     //> uidCountSync  : Long = 0
	
	val x = new AnyRef {} //x is called a monitor
                                                  //> x  : AnyRef = parallel.parallelJVM$$anonfun$main$1$$anon$3@dfd3711
  
  def getUniqueIdAtomic(): Long = x.synchronized {
	  uidCountSync = uidCountSync+1
	  uidCountSync
  }                                               //> getUniqueIdAtomic: ()Long
  
  def startThreadSync() = {
  	val t = new Thread {
  		override def run() {
  			val uids = for (i <- 0 until 10) yield getUniqueIdAtomic()
  			println(uids)
  		}
  	}
  	t.start()
  	t
  }                                               //> startThreadSync: ()Thread
  val syncId1 = startThreadSync()                 //> syncId1  : Thread = Thread[Thread-6,5,main]
  val syncId2 = startThreadSync()                 //> syncId2  : Thread = Thread[Thread-7,5,main]
	
	syncId1.join()                            //> Vector(1, 3, 4, 6, 8, 9, 11, 13, 14, 16)
                                                  //| Vector(2, 5, 7, 10, 12, 15, 17, 18, 19, 20)
	syncId2.join()
	 
}