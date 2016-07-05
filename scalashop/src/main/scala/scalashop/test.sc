package scalashop
import scalashop._
object test {

  val src = new Img(5, 5)                         //> src  : scalashop.Img = scalashop.package$Img@cac736f

  for (x <- 0 until 5; y <- 0 until 5)
  	src(x, y) = rgba(x, y, x + y, math.abs(x - y))
 	
 	val a = boxBlurKernel(src, 0, 0, 0)       //> a  : scalashop.RGBA = 0
 	val b = boxBlurKernel(src, 0, 1, 0)       //> b  : scalashop.RGBA = 65793
 	val c = boxBlurKernel(src, 1, 0, 0)       //> c  : scalashop.RGBA = 16777473
 	(a+b+c)/3                                 //> res0: Int = 5614422
  boxBlurKernel(src, 0, 0, 1)                     //> res1: scalashop.RGBA = 8421632

	val w = 4                                 //> w  : Int = 4
  val h = 3                                       //> h  : Int = 3
  val src2 = new Img(w, h)                        //> src2  : scalashop.Img = scalashop.package$Img@62043840
  val dst2 = new Img(w, h)                        //> dst2  : scalashop.Img = scalashop.package$Img@5315b42e
  src2(0, 0) = 0; src2(1, 0) = 1; src2(2, 0) = 2; src2(3, 0) = 9
  src2(0, 1) = 3; src2(1, 1) = 4; src2(2, 1) = 5; src2(3, 1) = 10
  src2(0, 2) = 6; src2(1, 2) = 7; src2(2, 2) = 8; src2(3, 2) = 11
  
  
  src2(0,1)                                       //> res2: scalashop.RGBA = 3
  
  VerticalBoxBlur.blur(src2, dst2, 0, 4, 2)
  boxBlurKernel(src2, 0, 0, 2)                    //> res3: scalashop.RGBA = 4
 
  // Equal 4 ... (3+6)/2 = 4
  dst2(0,0) // (0+1+2+3+4+5+6+7+8)/9 = 4          //> res4: scalashop.RGBA = 4
  dst2(0,1) // (0+1+2+3+4+5+6+7+8)/9 = 4          //> res5: scalashop.RGBA = 4
  dst2(0,2) // (0+1+2+3+4+5+6+7+8)/9 = 4          //> res6: scalashop.RGBA = 4
  
  
  // Equal 5 ... (1+4+7)/2 = 12/2 = 5
  dst2(1,0)                                       //> res7: scalashop.RGBA = 5
  dst2(1,1)                                       //> res8: scalashop.RGBA = 5
  dst2(1,2)                                       //> res9: scalashop.RGBA = 5
  
  // Equal 5 ... (2+5+8)/2 = 15/3 = 5
  dst2(2,0)                                       //> res10: scalashop.RGBA = 5
  dst2(2,1)                                       //> res11: scalashop.RGBA = 5
  dst2(2,2)                                       //> res12: scalashop.RGBA = 5
  
  // Equal 6 ... (9+10+11)/3 = 29/3 = 9
  dst2(3,0)                                       //> res13: scalashop.RGBA = 6
  dst2(3,1)                                       //> res14: scalashop.RGBA = 6
  dst2(3,2)                                       //> res15: scalashop.RGBA = 6

  val numTasks = 2                                //> numTasks  : Int = 2
  val chunk = 4/numTasks                          //> chunk  : Int = 2
  val start = 0 to 4 by chunk                     //> start  : scala.collection.immutable.Range = Range(0, 2, 4)
  val end = (0 to w by chunk).tail                //> end  : scala.collection.immutable.Range = Range(2, 4)

  start zip end                                   //> res16: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((0,2), (2
                                                  //| ,4))
  val start2 = 0 to 5 by 5/numTasks               //> start2  : scala.collection.immutable.Range = Range(0, 2, 4)
  val end2 = (0 to 5 by 5/numTasks).tail :+ 5     //> end2  : scala.collection.immutable.IndexedSeq[Int] = Vector(2, 4, 5)
	
	start2 zip end2                           //> res17: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((0,2), (2
                                                  //| ,4), (4,5))
                
}