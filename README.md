# coursera-scala-specialization

## Functional Programming Principles in Scala
Instructor: **Martin Ordesky**
#### Topics
[Week 1](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1) -
* Evaluation strategy and recursion

[Week 2](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week2) -
* Scala class
* higher order functions
* example: Rational.scala

[Week 3](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week3) -
* Scala traits and abstract class
* class hierarchy and organization
* examples: List.scala and IntSet.scala

[Week 4](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week4) -
* subtyping and polymorphism
* pattern matching
* Scala trait, case class and companion objects (alternative to abstract class and class implementations)
* examples: Expr.scala, Expr2.scala, and Expr3.scala

#### programming assignments:
1. [Recursive Functions](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/recfun/src/main/scala/recfun/Main.scala) - demonstrates recursion
2. [Functional Sets](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/funsets/src/main/scala/funsets/FunSets.scala) - demonstrates Higher Order Functions
3. [Object Oriented Sets](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/objsets/src/main/scala/objsets/TweetSet.scala) - demonstrates data and abstraction
    * [My Test](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/objsets/src/test/scala/objsets/test.sc) - scala worksheet output
4. [Huffman Encoding](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/patmat/src/main/scala/patmat/Huffman.scala) - demonstrates Pattern Matching and the scala List Collection
  * [My Test](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/patmat/src/test/scala/patmat/test.sc) - scala worksheet output
5. Anagrams - demonstrates Collections

## Functional Program Design in Scala
Instructor: **Martin Ordesky**
#### programming assignments:
1. Bloxorz - demonstrates lazy evaluation
2. Quickcheck - demonstrates functions and state
3. Calculator - demonstrates timely effects

## Parallel Programming
Instructors: **Viktor Kuncak and Aleksander Prokopec**

#### programming assignments:
1. Box Blur Filter - demonstrates Tasks
  * [boxBlurKernel](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/scalashop/src/main/scala/scalashop/package.scala) - this method computes the blurred RGBA value of a single pixel of the input image
  * [HorizontalBoxBlur](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/scalashop/src/main/scala/scalashop/HorizontalBoxBlur.scala) - singleton which implements methods blur and parBlur to blur horizontal pixels of the image in parallel
  * [VerticalBoxBlur](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/scalashop/src/main/scala/scalashop/VerticalBoxBlur.scala) - singleton which implements methods blur and parBlur to blur vertical pixels of the image in parallel
  * To run the ScalaShop program:
  ```
    sbt
    > runMain scalashop.ScalaShop
  ```
2. Reductions and Prefix Sums - demonstrates basic task parallel algorithms
3. K-Means - demonstrates Data-Parallelism
4. Barnes-Hut Simulation - demonstrates data structures for Parallel Computing
