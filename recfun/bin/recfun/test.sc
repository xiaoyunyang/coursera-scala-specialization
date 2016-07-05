package recfun

object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  def balance(chars: List[Char]): Boolean = chars match {
    case Nil => true
    case '(' :: t =>  hasMatch(t)._1 && balance(hasMatch(t)._2)
    case ')' :: t => false
    case h :: t => balance(t)
  }                                               //> balance: (chars: List[Char])Boolean
  def hasMatch(chars: List[Char]): (Boolean, List[Char]) = chars match {
	  case Nil => (false, Nil)
	  case ')' :: t => (true, t)
	  case '(' :: t => hasMatch(t)
	  case h :: t => hasMatch(t)
	}                                         //> hasMatch: (chars: List[Char])(Boolean, List[Char])
	
	
	balance("()".toList) //true               //> res0: Boolean = true
	balance("()()".toList) //true             //> res1: Boolean = true
	balance("(())()".toList) //true           //> res2: Boolean = false
	balance("(()".toList)                     //> res3: Boolean = true
	balance("(()()".toList)                   //> res4: Boolean = true
	balance("((".toList)                      //> res5: Boolean = false
	balance("(()".toList)                     //> res6: Boolean = true
	balance(")".toList)                       //> res7: Boolean = false
	balance(")(".toList)                      //> res8: Boolean = false
}