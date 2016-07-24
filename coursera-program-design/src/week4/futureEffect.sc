package week4

//import Future
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

object futureEffect {

	/** Error as an Effect - Try[T] handles exceptions */

	/** Latency as Effect - Future[T] handles exception and latency */
  trait Socket {
  	def readFromMemory(): Array[Byte]
  	def sendToEurope(packet: Array[Byte]): Array[Byte]
  }

  object Socket {
  	def apply() = Socket
  	def readFromMemory(): Array[Byte] = Array(2.toByte)
  	def sendToEurope(packet: Array[Byte]): Array[Byte] = Array(4.toByte)
  	
  }
  
  val socket = Socket()                           //> socket  : week4.futureEffect.Socket.type = week4.futureEffect$Socket$@1b701d
                                                  //| a1
  val packet = socket.readFromMemory()            //> packet  : Array[Byte] = Array(2)
  //block for 50,000 ns (3 days)
  //only continue if there is no exception
  val confirmation = socket.sendToEurope(packet)  //> confirmation  : Array[Byte] = Array(4)
  //block for 150,000,000 ns (5 years)
  //only continue if there is no exception
  
  //If a computation takes a lot of time, we will use a callback
 	//callback needs to use pattern matching
 	
 	trait SocketFuture {
 		def readFromMemory(): Future[Array[Byte]]
 		def sendToEurope(packet: Array[Byte]): Future[Array[Byte]]
 	}
 	
 	object SocketFuture {
  	def apply() = SocketFuture
  	def readFromMemory(): Future[Array[Byte]] = Future(Array(2.toByte))
  	def sendToEurope(packet: Array[Byte]): Future[Array[Byte]] = Future(Array(4.toByte))
  	
  }
  val socketFuture = SocketFuture()               //> socketFuture  : week4.futureEffect.SocketFuture.type = week4.futureEffect$$
                                                  //| anonfun$main$1$SocketFuture$3$@2ef9b8bc
  val packetFuture: Future[Array[Byte]] = socketFuture.readFromMemory()
                                                  //> packetFuture  : scala.concurrent.Future[Array[Byte]] = scala.concurrent.imp
                                                  //| l.Promise$DefaultPromise@69ea3742
 	
 	val confirmationFuture: Future[Array[Byte]] =
 		packetFuture.flatMap(p => socketFuture.sendToEurope(p))
                                                  //> confirmationFuture  : scala.concurrent.Future[Array[Byte]] = scala.concurre
                                                  //| nt.impl.Promise$DefaultPromise@6adede5
 	
}