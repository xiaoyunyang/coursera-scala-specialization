package week1

/*** For-expression are useful for querying the database (Slick) ***/
case class Book(title: String, authors: List[String])

object query {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(861); 

  //A mini-database - implemented as a Set - GOOD
  val booksSet: Set[Book] = Set (
  	Book(title 	 = "Structure and Interpretation of Computer Programs",
  		   authors = List("Abelson, Harald", "Sussman, Gerald J.")),
  	Book(title 	 = "Introduction to Functional Programming",
  		   authors = List("Bird, Richard", "Wadler, Phil")),
  	Book(title 	 = "Effective Java",
  		   authors = List("Bloch, Joshua")),
  	Book(title 	 = "Effective Java 2",
  		   authors = List("Bloch, Joshua")),
  	Book(title 	 = "Java Puzzlers",
  		   authors = List("Bloch, Joshua", "Gafter, Neal")),
   	Book(title 	 = "Programming in Scala",
  		   authors = List("Ordersky, Martin", "Spoon, Lex", "Venners, Bill"))
  );System.out.println("""booksSet  : Set[week1.Book] = """ + $show(booksSet ));$skip(712); 
  
  //A mini-database - implemented as a List - BAD
  val booksList: List[Book] = List (
  	Book(title 	 = "Structure and Interpretation of Computer Programs",
  		   authors = List("Abelson, Harald", "Sussman, Gerald J.")),
  	Book(title 	 = "Introduction to Functional Programming",
  		   authors = List("Bird, Richard", "Wadler, Phil")),
  	Book(title 	 = "Effective Java",
  		   authors = List("Bloch, Joshua")),
  	Book(title 	 = "Effective Java 2",
  		   authors = List("Bloch, Joshua")),
  	Book(title 	 = "Java Puzzlers",
  		   authors = List("Bloch, Joshua", "Gafter, Neal")),
   	Book(title 	 = "Programming in Scala",
  		   authors = List("Ordersky, Martin", "Spoon, Lex", "Venners, Bill"))
  );System.out.println("""booksList  : List[week1.Book] = """ + $show(booksList ));$skip(140); val res$0 = 
  
  //To find the titles of books whose author's name is "Bird"
  for(b <- booksSet; a <- b.authors if a startsWith "Bloch") yield b.title;System.out.println("""res0: scala.collection.immutable.Set[String] = """ + $show(res$0));$skip(76); val res$1 = 
  for(b <- booksList; a <- b.authors if a startsWith "Bloch") yield b.title;System.out.println("""res1: List[String] = """ + $show(res$1));$skip(140); val res$2 = 
  
	//To find all the books which have the word "Program" in the title
	for(b <- booksSet if b.title.indexOf("Program") >= 0) yield b.title;System.out.println("""res2: scala.collection.immutable.Set[String] = """ + $show(res$2));$skip(70); val res$3 = 
	for(b <- booksList if b.title.indexOf("Program") >= 0) yield b.title;System.out.println("""res3: List[String] = """ + $show(res$3));$skip(47); val res$4 = 

	booksSet.map(b => b.title indexOf "Program");System.out.println("""res4: scala.collection.immutable.Set[Int] = """ + $show(res$4));$skip(225); val res$5 = 
	
	//Find the names of all authors who have written at least two books present in the database
	for {
		b1 <- booksSet
		b2 <- booksSet
		if b1.title < b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1;System.out.println("""res5: scala.collection.immutable.Set[String] = """ + $show(res$5));$skip(133); val res$6 = 
	
	for {
		b1 <- booksSet
		b2 <- booksSet
		if b1.title != b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1;System.out.println("""res6: scala.collection.immutable.Set[String] = """ + $show(res$6));$skip(134); val res$7 = 
	
	for {
		b1 <- booksList
		b2 <- booksList
		if b1.title < b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1;System.out.println("""res7: List[String] = """ + $show(res$7));$skip(135); val res$8 = 
	
	for {
		b1 <- booksList
		b2 <- booksList
		if b1.title != b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1;System.out.println("""res8: List[String] = """ + $show(res$8));$skip(132); val res$9 = 
  for {
		b1 <- booksList
		b2 <- booksSet
		if b1.title < b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1;System.out.println("""res9: List[String] = """ + $show(res$9));$skip(133); val res$10 = 
	
	for {
		b1 <- booksSet
		b2 <- booksList
		if b1.title < b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1;System.out.println("""res10: scala.collection.immutable.Set[String] = """ + $show(res$10));$skip(144); val res$11 = 
              
  
  /** Translating for expression back into HOFs **/
  for(b <- booksSet; a <- b.authors if a startsWith "Bird") yield b.title;System.out.println("""res11: scala.collection.immutable.Set[String] = """ + $show(res$11));$skip(132); val res$12 = 


	//substitute the first generator by flatMap
	booksSet flatMap(b =>
  	for(a <- b.authors if a.startsWith("Bird")) yield b.title);System.out.println("""res12: scala.collection.immutable.Set[String] = """ + $show(res$12));$skip(145); val res$13 = 
	//substitute the if-statement by withFilter
	booksSet flatMap(b =>
  	for(a <- b.authors withFilter (a => a.startsWith("Bird"))) yield b.title);System.out.println("""res13: scala.collection.immutable.Set[String] = """ + $show(res$13));$skip(143); val res$14 = 
  	
  //substitute the second generator by map
  booksSet flatMap(b =>
  	b.authors withFilter (a => a.startsWith("Bird")) map (t => b.title));System.out.println("""res14: scala.collection.immutable.Set[String] = """ + $show(res$14))}


}
