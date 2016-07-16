package week6

object maps {
  val romanNumerals = Map('I' -> 1, 'V' -> 5, 'X' -> 10)
                                                  //> romanNumerals  : scala.collection.immutable.Map[Char,Int] = Map(I -> 1, V -> 
                                                  //| 5, X -> 10)
  val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern")
                                                  //> capitalOfCountry  : scala.collection.immutable.Map[String,String] = Map(US -
                                                  //| > Washington, Switzerland -> Bern)
	capitalOfCountry("US")                    //> res0: String = Washington
	//The following will cause NoSuchElementException
	//capitalOfCountry("Andorra")
  //Use get lifts the result up to Option, which returns either Some or None
  capitalOfCountry get "Andorra"                  //> res1: Option[String] = None
  capitalOfCountry get "US"                       //> res2: Option[String] = Some(Washington)


	def showCapitalWithOption(country: String) = capitalOfCountry.get(country) match {
		case Some(capital) => capital
		case None => "missing data"
	}                                         //> showCapitalWithOption: (country: String)String
	showCapitalWithOption("US")               //> res3: String = Washington
	showCapitalWithOption("Andorra")          //> res4: String = missing data

	//With default function essentially does the same thing
	val cap1 = capitalOfCountry withDefaultValue "<unknown>"
                                                  //> cap1  : scala.collection.immutable.Map[String,String] = Map(US -> Washington
                                                  //| , Switzerland -> Bern)
	cap1("Andorra")                           //> res5: String = <unknown>

	/** useful SQL queries */
	//groupBy and orderBy operations in SQL queries have analogues in Scala, namely sortWith and sorted.
	val fruit = List("apple", "pear", "orange", "pineapple")
                                                  //> fruit  : List[String] = List(apple, pear, orange, pineapple)
	fruit sortWith (_.length < _.length)      //> res6: List[String] = List(pear, apple, orange, pineapple)
	//lexicographical order
	fruit sorted                              //> res7: List[String] = List(apple, orange, pear, pineapple)

	//groupBy partitions a collection into a map of collections according to a discriminator function f
	fruit groupBy (_.head)                    //> res8: scala.collection.immutable.Map[Char,List[String]] = Map(p -> List(pea
                                                  //| r, pineapple), a -> List(apple), o -> List(orange))
  
  /** Polynomial */
  //polynomial can be seen as a map from exponents to coefficients
  Map(0 -> 5, 1 -> -2, 3 -> 1) //x^3 - 2x + 5     //> res9: scala.collection.immutable.Map[Int,Int] = Map(0 -> 5, 1 -> -2, 3 -> 1
                                                  //| )

	val p1 = new Poly(Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.2))
                                                  //> p1  : week6.Poly = 6.2x^5 + 4.0x^3 + 2.0x^1
	val p11 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
                                                  //> p11  : week6.Poly = 6.2x^5 + 4.0x^3 + 2.0x^1
 	val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))//> p2  : week6.Poly = 7.0x^3 + 3.0x^0
 	val p21 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
                                                  //> p21  : week6.Poly = 6.2x^5 + 4.0x^3 + 2.0x^1
 	p1 + p2                                   //> res10: week6.Poly = 6.2x^5 + 11.0x^3 + 2.0x^1 + 3.0x^0
 	
 	p1.terms(7)                               //> res11: Double = 0.0
}