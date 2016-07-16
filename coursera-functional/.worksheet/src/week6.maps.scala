package week6

object maps {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(85); 
  val romanNumerals = Map('I' -> 1, 'V' -> 5, 'X' -> 10);System.out.println("""romanNumerals  : scala.collection.immutable.Map[Char,Int] = """ + $show(romanNumerals ));$skip(76); 
  val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern");System.out.println("""capitalOfCountry  : scala.collection.immutable.Map[String,String] = """ + $show(capitalOfCountry ));$skip(24); val res$0 = 
	capitalOfCountry("US");System.out.println("""res0: String = """ + $show(res$0));$skip(192); val res$1 = 
	//The following will cause NoSuchElementException
	//capitalOfCountry("Andorra")
  //Use get lifts the result up to Option, which returns either Some or None
  capitalOfCountry get "Andorra";System.out.println("""res1: Option[String] = """ + $show(res$1));$skip(28); val res$2 = 
  capitalOfCountry get "US";System.out.println("""res2: Option[String] = """ + $show(res$2));$skip(151); 


	def showCapitalWithOption(country: String) = capitalOfCountry.get(country) match {
		case Some(capital) => capital
		case None => "missing data"
	};System.out.println("""showCapitalWithOption: (country: String)String""");$skip(29); val res$3 = 
	showCapitalWithOption("US");System.out.println("""res3: String = """ + $show(res$3));$skip(34); val res$4 = 
	showCapitalWithOption("Andorra");System.out.println("""res4: String = """ + $show(res$4));$skip(116); 

	//With default function essentially does the same thing
	val cap1 = capitalOfCountry withDefaultValue "<unknown>";System.out.println("""cap1  : scala.collection.immutable.Map[String,String] = """ + $show(cap1 ));$skip(17); val res$5 = 
	cap1("Andorra");System.out.println("""res5: String = """ + $show(res$5));$skip(188); 

	/** useful SQL queries */
	//groupBy and orderBy operations in SQL queries have analogues in Scala, namely sortWith and sorted.
	val fruit = List("apple", "pear", "orange", "pineapple");System.out.println("""fruit  : List[String] = """ + $show(fruit ));$skip(38); val res$6 = 
	fruit sortWith (_.length < _.length);System.out.println("""res6: List[String] = """ + $show(res$6));$skip(39); val res$7 = 
	//lexicographical order
	fruit sorted;System.out.println("""res7: List[String] = """ + $show(res$7));$skip(126); val res$8 = 

	//groupBy partitions a collection into a map of collections according to a discriminator function f
	fruit groupBy (_.head);System.out.println("""res8: scala.collection.immutable.Map[Char,List[String]] = """ + $show(res$8));$skip(136); val res$9 = 
  
  /** Polynomial */
  //polynomial can be seen as a map from exponents to coefficients
  Map(0 -> 5, 1 -> -2, 3 -> 1);System.out.println("""res9: scala.collection.immutable.Map[Int,Int] = """ + $show(res$9));$skip(55);  //x^3 - 2x + 5

	val p1 = new Poly(Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.2));System.out.println("""p1  : week6.Poly = """ + $show(p1 ));$skip(50); 
	val p11 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2);System.out.println("""p11  : week6.Poly = """ + $show(p11 ));$skip(45); 
 	val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0));System.out.println("""p2  : week6.Poly = """ + $show(p2 ));$skip(51); 
 	val p21 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2);System.out.println("""p21  : week6.Poly = """ + $show(p21 ));$skip(10); val res$10 = 
 	p1 + p2;System.out.println("""res10: week6.Poly = """ + $show(res$10));$skip(17); val res$11 = 
 	
 	p1.terms(7);System.out.println("""res11: Double = """ + $show(res$11))}
}
