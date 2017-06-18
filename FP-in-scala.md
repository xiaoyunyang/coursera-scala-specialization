## Motivation

__Why Learn Functional Programming?__

30 years ago, the emphasis was on creating and maintaining small code base that need to fulfill a set of functional requirements. Computational power was a huge constraint. Now we live in a different world. We build complex programs with many moving parts. Programs are expected to be reliable, responsive, and error resistant. Servers are scattered throughout the world so things like latency and multicore programming became the new pain points.

[SICP](https://mitpress.mit.edu/sites/default/files/6515.pdf)

* A Program is a pattern of rules to direct processes that manipulate data. The functional programming paradigm helps us:
	
	> use higher order functions to capture patterns in usage.

	> control complexity by building abstractions that hide details when appropriate.

	> Programs must be written for people to read and only incidentally for machines to execute.

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





