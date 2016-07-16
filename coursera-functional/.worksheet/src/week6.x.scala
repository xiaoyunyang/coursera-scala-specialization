package week6

import scala.io.Source

object x {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(160); 
	val in = Source.fromURL("http://lamp.epfl.ch/files/content/sites/lamp/files/teaching/progfun/linuxwords.txt");System.out.println("""in  : scala.io.BufferedSource = """ + $show(in ));$skip(252); 
	//in.getLines is an iterator so we need to convert it to a Sequence
	//some words contain a hyphen and our program can't deal with that. So we'll drop all the hyphen
	val words = in.getLines.toStream filter (word => word forall (chr => chr.isLetter));System.out.println("""words  : scala.collection.immutable.Stream[String] = """ + $show(words ));$skip(152); 
	
	//mnemonics
	val mnem = Map(
		'2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
		'6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ"
	);System.out.println("""mnem  : scala.collection.immutable.Map[Char,String] = """ + $show(mnem ));$skip(244); 
  /** Invert the mnem map to give a map from 'A' ... 'Z' tp '2' ... '9' */
  var charCode: Map[Char, Char] = {
  	//want to invert that map to go from characters to digit
  	for {
  		(digit, str) <- mnem
  		ltr <- str
  	} yield ltr -> digit
  };System.out.println("""charCode  : Map[Char,Char] = """ + $show(charCode ));$skip(156); 
	
	/** Maps a word to the digit string it can prepresent, e.g., "Java" -> "5282" */
	def wordCode(word: String): String = word.toUpperCase map charCode;System.out.println("""wordCode: (word: String)String""");$skip(18); val res$0 = 
	wordCode("JAVA");System.out.println("""res0: String = """ + $show(res$0));$skip(18); val res$1 = 
	wordCode("Java");System.out.println("""res1: String = """ + $show(res$1));$skip(303); 
	
	
	/**
	 * A map from digit strings to the words that represent them,
	 * e.g., "5282 -> List("Java", "Kata", "Lava", ...)
	 * Note: A missing number should map to the empty set, e.g., "111" -> "5282"
	 */
	val wordsForNum: Map[String, Seq[String]] = {
		words groupBy wordCode withDefaultValue Seq()
  };System.out.println("""wordsForNum  : Map[String,Seq[String]] = """ + $show(wordsForNum ));$skip(329); 
	
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
	};System.out.println("""encode: (number: String)Set[List[String]]""");$skip(23); val res$2 = 

	encode("7225247386");System.out.println("""res2: Set[List[String]] = """ + $show(res$2));$skip(92); 
  def translate(number: String): Set[String] = {
  	encode(number) map (_ mkString " ")
  };System.out.println("""translate: (number: String)Set[String]""");$skip(29); val res$3 = 
  
  translate("7225247386");System.out.println("""res3: Set[String] = """ + $show(res$3))}
}
