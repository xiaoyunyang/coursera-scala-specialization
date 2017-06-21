## Motivation

__Why Learn Functional Programming?__

Let's start with a simple example to demonstrate the key differences between functional programming  and imperative programming.

Suppose you want to find the average of a list of Ints. In imperative programming, you would do something like this:

```java
//In Java
double[] arr = new double[] {1,2,3};
double acc = 0;
for(int i=0; i<arr.length; i++) {
	acc += arr[i];
}
double ave = acc/arr.length;
System.out.println("The average of " + Arrays.toString(arr) + " is "+ave);  //> The average of [1.0, 2.0, 3.0] is 2.0
```

The functional programming solution is much shorter and higher level:

```scala
//In Scala
def list = List(1.0, 2.0 ,3.0)
def findAve(l: List[Double]): Double  = l.reduce(_ + _)/l.length
findAve(List(1,2,3))
println(s"The average of $list is $ave") //> The average of List(1.0, 2.0, 3.0) is 2.0
```

30 years ago, the emphasis was on creating and maintaining small code base that need to fulfill a set of functional requirements. Computational power was a huge constraint. Now we live in a different world. We build complex programs with many moving parts. Programs are expected to be reliable, responsive, and error resistant. Servers are scattered throughout the world so things like latency and multicore programming became the new pain points.

In the words of [SICP](https://mitpress.mit.edu/sites/default/files/6515.pdf):

* A Program is a pattern of rules to direct processes that manipulate data. The functional programming paradigm helps us:
	
	> use higher order functions to capture patterns in usage.

	> control complexity by building abstractions that hide details when appropriate.

	> Programs must be written for people to read and only incidentally for machines to execute.

__Why Scala?__

Scala is type-safe. Using the findAve example from earlier, type safe means you can impose the argument type and return type when you first define your function in Scala.

```scala
def findAve(l: List[Double]): Double  = l.reduce(_ + _)/l.length
findAve(List(1,2,3))
```

Suppose you leave out the return type `Double` in the function signature. Scala can deduce that the return type is based on the argument type.
```scala
def findAve(l: List[Double])  = l.reduce(_ + _)/l.length
//> findAve: (l: List[Double])Double
```

Type inference is pretty convenient at time and some languages like javascript do a good job making type inferences. 

However, omiting the type of your data can be the source of some nasty bugs such as the one demonstrated below in javascript. Javascript seems to automatically convert your argument type from int to double.

```javascript
//In Javascript
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

#### Cool Ideas in FP
__Currying__
The idea of Currying is named after the mathematician Haskell Curry. He also discovered one of the most important results in computer science, the Curry-Howard isomorphism which says that a program is a logical proof, and the hypothesis that it proves is its type.

```scala
//Currying is when you break down a function that takes multiple arguments into a //series of functions that take part of the arguments.
//converts a function f of two arguments into a function of one argument that partially applies f.
  def curry[A,B,C](f: (A,B) => C): A => (B => C) =
  		(a: A) => (b: B) => f(a,b)  
  //> curry: [A, B, C](f: (A, B) => C)A => (B => C)
```





