package calculator
import java.nio.ByteBuffer

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(95); 
  val MaxTweetLength = Signal(140);System.out.println("""MaxTweetLength  : <error> = """ + $show(MaxTweetLength ));$skip(29); 
	val mySig = Signal("Hello");System.out.println("""mySig  : <error> = """ + $show(mySig ));$skip(14); val res$0 = 
	sig().length;System.out.println("""res0: <error> = """ + $show(res$0));$skip(30); 
	
	val sig2 = Signal("Café€");System.out.println("""sig2  : <error> = """ + $show(sig2 ));$skip(15); val res$1 = 
	sig2().length;System.out.println("""res1: <error> = """ + $show(res$1));$skip(33); val res$2 = 
	
	"hello".toArray.map(_.toByte);System.out.println("""res2: Array[Byte] = """ + $show(res$2));$skip(79); val res$3 = 
	"Café".toArray.map(_.toByte)
				.foldRight(0)((a,b) => if(a<0) 2+b else 1+b);System.out.println("""res3: Int = """ + $show(res$3));$skip(12); val res$4 = 
  'h'.toInt;System.out.println("""res4: Int = """ + $show(res$4));$skip(12); val res$5 = 
 	'e'.toInt;System.out.println("""res5: Int = """ + $show(res$5));$skip(12); val res$6 = 
 	'l'.toInt;System.out.println("""res6: Int = """ + $show(res$6));$skip(12); val res$7 = 
 	'l'.toInt;System.out.println("""res7: Int = """ + $show(res$7));$skip(12); val res$8 = 
 	'o'.toInt;System.out.println("""res8: Int = """ + $show(res$8));$skip(12); val res$9 = 
 	'a'.toInt;System.out.println("""res9: Int = """ + $show(res$9));$skip(12); val res$10 = 
 	'€'.toInt;System.out.println("""res10: Int = """ + $show(res$10));$skip(28); val res$11 = 
 	"hello".getBytes().length;System.out.println("""res11: Int = """ + $show(res$11));$skip(27); val res$12 = 
 	"café".getBytes().length;System.out.println("""res12: Int = """ + $show(res$12));$skip(52); val res$13 = 
 
 	ByteBuffer.wrap(Array[Byte](1, 2, 3, 4)).getInt;System.out.println("""res13: Int = """ + $show(res$13));$skip(80); 
  
 	//This should be 16 characters
 	val tweet = "foo blabla \uD83D\uDCA9 bar";System.out.println("""tweet  : String = """ + $show(tweet ));$skip(15); val res$14 = 
 	tweet.length;System.out.println("""res14: Int = """ + $show(res$14));$skip(26); val res$15 = 
 	tweet.getBytes().length;System.out.println("""res15: Int = """ + $show(res$15));$skip(43); val res$16 = 
 	
 	tweet.codePointCount(0, tweet.length);System.out.println("""res16: Int = """ + $show(res$16));$skip(211); 
 	
 	def tweetRemainingCharsCount(tweetText: Signal[String]): Signal[Int] = {
    val textLen = tweetText().codePointCount(0, tweetText().length)
    val remain = MaxTweetLength - textLen
    Signal(remain)
  };System.out.println("""tweetRemainingCharsCount: (tweetText: <error>)<error>""")}
 	
 	
}
