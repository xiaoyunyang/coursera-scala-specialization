package recfun

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(73); 
  println("Welcome to the Scala worksheet");$skip(204); 
  def balance(chars: List[Char]): Boolean = chars match {
    case Nil => true
    case '(' :: t =>  hasMatch(t)._1 && balance(hasMatch(t)._2)
    case ')' :: t => false
    case h :: t => balance(t)
  };System.out.println("""balance: (chars: List[Char])Boolean""");$skip(196); 
  def hasMatch(chars: List[Char]): (Boolean, List[Char]) = chars match {
	  case Nil => (false, Nil)
	  case ')' :: t => (true, t)
	  case '(' :: t => hasMatch(t)
	  case h :: t => hasMatch(t)
	};System.out.println("""hasMatch: (chars: List[Char])(Boolean, List[Char])""");$skip(33); val res$0 = 
	
	
	balance("()".toList);System.out.println("""res0: Boolean = """ + $show(res$0));$skip(31); val res$1 =  //true
	balance("()()".toList);System.out.println("""res1: Boolean = """ + $show(res$1));$skip(33); val res$2 =  //true
	balance("(())()".toList);System.out.println("""res2: Boolean = """ + $show(res$2));$skip(23); val res$3 =  //true
	balance("(()".toList);System.out.println("""res3: Boolean = """ + $show(res$3));$skip(25); val res$4 = 
	balance("(()()".toList);System.out.println("""res4: Boolean = """ + $show(res$4));$skip(22); val res$5 = 
	balance("((".toList);System.out.println("""res5: Boolean = """ + $show(res$5));$skip(23); val res$6 = 
	balance("(()".toList);System.out.println("""res6: Boolean = """ + $show(res$6));$skip(21); val res$7 = 
	balance(")".toList);System.out.println("""res7: Boolean = """ + $show(res$7));$skip(22); val res$8 = 
	balance(")(".toList);System.out.println("""res8: Boolean = """ + $show(res$8))}
}
