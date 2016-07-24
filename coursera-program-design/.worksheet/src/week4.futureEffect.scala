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
  	
  };import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(579); 
  
  val socket = Socket();System.out.println("""socket  : week4.futureEffect.Socket.type = """ + $show(socket ));$skip(39); 
  val packet = socket.readFromMemory();System.out.println("""packet  : Array[Byte] = """ + $show(packet ));$skip(125); 
  //block for 50,000 ns (3 days)
  //only continue if there is no exception
  val confirmation = socket.sendToEurope(packet)
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
  	
  };System.out.println("""confirmation  : Array[Byte] = """ + $show(confirmation ));$skip(590); 
  val socketFuture = SocketFuture();System.out.println("""socketFuture  : week4.futureEffect.SocketFuture.type = """ + $show(socketFuture ));$skip(72); 
  val packetFuture: Future[Array[Byte]] = socketFuture.readFromMemory();System.out.println("""packetFuture  : scala.concurrent.Future[Array[Byte]] = """ + $show(packetFuture ));$skip(110); 
 	
 	val confirmationFuture: Future[Array[Byte]] =
 		packetFuture.flatMap(p => socketFuture.sendToEurope(p));System.out.println("""confirmationFuture  : scala.concurrent.Future[Array[Byte]] = """ + $show(confirmationFuture ))}
 	
}
