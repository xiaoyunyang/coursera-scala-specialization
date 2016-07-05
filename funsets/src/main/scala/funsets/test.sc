package funsets
import FunSets._
object test {
	val s1 = singletonSet(1)                  //> s1  : funsets.FunSets.Set = <function1>
  val s2 = singletonSet(2)                        //> s2  : funsets.FunSets.Set = <function1>
  val s3 = singletonSet(3)                        //> s3  : funsets.FunSets.Set = <function1>
  val s4 = singletonSet(-1)                       //> s4  : funsets.FunSets.Set = <function1>
  val s5 = singletonSet(-2)                       //> s5  : funsets.FunSets.Set = <function1>
  val s6 = singletonSet(-3)                       //> s6  : funsets.FunSets.Set = <function1>
  
  
  contains(s1,1)                                  //> res0: Boolean = true
  contains(s2,2)                                  //> res1: Boolean = true
  contains(s3,3)                                  //> res2: Boolean = true
  
  union(s1,s2)                                    //> res3: funsets.FunSets.Set = <function1>
  contains(union(s1,s2),1)                        //> res4: Boolean = true
  contains(union(s1,s2),2)                        //> res5: Boolean = true
  contains(union(s1,s3),3)                        //> res6: Boolean = true
  contains(union(s1,s2),3)                        //> res7: Boolean = false
  contains(s1,1)                                  //> res8: Boolean = true
             
  union(s1,s3)                                    //> res9: funsets.FunSets.Set = <function1>
  union(s4,s4)                                    //> res10: funsets.FunSets.Set = <function1>
 
}