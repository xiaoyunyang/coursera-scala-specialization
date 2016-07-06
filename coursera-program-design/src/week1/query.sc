package week1

/*** For-expression are useful for querying the database (Slick) ***/
case class Book(title: String, authors: List[String])

object query {

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
  )                                               //> booksSet  : Set[week1.Book] = Set(Book(Effective Java 2,List(Bloch, Joshua))
                                                  //| , Book(Structure and Interpretation of Computer Programs,List(Abelson, Haral
                                                  //| d, Sussman, Gerald J.)), Book(Effective Java,List(Bloch, Joshua)), Book(Intr
                                                  //| oduction to Functional Programming,List(Bird, Richard, Wadler, Phil)), Book(
                                                  //| Programming in Scala,List(Ordersky, Martin, Spoon, Lex, Venners, Bill)), Boo
                                                  //| k(Java Puzzlers,List(Bloch, Joshua, Gafter, Neal)))
  
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
  )                                               //> booksList  : List[week1.Book] = List(Book(Structure and Interpretation of C
                                                  //| omputer Programs,List(Abelson, Harald, Sussman, Gerald J.)), Book(Introduct
                                                  //| ion to Functional Programming,List(Bird, Richard, Wadler, Phil)), Book(Effe
                                                  //| ctive Java,List(Bloch, Joshua)), Book(Effective Java 2,List(Bloch, Joshua))
                                                  //| , Book(Java Puzzlers,List(Bloch, Joshua, Gafter, Neal)), Book(Programming i
                                                  //| n Scala,List(Ordersky, Martin, Spoon, Lex, Venners, Bill)))
  
  //To find the titles of books whose author's name is "Bird"
  for(b <- booksSet; a <- b.authors if a startsWith "Bloch") yield b.title
                                                  //> res0: scala.collection.immutable.Set[String] = Set(Effective Java 2, Effect
                                                  //| ive Java, Java Puzzlers)
  for(b <- booksList; a <- b.authors if a startsWith "Bloch") yield b.title
                                                  //> res1: List[String] = List(Effective Java, Effective Java 2, Java Puzzlers)
  
	//To find all the books which have the word "Program" in the title
	for(b <- booksSet if b.title.indexOf("Program") >= 0) yield b.title
                                                  //> res2: scala.collection.immutable.Set[String] = Set(Structure and Interpreta
                                                  //| tion of Computer Programs, Introduction to Functional Programming, Programm
                                                  //| ing in Scala)
	for(b <- booksList if b.title.indexOf("Program") >= 0) yield b.title
                                                  //> res3: List[String] = List(Structure and Interpretation of Computer Programs
                                                  //| , Introduction to Functional Programming, Programming in Scala)

	booksSet.map(b => b.title indexOf "Program")
                                                  //> res4: scala.collection.immutable.Set[Int] = Set(-1, 41, 27, 0)
	
	//Find the names of all authors who have written at least two books present in the database
	for {
		b1 <- booksSet
		b2 <- booksSet
		if b1.title < b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1                                //> res5: scala.collection.immutable.Set[String] = Set(Bloch, Joshua)
	
	for {
		b1 <- booksSet
		b2 <- booksSet
		if b1.title != b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1                                //> res6: scala.collection.immutable.Set[String] = Set(Bloch, Joshua)
	
	for {
		b1 <- booksList
		b2 <- booksList
		if b1.title < b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1                                //> res7: List[String] = List(Bloch, Joshua, Bloch, Joshua, Bloch, Joshua)
	
	for {
		b1 <- booksList
		b2 <- booksList
		if b1.title != b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1                                //> res8: List[String] = List(Bloch, Joshua, Bloch, Joshua, Bloch, Joshua, Bloc
                                                  //| h, Joshua, Bloch, Joshua, Bloch, Joshua)
  for {
		b1 <- booksList
		b2 <- booksSet
		if b1.title < b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1                                //> res9: List[String] = List(Bloch, Joshua, Bloch, Joshua)
	
	for {
		b1 <- booksSet
		b2 <- booksList
		if b1.title < b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1                                //> res10: scala.collection.immutable.Set[String] = Set(Bloch, Joshua)
              
  
  /** Translating for expression back into HOFs **/
  for(b <- booksSet; a <- b.authors if a startsWith "Bird") yield b.title
                                                  //> res11: scala.collection.immutable.Set[String] = Set(Introduction to Functio
                                                  //| nal Programming)


	//substitute the first generator by flatMap
	booksSet flatMap(b =>
  	for(a <- b.authors if a.startsWith("Bird")) yield b.title)
                                                  //> res12: scala.collection.immutable.Set[String] = Set(Introduction to Functio
                                                  //| nal Programming)
	//substitute the if-statement by withFilter
	booksSet flatMap(b =>
  	for(a <- b.authors withFilter (a => a.startsWith("Bird"))) yield b.title)
                                                  //> res13: scala.collection.immutable.Set[String] = Set(Introduction to Functio
                                                  //| nal Programming)
  	
  //substitute the second generator by map
  booksSet flatMap(b =>
  	b.authors withFilter (a => a.startsWith("Bird")) map (t => b.title))
                                                  //> res14: scala.collection.immutable.Set[String] = Set(Introduction to Functio
                                                  //| nal Programming)


}