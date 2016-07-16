package week6

import scala.io.Source

object x {
	val in = Source.fromURL("http://lamp.epfl.ch/files/content/sites/lamp/files/teaching/progfun/linuxwords.txt")
                                                  //> in  : scala.io.BufferedSource = non-empty iterator
	//in.getLines is an iterator so we need to convert it to a Sequence
	//some words contain a hyphen and our program can't deal with that. So we'll drop all the hyphen
	val words = in.getLines.toStream filter (word => word forall (chr => chr.isLetter))
                                                  //> words  : scala.collection.immutable.Stream[String] = Stream(Aarhus, ?)
	
	//mnemonics
	val mnem = Map(
		'2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
		'6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ"
	)                                         //> mnem  : scala.collection.immutable.Map[Char,String] = Map(8 -> TUV, 4 -> GHI
                                                  //| , 9 -> WXYZ, 5 -> JKL, 6 -> MNO, 2 -> ABC, 7 -> PQRS, 3 -> DEF)
  /** Invert the mnem map to give a map from 'A' ... 'Z' tp '2' ... '9' */
  var charCode: Map[Char, Char] = {
  	//want to invert that map to go from characters to digit
  	for {
  		(digit, str) <- mnem
  		ltr <- str
  	} yield ltr -> digit                      //> charCode  : Map[Char,Char] = Map(E -> 3, X -> 9, N -> 6, T -> 8, Y -> 9, J -
                                                  //| > 5, U -> 8, F -> 3, A -> 2, M -> 6, I -> 4, G -> 4, V -> 8, Q -> 7, L -> 5,
                                                  //|  B -> 2, P -> 7, C -> 2, H -> 4, W -> 9, K -> 5, R -> 7, O -> 6, D -> 3, Z -
                                                  //| > 9, S -> 7)
  }
	
	/** Maps a word to the digit string it can prepresent, e.g., "Java" -> "5282" */
	def wordCode(word: String): String = word.toUpperCase map charCode
                                                  //> wordCode: (word: String)String
	wordCode("JAVA")                          //> res0: String = 5282
	wordCode("Java")                          //> res1: String = 5282
	
	
	/**
	 * A map from digit strings to the words that represent them,
	 * e.g., "5282 -> List("Java", "Kata", "Lava", ...)
	 * Note: A missing number should map to the empty set, e.g., "111" -> "5282"
	 */
	val wordsForNum: Map[String, Seq[String]] = {
		words groupBy wordCode withDefaultValue Seq()
                                                  //> wordsForNum  : Map[String,Seq[String]] = Map(63972278 -> Stream(newscast, ?
                                                  //| ), 29237638427 -> Stream(cybernetics, ?), 782754448 -> Stream(starlight, ?)
                                                  //| , 2559464 -> Stream(allying, ?), 862532733 -> Stream(uncleared, ?), 3656922
                                                  //| 59 -> Stream(enjoyably, ?), 868437 -> Stream(unties, ?), 33767833 -> Stream
                                                  //| (deportee, ?), 742533 -> Stream(picked, ?), 3364646489 -> Stream(femininity
                                                  //| , ?), 3987267346279 -> Stream(extraordinary, ?), 7855397 -> Stream(pulleys,
                                                  //|  ?), 67846493 -> Stream(optimize, ?), 4723837 -> Stream(grafter, ?), 386583
                                                  //|  -> Stream(evolve, ?), 78475464 -> Stream(Stirling, ?), 746459 -> Stream(si
                                                  //| ngly, ?), 847827 -> Stream(vistas, ?), 546637737 -> Stream(lionesses, ?), 2
                                                  //| 8754283 -> Stream(curlicue, ?), 84863372658 -> Stream(thunderbolt, ?), 4676
                                                  //| 7833 -> Stream(imported, ?), 26437464 -> Stream(angering, ?), 8872267 -> St
                                                  //| ream(turbans, ?), 77665377 -> Stream(spoolers, ?), 46636233 -> Stream(homem
                                                  //| ade, ?), 7446768759 -> 
                                                  //| Output exceeds cutoff limit.
  }
	
	//e.g., phone number "7225247386" should translate to Scala
	def encode(number: String): Set[List[String]] = {
		if(number.isEmpty) Set(List())
		else {
			for {
				split <- 1 to number.length //a range
				word <- wordsForNum(number take split)
				rest <- encode(number drop split)
			} yield word :: rest
		}.toSet
	}                                         //> encode: (number: String)Set[List[String]]

	encode("7225247386")                      //> res2: Set[List[String]] = Set(List(rack, ah, re, to), List(sack, ah, re, to
                                                  //| ), List(Scala, ire, to), List(sack, air, fun), List(rack, air, fun), List(r
                                                  //| ack, bird, to), List(pack, air, fun), List(pack, ah, re, to), List(pack, bi
                                                  //| rd, to), List(Scala, is, fun), List(sack, bird, to))
  def translate(number: String): Set[String] = {
  	encode(number) map (_ mkString " ")
  }                                               //> translate: (number: String)Set[String]
  
  translate("7225247386")                         //> res3: Set[String] = Set(sack air fun, pack ah re to, pack bird to, Scala ir
                                                  //| e to, Scala is fun, rack ah re to, pack air fun, sack bird to, rack bird to
                                                  //| , sack ah re to, rack air fun)
}