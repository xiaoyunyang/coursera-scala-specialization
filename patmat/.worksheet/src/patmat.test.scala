package patmat
import Huffman._
object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(104); 

  val chars = List('a', 'a', 'b', 'a', 'a', 'c', 'b','4');System.out.println("""chars  : List[Char] = """ + $show(chars ));$skip(15); val res$0 = 
 	times(chars);System.out.println("""res0: List[(Char, Int)] = """ + $show(res$0));$skip(51); 
  
  val m1 = times(string2Chars("Massachusetts"));System.out.println("""m1  : List[(Char, Int)] = """ + $show(m1 ));$skip(49); 
  
  val m2 = times(string2Chars("Mississippi"));System.out.println("""m2  : List[(Char, Int)] = """ + $show(m2 ));$skip(44); 
  val m3 = times(string2Chars("Mississpp"));System.out.println("""m3  : List[(Char, Int)] = """ + $show(m3 ));$skip(93); 
                                                  
  val orderedM1 = makeOrderedLeafList(m1);System.out.println("""orderedM1  : List[patmat.Huffman.Leaf] = """ + $show(orderedM1 ));$skip(42); 
  val orderedM2 = makeOrderedLeafList(m2);System.out.println("""orderedM2  : List[patmat.Huffman.Leaf] = """ + $show(orderedM2 ));$skip(42); 
  val orderedM3 = makeOrderedLeafList(m3);System.out.println("""orderedM3  : List[patmat.Huffman.Leaf] = """ + $show(orderedM3 ));$skip(60); val res$1 = 
  singleton(makeOrderedLeafList(times(string2Chars("ab"))));System.out.println("""res1: Boolean = """ + $show(res$1));$skip(59); val res$2 = 
  singleton(makeOrderedLeafList(times(string2Chars("a"))));System.out.println("""res2: Boolean = """ + $show(res$2));$skip(61); val res$3 = 
  singleton(makeOrderedLeafList(times(string2Chars("aaa"))));System.out.println("""res3: Boolean = """ + $show(res$3));$skip(72); val res$4 = 
                                                  
  combine(orderedM1);System.out.println("""res4: List[patmat.Huffman.CodeTree] = """ + $show(res$4));$skip(21); val res$5 = 
  combine(orderedM2);System.out.println("""res5: List[patmat.Huffman.CodeTree] = """ + $show(res$5));$skip(21); val res$6 = 
  combine(orderedM3);System.out.println("""res6: List[patmat.Huffman.CodeTree] = """ + $show(res$6));$skip(67); val res$7 = 
   
    
    
    
  createCodeTree(string2Chars("Massachusetts"));System.out.println("""res7: patmat.Huffman.CodeTree = """ + $show(res$7));$skip(29); val res$8 = 
  decode(frenchCode, secret);System.out.println("""res8: List[Char] = """ + $show(res$8));$skip(49); val res$9 = 
  encode(frenchCode)(decode(frenchCode, secret));System.out.println("""res9: List[patmat.Huffman.Bit] = """ + $show(res$9));$skip(9); val res$10 = 
  secret;System.out.println("""res10: List[patmat.Huffman.Bit] = """ + $show(res$10))}
           
      
}
