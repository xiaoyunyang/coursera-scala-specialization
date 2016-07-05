package recfun

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  import Main.balance

  /* 
   * These Tests should return true 
   */
  test("balance: empty String") {
    assert(balance("".toList)==true)
  }
  test("balance: simple true -  '()'") {
    assert(balance("()".toList)==true)
  }
  test("balance: '()()' is balanced") {
    assert(balance("()".toList)==true)
  }
  test("balance: '(a(b)c)(d)' is balanced") {
    assert(balance("(a(b)c)(d)".toList)==true)
  }
  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him (that it is not (yet) done). (But he wasn't listening)' is balanced") {
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }
  
  /* 
   * These Tests should return false
   */
  test("balance: simple false - '(' is unbalanced") {
    assert(balance("(".toList)==false)
  }
  test("balance: simple false - ')' is unbalanced") {
    assert(balance(")".toList)==false)
  }
  test("balance: simple false - ')(' is unbalanced") {
    assert(balance(")(".toList)==false)
  }
  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }
  test("balance: '())(' is unbalanced - counting is not enough") {
    assert(!balance("())(".toList))
  }

  test("balance: '((()' is unbalanced") {
    assert(!balance("((()".toList))
  }

}
