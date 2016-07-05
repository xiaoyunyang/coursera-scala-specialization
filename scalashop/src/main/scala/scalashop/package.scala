
import common._

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {
    // TODO implement using two nested while loops

    var sumR = 0
    var sumG = 0
    var sumB = 0
    var sumA = 0
    var cnt = 0
    
    for(i <- clamp(x - radius, 0, src.width) until clamp(x+radius+1, 0, src.width)) {
      for(j <- clamp(y - radius, 0, src.height) until clamp(y+radius+1, 0, src.height)) {
        
        sumR = sumR + red(src.apply(i,j))  
        sumG = sumG + green(src.apply(i,j))  
        sumB = sumB + blue(src.apply(i,j))  
        sumA = sumA + alpha(src.apply(i,j))  
        cnt = cnt + 1
        //println(s"i = $i, j = $j, sum = $sum, cnt = $cnt")
      }
    }
    //println(s"sum=$sum, cnt=$cnt")
    rgba(sumR/cnt, sumG/cnt, sumB/cnt, sumA/cnt)
  }

}
