package week2

//Don't have to import Rational._ because it's in the same package
object datastruct {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(130); 
  val x = new Rational(1, 3);System.out.println("""x  : <error> = """ + $show(x ));$skip(29); 
  val y = new Rational(5, 7);System.out.println("""y  : <error> = """ + $show(y ));$skip(29); 
  val z = new Rational(3, 2);System.out.println("""z  : <error> = """ + $show(z ));$skip(65); 
  
  //val strange = new Rational(1,0)
  val a = new Rational(2);System.out.println("""a  : <error> = """ + $show(a ));$skip(29); 
  val b = new Rational(2, 4);System.out.println("""b  : <error> = """ + $show(b ));$skip(10); val res$0 = 
  x.numer;System.out.println("""res0: <error> = """ + $show(res$0));$skip(10); val res$1 = 
  x.denom;System.out.println("""res1: <error> = """ + $show(res$1));$skip(14); val res$2 = 
  
  x.add(y);System.out.println("""res2: <error> = """ + $show(res$2));$skip(11); val res$3 = 
  y.add(x);System.out.println("""res3: <error> = """ + $show(res$3));$skip(11); val res$4 = 
  
  x.neg;System.out.println("""res4: <error> = """ + $show(res$4));$skip(11); val res$5 = 
  x.sub(y);System.out.println("""res5: <error> = """ + $show(res$5));$skip(18); val res$6 = 
  x.sub(y).sub(z);System.out.println("""res6: <error> = """ + $show(res$6));$skip(11); val res$7 = 
  y.add(y);System.out.println("""res7: <error> = """ + $show(res$7));$skip(14); val res$8 = 
 
  x.less(y);System.out.println("""res8: <error> = """ + $show(res$8));$skip(11); val res$9 = 
  x.max(y);System.out.println("""res9: <error> = """ + $show(res$9));$skip(124); val res$10 = 
  
  /**  Scala allows infix operators and symbolic identifiers **/
  x < y;System.out.println("""res10: <error> = """ + $show(res$10));$skip(32); val res$11 =  //writing less than using a symbolic identifier
  x max2 y;System.out.println("""res11: <error> = """ + $show(res$11));$skip(12); val res$12 =  //writing max2 infix
  x - y - z;System.out.println("""res12: <error> = """ + $show(res$12));$skip(8); val res$13 = 
  y + x;System.out.println("""res13: <error> = """ + $show(res$13));$skip(13); 
  val t = -x;System.out.println("""t  : <error> = """ + $show(t ));$skip(16); 
  val s = x.neg;System.out.println("""s  : <error> = """ + $show(s ));$skip(10); val res$14 = 
  x.numer;System.out.println("""res14: <error> = """ + $show(res$14));$skip(10); val res$15 = 
  x.denom;System.out.println("""res15: <error> = """ + $show(res$15));$skip(10); val res$16 = 
  t.numer;System.out.println("""res16: <error> = """ + $show(res$16));$skip(10); val res$17 = 
  t.denom;System.out.println("""res17: <error> = """ + $show(res$17));$skip(10); val res$18 = 
  s.numer;System.out.println("""res18: <error> = """ + $show(res$18));$skip(10); val res$19 = 
  s.denom;System.out.println("""res19: <error> = """ + $show(res$19));$skip(68); val res$20 = 
  
  /** Substitution of Scala classes */
  new Rational(1,2).numer;System.out.println("""res20: <error> = """ + $show(res$20));$skip(156); val res$21 = 
  /* Substitutions:
  		(1) First, 1 replaces x, 2 replaces y
  		(2) then new Rational(1,2) replaces this
  */
  new Rational(1,2).less(new Rational(2,3));System.out.println("""res21: <error> = """ + $show(res$21))}
  /* Substitutions:
  		(1) First, 1 replaces x, 2 abd replaces y
  		(2) Then new Rational(2,3) replaces that
  		(3) Then new Rational(1,2) replaces this
  */
  
}
