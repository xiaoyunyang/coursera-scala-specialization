package scalashop
import scalashop._
object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(77); 

  val src = new Img(5, 5);System.out.println("""src  : scalashop.Img = """ + $show(src ));$skip(90); 

  for (x <- 0 until 5; y <- 0 until 5)
  	src(x, y) = rgba(x, y, x + y, math.abs(x - y));$skip(41); 
 	
 	val a = boxBlurKernel(src, 0, 0, 0);System.out.println("""a  : scalashop.RGBA = """ + $show(a ));$skip(38); 
 	val b = boxBlurKernel(src, 0, 1, 0);System.out.println("""b  : scalashop.RGBA = """ + $show(b ));$skip(38); 
 	val c = boxBlurKernel(src, 1, 0, 0);System.out.println("""c  : scalashop.RGBA = """ + $show(c ));$skip(12); val res$0 = 
 	(a+b+c)/3;System.out.println("""res0: Int = """ + $show(res$0));$skip(30); val res$1 = 
  boxBlurKernel(src, 0, 0, 1);System.out.println("""res1: scalashop.RGBA = """ + $show(res$1));$skip(12); 

	val w = 4;System.out.println("""w  : Int = """ + $show(w ));$skip(12); 
  val h = 3;System.out.println("""h  : Int = """ + $show(h ));$skip(27); 
  val src2 = new Img(w, h);System.out.println("""src2  : scalashop.Img = """ + $show(src2 ));$skip(27); 
  val dst2 = new Img(w, h);System.out.println("""dst2  : scalashop.Img = """ + $show(dst2 ));$skip(65); 
  src2(0, 0) = 0; src2(1, 0) = 1; src2(2, 0) = 2; src2(3, 0) = 9;$skip(66); 
  src2(0, 1) = 3; src2(1, 1) = 4; src2(2, 1) = 5; src2(3, 1) = 10;$skip(66); 
  src2(0, 2) = 6; src2(1, 2) = 7; src2(2, 2) = 8; src2(3, 2) = 11;$skip(18); val res$2 = 
  
  
  src2(0,1);System.out.println("""res2: scalashop.RGBA = """ + $show(res$2));$skip(47); 
  
  VerticalBoxBlur.blur(src2, dst2, 0, 4, 2);$skip(31); val res$3 = 
  boxBlurKernel(src2, 0, 0, 2);System.out.println("""res3: scalashop.RGBA = """ + $show(res$3));$skip(72); val res$4 = 
 
  // Equal 4 ... (3+6)/2 = 4
  dst2(0,0);System.out.println("""res4: scalashop.RGBA = """ + $show(res$4));$skip(41); val res$5 =  // (0+1+2+3+4+5+6+7+8)/9 = 4
  dst2(0,1);System.out.println("""res5: scalashop.RGBA = """ + $show(res$5));$skip(41); val res$6 =  // (0+1+2+3+4+5+6+7+8)/9 = 4
  dst2(0,2);System.out.println("""res6: scalashop.RGBA = """ + $show(res$6));$skip(56); val res$7 =  // (0+1+2+3+4+5+6+7+8)/9 = 4
  
  
  // Equal 5 ... (1+4+7)/2 = 12/2 = 5
  dst2(1,0);System.out.println("""res7: scalashop.RGBA = """ + $show(res$7));$skip(12); val res$8 = 
  dst2(1,1);System.out.println("""res8: scalashop.RGBA = """ + $show(res$8));$skip(12); val res$9 = 
  dst2(1,2);System.out.println("""res9: scalashop.RGBA = """ + $show(res$9));$skip(53); val res$10 = 
  
  // Equal 5 ... (2+5+8)/2 = 15/3 = 5
  dst2(2,0);System.out.println("""res10: scalashop.RGBA = """ + $show(res$10));$skip(12); val res$11 = 
  dst2(2,1);System.out.println("""res11: scalashop.RGBA = """ + $show(res$11));$skip(12); val res$12 = 
  dst2(2,2);System.out.println("""res12: scalashop.RGBA = """ + $show(res$12));$skip(55); val res$13 = 
  
  // Equal 6 ... (9+10+11)/3 = 29/3 = 9
  dst2(3,0);System.out.println("""res13: scalashop.RGBA = """ + $show(res$13));$skip(12); val res$14 = 
  dst2(3,1);System.out.println("""res14: scalashop.RGBA = """ + $show(res$14));$skip(12); val res$15 = 
  dst2(3,2);System.out.println("""res15: scalashop.RGBA = """ + $show(res$15));$skip(20); 

  val numTasks = 2;System.out.println("""numTasks  : Int = """ + $show(numTasks ));$skip(25); 
  val chunk = 4/numTasks;System.out.println("""chunk  : Int = """ + $show(chunk ));$skip(30); 
  val start = 0 to 4 by chunk;System.out.println("""start  : scala.collection.immutable.Range = """ + $show(start ));$skip(35); 
  val end = (0 to w by chunk).tail;System.out.println("""end  : scala.collection.immutable.Range = """ + $show(end ));$skip(17); val res$16 = 

  start zip end;System.out.println("""res16: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$16));$skip(36); 
  val start2 = 0 to 5 by 5/numTasks;System.out.println("""start2  : scala.collection.immutable.Range = """ + $show(start2 ));$skip(46); 
  val end2 = (0 to 5 by 5/numTasks).tail :+ 5;System.out.println("""end2  : scala.collection.immutable.IndexedSeq[Int] = """ + $show(end2 ));$skip(19); val res$17 = 
	
	start2 zip end2;System.out.println("""res17: scala.collection.immutable.IndexedSeq[(Int, Int)] = """ + $show(res$17))}
                
}
