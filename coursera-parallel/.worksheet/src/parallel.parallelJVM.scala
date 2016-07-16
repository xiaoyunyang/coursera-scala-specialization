package parallel

object parallelJVM {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(61); 

	val a = new Thread();System.out.println("""a  : Thread = """ + $show(a ));$skip(11); 
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
	};$skip(285); 
	val t = new HelloThread();System.out.println("""t  : parallel.parallelJVM.HelloThread = """ + $show(t ));$skip(29); 
	val s1 = new HelloThread2();System.out.println("""s1  : parallel.parallelJVM.HelloThread2 = """ + $show(s1 ));$skip(29); 
	val s2 = new HelloThread2();System.out.println("""s2  : parallel.parallelJVM.HelloThread2 = """ + $show(s2 ));$skip(11); 
	t.start();$skip(12); 
	s1.start();$skip(12); 
	s2.start();$skip(144); 
	//If you don't call join on t, s1, s2 threads,
	//these computation will not return to the main thread and you don't get the results
	t.join();$skip(11); 
	s1.join();$skip(11); 
	s2.join();$skip(121); 
  
  /****** Asynchrnously generate unique IDs *******/
  //threads are inherently asynchronous
  var uidCountAsync = 0L;System.out.println("""uidCountAsync  : Long = """ + $show(uidCountAsync ));$skip(92); 

  def getUniqueIdAsync(): Long = {
	  uidCountAsync = uidCountAsync+1
	  uidCountAsync
  };System.out.println("""getUniqueIdAsync: ()Long""");$skip(196); 
  
  def startThreadAsync() = {
  	val t = new Thread {
  		override def run() {
  			val uids = for (i <- 0 until 10) yield getUniqueIdAsync()
  			println(uids)
  		}
  	}
  	t.start()
  	t
  };System.out.println("""startThreadAsync: ()Thread""");$skip(36); 
  val asyncId1 = startThreadAsync();System.out.println("""asyncId1  : Thread = """ + $show(asyncId1 ));$skip(36); 
  val asyncId2 = startThreadAsync();System.out.println("""asyncId2  : Thread = """ + $show(asyncId2 ));$skip(228); 
	
	//both threads could output 1 if asyncId2 executes before asyncId1 finishes execution
	//because they are asynchronous and both mutates var uidCountAsync
	asyncId1.join();$skip(71);   //outputs: Vector(1, 2, 4, 6, 8, 10, 12, 14, 16, 18)
	asyncId2.join();$skip(79);   //outputs: Vector(1, 3, 5, 7, 9, 11, 13, 15, 17, 19)
	 
	/****** Synchrnously generate unique IDs *******/
	
	var uidCountSync = 0L;System.out.println("""uidCountSync  : Long = """ + $show(uidCountSync ));$skip(49); 
	
	val x = new AnyRef {};System.out.println("""x  : AnyRef = """ + $show(x ));$skip(107);  //x is called a monitor
  
  def getUniqueIdAtomic(): Long = x.synchronized {
	  uidCountSync = uidCountSync+1
	  uidCountSync
  };System.out.println("""getUniqueIdAtomic: ()Long""");$skip(196); 
  
  def startThreadSync() = {
  	val t = new Thread {
  		override def run() {
  			val uids = for (i <- 0 until 10) yield getUniqueIdAtomic()
  			println(uids)
  		}
  	}
  	t.start()
  	t
  };System.out.println("""startThreadSync: ()Thread""");$skip(34); 
  val syncId1 = startThreadSync();System.out.println("""syncId1  : Thread = """ + $show(syncId1 ));$skip(34); 
  val syncId2 = startThreadSync();System.out.println("""syncId2  : Thread = """ + $show(syncId2 ));$skip(18); 
	
	syncId1.join();$skip(16); 
	syncId2.join()}
	 
}
