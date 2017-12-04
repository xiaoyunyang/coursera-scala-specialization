## Motivation
![Why Functional Programming](https://i.imgur.com/Sex1E8m.jpg)

__Why Learn Functional Programming (FP)?__

Let's start with a simple motivating example to demonstrate the key differences between functional programming  and imperative programming.

Suppose you want to find the average of a list of Ints. In imperative programming, you would do something like this:

```java
//In Java
double[] arr = new double[] {1,2,3};
double acc = 0;
for(int i=0; i<arr.length; i++) {
	acc += arr[i];
}
double ave = acc/arr.length;

System.out.println("The average of " + Arrays.toString(arr) + " is "+ave);  
//> The average of [1.0, 2.0, 3.0] is 2.0
```

The functional programming solution is much shorter and higher level:

```scala
//In Scala
def list = List(1.0, 2.0 ,3.0)
def findAve(l: List[Double]): Double  = l.reduce(_ + _)/l.length
findAve(List(1,2,3))

println(s"The average of $list is $ave") 
//> The average of List(1.0, 2.0, 3.0) is 2.0
```

30 years ago, the emphasis was on creating and maintaining small code base that need to fulfill a set of functional requirements. Computational power was a huge constraint. Now we live in a different world. We build complex programs with many moving parts. Programs are expected to be reliable, responsive, and error resistant. Servers are scattered throughout the world so things like latency and multicore programming became the new pain points.

The most visible benefits of functional programming are:

* __developer productivity__ - better code reusability and modularity, which is made possible by treating functions as composable primitives and something that describes behavior in the abstract (i.e., without binding it to actual use cases or data). 
* __Program correctness__ - a benfit of referential transparency
* __Scalability__ - program correct whether run on one core or multiple cores (another benefit of referential transparency)

__Why Now?__

* 1950s - 
Functional programming was invented, but not popular because it took more memory to be stateless. Memory was expensive.
* 1980s - Object Oriented Programming (OO) became popular in the 80s because GUIs became popular. In OO style, it’s easier to program things that use a fixed number of operations for an unlimited number of operation.
* 2010s - Memory is cheap. Can’t make transistors any smaller (marginal gains in hardware capability). Fast processing and big data processing requires more than one core.  There has been an increasing emphasis on asynchronous, distributed, multi-core and use cloud computing. Multicore competing for the same memory bus, OS no longer manages threads for you on multicore - if you need to perform the same tasks faster and faster, you can increase to unlimited number of cores. Stateful programming is more of a liability now with these new requirement. 
Functional ➡ no assignments ➡ no states ➡ no blocking or concurrency issues.

In the words of [SICP](https://mitpress.mit.edu/sites/default/files/6515.pdf), a Program is a pattern of rules to direct processes that manipulate data. The functional programming paradigm helps us:
	
	> use higher order functions to capture patterns in usage.

	> control complexity by building abstractions that hide details when appropriate.

	> Programs must be written for people to read and only incidentally for machines to execute.


## Key (and cool) Ideas From FP

This section talks about how to use functional programming concepts to design robust software programs. Ideas from this section are inspired by [This Video](https://www.youtube.com/watch?v=E8I19uA-wGY) and [This Book](https://www.manning.com/books/functional-programming-in-scala)


### Functional Programming Design Patterns

In the functional programming paradigm, we want to get away from hard coding values or function behaviors. Thus, we parameterize the behavior as another function.  How do we do that? We have to start thinking about functions as something configurable and composable. These are the bread and butter concepts that FP-ers must come to embrace as a way of life:


* __Functions__:  In FP, we think of our programs as pipes for data to travel through. Functions can be inputs and outputs.

* __Higher Order Functions (HOFs)__:  Probably the coolest and most useful concept in FP. A HOF is simply a function that takes another function as input.

* __Polymorphic Functions__:  If functions are pipes, think of a polymorphic function as a design for a pipe that can be used to make different types of pipes. Instead of creating a new function from scratch, you can "configure" a polymorphic function (sometimes called a generic function) to serve specific purpose of the function. We can "abstract out" the polymorphic function from observing the structural similarities between different functions.

Below is a common pattern that illustrates these three concepts:

__The Loop Pattern__

Let's look at what’s in common between the two loop functions and what’s distinct between them. 

* Common:  Take an array of *stuff* and combine them. 
* Distinct: The initial value and what the types of data you work on are different. 

```javascript
//in javascript

//A loop - prints "1 2 3" to console
var res = 0
let nums = [1, 2, 3]
for(i=0; i<nums.length; i++) {
	res = res + nums[i]
}

//Another loop - Performs 1+2+3 then print out sum
var res = ""
let strs = ["a", "b", "c"]
for(i=0; i< strs.length; i++) {
	res = res + strs[i]
}

```
We want to preserve the distinction and get rid of the boilerplate common code. So we can parameterize the loop action as a function parameter and the initial value as a data parameter in the fold function. Fold is basically a loop. The boilerplate is represented by the following pseudo code:

```
Provide arguments and some initial value.
Iterate through a loop updating this value.
```

We can write a function called `reduce` that takes care of the boilerplate that can be used to all kinds of problems that uses this pattern:

```javascript
[1,2,3].reduce((a,b) => (a+b), 0) //> 6
["a", "b", "c"].reduce((a,b) => a+b, "") //> "abc"
```

`reduce` is actually a __HOF__ that javascript [gives you for free](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/Reduce?v=a). What makes it a HOF is that it takes another function as an argument, i.e., `(a,b) => a+b`. In this example, we are being lazy by not declaring this function first (e.g., assigning to a const called `addTwo`) prior to using it as an argument in `reduce`. It is perfectly fine to leave that function without a name, that is *anonymous*, because it's simple and short enough that we can declare it directly in the `reduce` function. The `(a,b,) => a+b` is called an __anonymous function__.

`reduce` is also a __polymorphic function__. It does not hardcode behavior and lets you use it on any array of *stuff* and reduce it by specifying a rule (e.g., `(a,b) => a+b`).

However, if we want to be using the rule with this exact rule a lot, it may be useful to "configure" the `reduce` function to some more specific function for brevity and convenience.  As shown below, `myReduce` can be used on both arrays of strings and arrays of numbers.

```javascript
const myReduce = (arr, initVal) => 
	arr.reduce((a,b) => (a+b), initVal)

let arr1 = [1,2,3]
let arr2 = ["a", "b", "c"]
myReduce(arr1, 0) //> 6
myReduce(arr2, "") //>"abc"

```

What happens if we do this?

```javascript
let arr3 = [arr1, arr2]
myReduce(arr3, [])
```

The above is going to evaluate to `"1,2,3a,b,c"`. Why is that?

It turns out in javascript, if you want to concatenate arrays, you have to use `arr.concat`:

```javascript
let arr3 = [arr1, arr2]
arr3.reduce((a,b) => a.concat(b), [])

```
That may seem counterintuitive because when you use `+` on strings, you are essentially concatenating. Not to mention that fundamentally, strings are just an array of chars.

In scala, the `++` is used for concatenation of any collections, although the result is going to differ based on how you use it as shown below:

```scala
//In Scala
List(1,2,3) ++ List("a", "b", "c") //> res: List[Any] = List(1, 2, 3, a, b, c)

List(1,2,3) ++ "abc" 
//> res: List[AnyVal] = List(1, 2, 3, a, b, c)

"abc" ++ List(1,2,3) 
//res: scala.collection.immutable.IndexedSeq[AnyVal] = Vector(a, b, c, 1, 2, 3)

"abc" ++ "def" //> res: String = abcdef
"abc" + "def" //> res: String = abcdef
```

__Function Composition and Partial Application (Currying)__

The idea of Currying is named after the mathematician Haskell Curry. He also discovered one of the most important results in computer science, the Curry-Howard isomorphism which says that a program is a logical proof, and the hypothesis that it proves is its type.

```scala
//Currying is when you break down a function that takes multiple arguments into a 
//series of functions that take part of the arguments.
//converts a function f of two arguments into a function of one argument that partially applies f.
  def curry[A,B,C](f: (A,B) => C): A => (B => C) =
  		(a: A) => (b: B) => f(a,b)  
  //> curry: [A, B, C](f: (A, B) => C)A => (B => C)
```

Bottomline: Partial application lets you make reusable functions.

### Types

An interesting observation from the sample code above shows that Scala distinguishes between many types of values while javascript has just a few, i.e., `var` (mutable, global),  `const` (immutable) or `let` (mutable, local). This brings up another interesting feature of FP - Types. As quoted from [This Video](https://www.youtube.com/watch?v=E8I19uA-wGY):

> Type are not classes. Types are just the label for the set of inputs or the set of outputs. Classes are a collection of functions, Types can be composed.

Scala is type-safe, which is the biggest selling point for Scala according to [LightBend](https://www.lightbend.com/) (formerly TypeSafe) - the company founded by the creator of Scala. Using the findAve example from earlier, type safe means you can impose the argument type and return type when you first define your function in Scala.

```scala
def findAve(l: List[Double]): Double  = l.reduce(_ + _)/l.length
findAve(List(1,2,3))
```
Look What happens in a language that's not type safe. Suppose you have a text input to get the user to enter a number `num`, then your program subtracts 1 from `num`.

```javascript
//In javascript
let num = "7" //user inputs "7" for num
let res = num - 1 //> 6 ... good

let num = "07" //user inputs "07" for num
res = num - 1 //>6 ... still good

let num = "apple" - 1 //> NaN
res = num - 1 //> NaN ... bad

```

In a type-safe language like scala, subtracting 1 from a String doesn't make sense. However, in javascript, it works - but only if the String represents a number.  

When the language is not type-safe, it's up to the programmer to ensure that the string indeed represents a number. Also, javascript is dynamically typed so the compiler won't help you catch this type of error. Errors would come up during run-time depending what that string is, which could lead to some nasty bugs.

Suppose you leave out the return type `Double` in the function signature. Scala can deduce that the return type is based on the argument type.
```scala
def findAve(l: List[Double])  = l.reduce(_ + _)/l.length
//> findAve: (l: List[Double])Double
```

Type inference is pretty convenient at time and some languages like javascript do a good job making type inferences. 

However, omiting the type of your data can be the source of some nasty bugs such as the one demonstrated below in javascript. javascript seems to automatically convert your argument type from int to double.

```javascript
//In javascript
let findAve = arr => arr.reduce((a,b) => a+b)/arr.length

findAve([1,2,3]) //> 2
findAve([1,1,3]) //>1.6666666666666667
```

```scala
//In Scala
def findAve(l: List[Int])  = l.reduce(_ + _)/l.length  
//> findAve: (l: List[Int])Int

findAve(List(1,2,3)) //> 2
findAve(List(1,1,3)) //> 1

def findAve2(l: List[Int]): Double  = l.reduce(_ + _)/l.length
findAve2(List(1,1,3)) //> 1.0

def findAve3(l: List[Double]): Double  = l.reduce(_ + _)/l.length
findAve: (l: List[Double])Double
findAve(List(1,1,3)) //>1.6666666666666667
```


__Pattern Matching__


__Recursion__


### Referential Transparency (RT)

As [FP in Scala](https://github.com/fpinscala/fpinscala/wiki/Chapter-1:-What-is-functional-programming%3F) puts it:
> Referential transparency forces the invariant that everything a function does is represented by the value that it returns, according to the result type of the function. At each step we replace a term with an equivalent one; computation proceeds by substituting equals for equals. In other words, RT enables equational reasoning about programs.

I like the second definition better:
> Another way of understanding RT is that the meaning of RT expressions does not depend on context and may be reasoned about locally, whereas the meaning of non-RT expressions is context-dependent and requires more global reasoning.

The obvious benefit is RT makes the program less prone to bugs. Since RT forces data immutability, making a change to your data requires you to create new data, which hurts memory performance. Similar to recursion, there are things "under the hood" that can be done to improve memory performance while still enforcing immutability at a high level. 

We want to write referential transparent programs to avoid side effects. What are side effects and what are the dangers of side effects? See Monad section below for more on this.


### Laziness
Laziness and memoization could be translated to two obvious principles:

* Laziness = “Don’t compute something until you need it.”
* Memoization = “Don’t recompute something you have computed before.”

__Closure__

When you declare a local variable, that variable has a scope. Generally local variables exist only within the block or function in which you declare them.

A closure is a persistent local variable scope.

### Monad
Now you understand higher order functions and types, you are ready for learning what a monad is. Instead of hard coding error handlers in your code to deal with non-deterministic nature of real world applications, you delegate the error handling to monads, which are implemented as a type in Scala. Another way to think about the monad is that [a monad is simply a wrapper](https://medium.com/@sinisalouc/demystifying-the-monad-in-scala-cc716bb6f534).

There are typically four types of monads to express four types of side effects:

A naive way to handle non-determinism is by writing code with lots of side effects.

> A side effect is any application state change that is observable outside the called function other than its returned value. ~ [Eric Elliott](https://medium.com/javascript-scene/master-the-javascript-interview-what-is-functional-programming-7f218c68b3a0)

Java web applications from the early 2000s are littered with try/catch blocks to guard against unintended/undesirable program states due to external processes (e.g., user input or loss of network connectivity). This practice often implies lots of boilerplate code that are error prone and results in the _blocking_ of operations downstream until the necessary information is obtained.

Many programs designed with a user interface are especially at risk of side effects. A tell tale sign is anytime your function is reading or modifying external variable (e.g., a global variable) outside its own function scope. These external variables include:

* Reading user input
* Writing to a file
* Reading from a network
* Reliance on a global state variable (Note: reading/modifying global variables/states is a necessary evil in many real time embedded systems due to microprocessor constraints; however, you should not be doing that if your distributed software is not running on microprocessors.)

How do side effects hurt developer productivity and program correctness?

Side effects makes it difficult for you to reason about the correctness of your program locally. If you have a referentially transparent function (i.e., free of side effects), then you can look at your function and know exactly what it does, meaning the proof of correctness may be limited to the case of testing the response of the function to given inputs. When your program is not referentially transparent, you need to obtain knowledge about external processes, global variables/states, and there is no way to prove correctness of your function by simply running unit tests. You need to run acceptance testing and user testing every time you make a small change in a function. This can have _expensive_ ramification in the real world.

We don't want to get rid of the interesting parts of our program (e.g., user interface) in fear that they will introduce side effects into our program. Rather we want to introduce FP design patterns into our programs to protect us from the negative consequences of side effects. How do we do that? Monads. 

There are typically four types of monads to express four types of side effects:

1. Exception - Takes into account operations that can fail. The exception monad is [implemented](http://blog.xebia.com/try-option-or-either/) using `Option`, `Either`, or `Try` in Scala.
2. Future - Takes into account that computation takes time (latency). The future monad is implemented using `Future` in Scala, which does a callback when the task is complete.
3. Iterable - Reacting to synchronous data streams.
4. Observable - Reacting to asynchronous data streams.


### Monoid

The idea of monoid is actually simple. You'd encounter a monoid in your day-to-day programming without even realizing it's called a monoid. I've encountered it the other day while trying to determine whether there is a non-empty string in my collections of arrays.

```javascript
//In javascript

let arr1 = [""]
let arr2 = ["", ""]
let arr3 = ["", "", "a"]

let res1 = arr1.reduce((a,b) => a+b) //> ""
let res2 = arr2.reduce((a,b) => a+b) //> ""
let res3 = arr3.reduce((a,b) => a+b) //> "a"

const strLen = arr => 
	arr.reduce((a,b) => a+b).length

strLen([res1]) //> 0
strLen([res1, res2]) //> 0
strLen([res1, res2, res3]) //> 1
strLen([res3, res2, res1]) //> 1
```

What do we notice?  

* The function `isEmpty` will provide the same correct result no matter whether the ``"a"`` lives in `arr1`, `arr2` or `arr3`. The result does not hinges on the groupings of the strings (Associativity).
* The final result does not care how many empty strings you have as any string `s` concatenated with the empty string is that string (i.e., `s + "" == s`). However, having a single non-empty string changes your result.
* The order in which the string inputs are provided to the function `isEmpty` does not impact the final result (Commuativity).

This is recipe for correctness in asynchronous programming and parallel computing.

Because how you group the things over which you want to compute doesn't matter and the order of computing doesn't matter, you can calculate the final result by assigning the subproblems to be calculated by different processors and combine the final answer at the end.

The most [common monoids](https://www.safaribooksonline.com/blog/2013/05/15/monoids-for-programmers-a-scala-example/) are:

* Integers with addition. 
* Strings with concatenation.
* Lists with concatenation, like `List(1,2)+List(3,4) == List(1,2,3,4)`.
* Sets with their union, like `Set(1,2,3)+Set(2,4) == Set(1,2,3,4)`.

Bottomline:  Why do we care about monoids at all? You can use the monoid laws to verify correctness of your program (esp. when your program deals with aggregating data and operations) under asynchronous conditions and evaluate whether the behavior of your program is going to change if you tried to use parallel computing to optimize for speed and load distribution. 