# coursera-scala-specialization

## Functional Programming Principles in Scala
Instructor: **Martin Odersky**
#### Topics
[Week 1](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1) -
* Evaluation strategy and recursion: [fixedpoint.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1/fixedpoint.sc) demonstrates writing a basic function in scala.
 [eval_strategy.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-functional/src/week1/eval_strategy.sc) illustrates call-by-vale (imperative, C) versus call-by-name (not to be confused with lazy evaluation).

 ```scala
  def constOne(x: Int, y: => Int) = 1   //y has a type '=> Int' which means y is passed as a by-name parameter
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
* To run a Scala Application, just add a main function:

  ```scala
  object ScalaProgram {
    def main(args: Array[String]) = {
     println("hello world!")
    }
  }
  ```
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
1. Recursive Functions - demonstrates recursion
2. Functional Sets - demonstrates Higher Order Functions
3. Object Oriented Sets - demonstrates data and abstraction
4. Huffman Encoding - demonstrates Pattern Matching and the scala List Collection
5. Anagrams - demonstrates Collections

## Functional Program Design in Scala
Instructor: **Martin Odersky**

#### Topics
[Week 1](https://github.com/xiaoyunyang/coursera-scala-specialization/tree/master/coursera-program-design/src/week1) -
*  Partial functions - See [partialfuns.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/partialfuns.sc)
 ```scala
 val f1: PartialFunction[String, String] = { case "ping" => "pong" }

 //result: List(pong, 404, 404)
 List("ping", "abc", "pong").map(a =>
   if(f1.isDefinedAt(a)) f1(a) else "404"
 )
 ```
* For-expressions/for-comprehension - shortcuts for doing a flatMap, filter, then a map. Useful when you need to do nested loops. See [collections.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/collections.sc) for how to use for expressions to implement filter, map, and flatMap, and vice versa. See [json.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/json.sc) and [query.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/query.sc) for how for expressions are useful for database query and json applications.
* Random Generators and ScalaCheck - [Generator.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/Generators.scala) has various random generators written using ```scala.util.Random```. [generators.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/generators.sc) shows how it's used.
* Monad - functional programming and reactive programming pattern. Three Monad Laws, Option and Try. See [monad.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week1/monad.sc).

[Week 2](https://github.com/xiaoyunyang/coursera-scala-specialization/tree/master/coursera-program-design/src/week2) -
* Streams - Streams are similar to lists, but their tail is evaluated only on demand. There are three ways to create Streams:
 ```scala
  //Notice the tail is a "?". This means the tail is not yet evaluated
  Stream(1,2,3) //res: Stream(1, ?)
  (1 to 3).toStream //res: Stream(1, ?)
  val xs = Stream.cons(2, Stream.cons(3, Stream.empty)) //res: Stream(2, ?)
  1 #:: xs //res: Stream(1, ?)
 ```

 Unlike the List, only the head of the Stream gets evaluated when it's first instantiated. The tail do not get evaluated until it's first needed.

 ```scala
 def streamRange(lo: Int, hi: Int): Stream[Int] = {
    print(lo+" ") //<- a side effect
    if(lo >= hi) Stream.empty
    else Stream.cons(lo, streamRange(lo + 1, hi))
 }

 /* The following will not print out "1" because the tail of the
  * stream is unevaluated so it will only print out the head (i.e. 1)
  */
 streamRange(1, 10).take(3)  

 /* The following will print out "1 2 3" as side effect because we
  * are forcing all the tails of the Stream to be evaluated by making * it toList
  */
 streamRange(1, 10).take(3).toList
 ```
 See [MyStream.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/MyStream.scala) for an implementation and [Stream.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/stream.sc) for some examples of using Stream.
* Lazy Evaluation - Laziness means do things as late as possible and never do them twice. The later is achieved with memoization, meaning storing the result of the first evaluation and re-using the stored result instead of recomputing. This optimization is sound, since in a purely functional language, an expression produces the same result each time it is evaluated. In the case of by-name and strict evaluation, everything is recomputed. In general, lazy evaluation is a combination of by-name evaluation and memoization. See [laziness.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/laziness.sc) for some examples of lazy evaluations and infinite sequence using Streams. [MyLazyStream.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/MyLazyStream.scala) modified [MyStream.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/MyStream.scala) to be lazy in the tail.

 ```scala
 //If you run the following program, "xzyz" gets printed as a side effect
 def expr = {
    val x = { print("x"); 1} //val gets instantiated when you first define it
    lazy val y = { print("y"); 2 } //lazy val gets instantiated only the first time you use it
    def z = { print("z"); 3 } //def gets instantiated everytime you use it
    z + y + x + z + y + x
 }    
 ```
* Infinite Sequence - using Streams

 ```scala
 // The Sieve of Eratosthenes:
 def sieve(s: Stream[Int]): Stream[Int] =
   s.head #:: sieve(s.tail filter (_ % s.head != 0))
 
 val primes = sieve(from(2))  //> primes  : Stream[Int] = Stream(2, ?)
 primes.take(10).toList    //> res4: List[Int] = List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
 ```
* The water pouring problem - Program Design. Two optimizations to avoid re-computation: (1) keep a list of explored paths, pass that into the next Path calculation (2) pass the current endState to the constructor of the next Path so the next Path won't have to recompute the last Path's endState all over again using the history. See [Pouring.scala](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/Pouring.scala) and [pouringTest.sc](https://github.com/xiaoyunyang/coursera-scala-specialization/blob/master/coursera-program-design/src/week2/pouringTest.sc) for implementation and result.

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
  * boxBlurKernel - this method computes the blurred RGBA value of a single pixel of the input image
  * HorizontalBoxBlur - singleton which implements methods blur and parBlur to blur horizontal pixels of the image in parallel
  * VerticalBoxBlur - singleton which implements methods blur and parBlur to blur vertical pixels of the image in parallel
  * To run the ScalaShop program, in the console:
    ```
      sbt
      > runMain scalashop.ScalaShop
    ```
2. Reductions and Prefix Sums - demonstrates basic task parallel algorithms
3. K-Means - demonstrates Data-Parallelism
4. Barnes-Hut Simulation - demonstrates data structures for Parallel Computing
