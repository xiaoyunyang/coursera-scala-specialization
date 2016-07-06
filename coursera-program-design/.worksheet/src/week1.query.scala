package week1

object query {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(604); 
  //A mini-database
  val books: List[Book] = List (
  	Book(title = "Structure and Interpretation of Computer Programs",
  		   authors = List("Abelson, Harald", "Sussman, Gerald J.")),
  	Book(title = "Introduction to Functional Programming",
  		   authors = List("Bird, Richard", "Wadler, Phil")),
  	Book(title = "Effective Java",
  		   authors = List("Bloch, Joshua")),
  	Book(title = "Java Puzzlers",
  		   authors = List("Bloch, Joshua", "Gafter, Neal")),
   	Book(title = "Programming in Scala",
  		   authors = List("Ordersky, Joshua"))
  		   
   		   
  
  );System.out.println("""books  : List[week1.Book] = """ + $show(books ))}
}

case class Book(title: String, authors: List[String])
