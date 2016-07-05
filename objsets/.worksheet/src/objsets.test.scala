package objsets

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(32); val res$0 = 
1;System.out.println("""res0: Int(1) = """ + $show(res$0));$skip(22); 
	val set1 = new Empty;System.out.println("""set1  : objsets.Empty = """ + $show(set1 ));$skip(53); 
  val set2 = set1.incl(new Tweet("a", "a body", 20));System.out.println("""set2  : objsets.TweetSet = """ + $show(set2 ));$skip(53); 
  val set3 = set2.incl(new Tweet("b", "b body", 20));System.out.println("""set3  : objsets.TweetSet = """ + $show(set3 ));$skip(38); 
  val c = new Tweet("c", "c body", 7);System.out.println("""c  : objsets.Tweet = """ + $show(c ));$skip(38); 
  val d = new Tweet("d", "d body", 9);System.out.println("""d  : objsets.Tweet = """ + $show(d ));$skip(27); 
  val set4c = set3.incl(c);System.out.println("""set4c  : objsets.TweetSet = """ + $show(set4c ));$skip(27); 
  val set4d = set3.incl(d);System.out.println("""set4d  : objsets.TweetSet = """ + $show(set4d ));$skip(27); 
  val set5 = set4c.incl(d);System.out.println("""set5  : objsets.TweetSet = """ + $show(set5 ));$skip(35); val res$1 = 
 
  
  
  set5.descendingByRetweet;System.out.println("""res1: objsets.TweetList = """ + $show(res$1));$skip(152); 
 
 
 
  
  //set5 in descending is {(a, a body, 20), (b, b body, 20), (d, d body, 9), (c, c body, 7), (Empty)}
  
  
  val x = List("d body", "b body");System.out.println("""x  : List[String] = """ + $show(x ));$skip(112); 
  def byKeywords(keywords: List[String]) = (tweet: Tweet) => keywords.exists(term => tweet.text.contains(term));System.out.println("""byKeywords: (keywords: List[String])objsets.Tweet => Boolean""");$skip(29); val res$2 = 
  set5.filter(byKeywords(x));System.out.println("""res2: objsets.TweetSet = """ + $show(res$2))}
       
  
}
