package funsets
import FunSets._
object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(72); 
	val s1 = singletonSet(1);System.out.println("""s1  : funsets.FunSets.Set = """ + $show(s1 ));$skip(27); 
  val s2 = singletonSet(2);System.out.println("""s2  : funsets.FunSets.Set = """ + $show(s2 ));$skip(27); 
  val s3 = singletonSet(3);System.out.println("""s3  : funsets.FunSets.Set = """ + $show(s3 ));$skip(28); 
  val s4 = singletonSet(-1);System.out.println("""s4  : funsets.FunSets.Set = """ + $show(s4 ));$skip(28); 
  val s5 = singletonSet(-2);System.out.println("""s5  : funsets.FunSets.Set = """ + $show(s5 ));$skip(28); 
  val s6 = singletonSet(-3);System.out.println("""s6  : funsets.FunSets.Set = """ + $show(s6 ));$skip(23); val res$0 = 
  
  
  contains(s1,1);System.out.println("""res0: Boolean = """ + $show(res$0));$skip(17); val res$1 = 
  contains(s2,2);System.out.println("""res1: Boolean = """ + $show(res$1));$skip(17); val res$2 = 
  contains(s3,3);System.out.println("""res2: Boolean = """ + $show(res$2));$skip(18); val res$3 = 
  
  union(s1,s2);System.out.println("""res3: funsets.FunSets.Set = """ + $show(res$3));$skip(27); val res$4 = 
  contains(union(s1,s2),1);System.out.println("""res4: Boolean = """ + $show(res$4));$skip(27); val res$5 = 
  contains(union(s1,s2),2);System.out.println("""res5: Boolean = """ + $show(res$5));$skip(27); val res$6 = 
  contains(union(s1,s3),3);System.out.println("""res6: Boolean = """ + $show(res$6));$skip(27); val res$7 = 
  contains(union(s1,s2),3);System.out.println("""res7: Boolean = """ + $show(res$7));$skip(17); val res$8 = 
  contains(s1,1);System.out.println("""res8: Boolean = """ + $show(res$8));$skip(29); val res$9 = 
             
  union(s1,s3);System.out.println("""res9: funsets.FunSets.Set = """ + $show(res$9));$skip(15); val res$10 = 
  union(s4,s4);System.out.println("""res10: funsets.FunSets.Set = """ + $show(res$10))}
 
}
