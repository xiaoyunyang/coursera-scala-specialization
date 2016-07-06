# coursera-scala-specialization

## Functional Programming Principles in Scala
Instructor: **Martin Odersky**
#### Topics
[Week 1](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1) -
* Evaluation strategy and recursion: [fixedpoint.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1/fixedpoint.sc) demonstrates writing a basic function in scala.
 [eval_strategy.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1/eval_strategy.sc) illustrates call-by-vale (imperative, C) versus call-by-name (lazy evaluation).

 ```scala
  def constOne(x: Int, y: => Int) = 1   //y has a type '=> Int' which means y is call-by-name
  constOne(1+2, loop) //reduces to 1
  constOne(loop, 1+2) //infinite cycle
 ```

[Week 2](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week2) -
* higher order functions - basically functions that has functions as arguments. See [HOF.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week2/HOF.sc) for some examples.

  ```scala
   //without curry
   def fun(z: Int, x: Int): Int = z + x
   List(1,2,3) map (x => fun(5, x))          
   List(1,2,3) map (fun(5, _))               

   //with curry
   def fun2(z: Int)(x: Int): Int = z + x  
   List(1,2,3) map fun2(5)                
  ```
* Scala class and datastructure in scala example: [Rational.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week2/Rational.scala), which gets called in [datastructure.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week2/datastruct.sc)

[Week 3](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3) -
* class hierarchy and organization. See example: [List.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3/List.scala) and [IntSet.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3/IntSet.scala), which is called by [class_hierarchy.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3/class_hierarchy.sc).
* Scala traits and abstract class. See [classes_org.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3/classes_org.sc) for examples of extending traits as well as when you use Nothing and AnyVal.

![class hierarchy](https://github.com/mbonaci/scala/blob/master/resources/Scala-class-hierarchy.gif?raw=true)

~ image from http://mbonaci.github.io/scala/

[Week 4](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4) -
* Subtyping and typing rules for functions: see  [subtyping.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/subtyping.sc). Functions are contravariant in their argument types and covariant in their result type. For example, the scala Function1 trait:
 ```scala
 /* Variance
  * the '-' and '+' in front of the type A and R denotes the
  * variance. '+R' and '-A' means A <: R or 'A is a subtype of R' or * 'R is a supertype of A'
  */
 trait Function1[-A, +R] {
   def apply(x: A): R
 }
 ```
* polymorphism, illustrated in [MyList.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/MyList.scala) and the scala worksheet. [collectionlist.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/collectionlist.sc). As another example, [Boolean2.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Boolean2.scala) implements a boolean using an abstract class and two companion objects. In the object oriented approach with a trait and class implementations, we can make a singleton for things that only has one implementation. See the Scala implementation of the Peano number in [Nat.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Nat.scala).
* Pattern Matching, or a fancy if statements (a generalization of switch from C/Java to class hierarchies) is a good solution for Functional Decomposition. As the purpose of decomposition is to reverse the construction process, pattern matching offers a great way to automate this deconstruction process. The comparison of pattern matching decomposition approach (using scala trait and case class) with the object oriented approach (trait and class implementations) is highlighted in  [Expr.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Expr.scala), [Expr2.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Expr2.scala), and [Expr3.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4/Expr3.scala).

#### Programming Assignments
1. [Recursive Functions](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/recfun/src/main/scala/recfun/Main.scala) - demonstrates recursion
2. [Functional Sets](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/funsets/src/main/scala/funsets/FunSets.scala) - demonstrates Higher Order Functions
3. [Object Oriented Sets](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/objsets/src/main/scala/objsets/TweetSet.scala) - demonstrates data and abstraction
    * [My Test](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/objsets/src/test/scala/objsets/test.sc) - scala worksheet output
4. [Huffman Encoding](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/patmat/src/main/scala/patmat/Huffman.scala) - demonstrates Pattern Matching and the scala List Collection
  * [My Test](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/patmat/src/test/scala/patmat/test.sc) - scala worksheet output
5. Anagrams - demonstrates Collections

## Functional Program Design in Scala
Instructor: **Martin Odersky**

#### Topics
[Week 1]()
*  Partial functions
 ```scala
 val f1: PartialFunction[String, String] = { case "ping" => "pong" }

 //result: List(pong, 404, 404)
 List("ping", "abc", "pong").map(a =>
    if(f1.isDefinedAt(a)) f1(a) else "404"
 )
 ```

#### Programming Assignments
1. Bloxorz - demonstrates lazy evaluation
2. Quickcheck - demonstrates functions and state
3. Calculator - demonstrates timely effects

## Parallel Programming
Instructors: **Viktor Kuncak and Aleksander Prokopec**

#### Topics
[Week 1](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-parallel/src/week1) -
* Deadlock
* Parallelism on the JVM
* First Class Tasks

#### Programming Assignments
1. Box Blur Filter - demonstrates Tasks
  * [boxBlurKernel](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/scalashop/src/main/scala/scalashop/package.scala) - this method computes the blurred RGBA value of a single pixel of the input image
  * [HorizontalBoxBlur](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/scalashop/src/main/scala/scalashop/HorizontalBoxBlur.scala) - singleton which implements methods blur and parBlur to blur horizontal pixels of the image in parallel
  * [VerticalBoxBlur](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/scalashop/src/main/scala/scalashop/VerticalBoxBlur.scala) - singleton which implements methods blur and parBlur to blur vertical pixels of the image in parallel
  * To run the ScalaShop program, in the console:
    ```
      sbt
      > runMain scalashop.ScalaShop
    ```
2. Reductions and Prefix Sums - demonstrates basic task parallel algorithms
3. K-Means - demonstrates Data-Parallelism
4. Barnes-Hut Simulation - demonstrates data structures for Parallel Computing
