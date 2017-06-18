## Minor Differences

__Differences between val and def (and lazy val)__

```scala
List(1,2,3).mkString(" ")      //> res1: String = 1 2 3
List(1,2,3).mkString(" -- ")   //> res1: String = 1 -- 2 -- 3
def f = println("foo")         //> f: => Unit
val g = println("foo")         //> foo
                               //| g  : Unit = ()



for(i<- 1 until 5) f                      //> foo
                                          //| foo
                                          //| foo
                                          //| foo
  
 for(i<- 1 until 5) g
```

__def vs. val__

```scala
//These guys have to be def beause if they were val, then they get evaluated the first time
//they are declared. We want to measure the evaluation inside the scalameter
def e1 = "hello".toList.map(f(_))         //> e1: => List[String]
val e11 = "hello".toList.map(f(_))        //> e11  : List[String] = List(h!, e!, l!, l!, o!)
lazy val e12 = "hello".toList.map(f(_))   //> e12: => List[String]


def e13 = "hello".toList.map(f2(_))       //> e13: => List[String]
val e14 = "hello".toList.map(f2(_))       //> e14  : List[String] = List(h!, e!, l!, l!, o!)


def e2 = "world".toList.map(a => f(a))    //> e2: => List[String]
def e3 = f("hello")                       //> e3: => String
def e4 = f("world")                       //> e4: => String

val (v1, v2) = parallel(e1, e2)           //> v1  : List[String] = List(h!, e!, l!, l!, o!)
                                                  //| v2  : List[String] = List(w!, o!, r!, l!, d!)

 
//lazy val - first time slow, then subsequent evaluations are fast because of memoization
//computation first time it's called below.
val time12 = withWarmer(new Warmer.Default) measure { e12 }
                                                  //> time12  : Double = 0.001023
val time12a = withWarmer(new Warmer.Default) measure { e12 }
                                                  //> time12a  : Double = 5.84E-4
val time12b = withWarmer(new Warmer.Default) measure { e12 }
                                                  //> time12b  : Double = 6.32E-4

 
//def - slow everytime because recomputes everytime you call it
val time1 = withWarmer(new 
Warmer.Default) measure { e1 }
                                                 
 //> time1  : Double = 0.030508
val time1a = withWarmer(new Warmer.Default) measure { e1 }
                                                  //> time1a  : Double = 0.03919
val time1b = withWarmer(new Warmer.Default) measure { e1 }
                                                  //> time1b  : Double = 0.012284


//val - everytime fast because computation is performed when the val was first declared above
val time11 = withWarmer(new 
Warmer.Default) measure { e11 }
                                                 
 //> time11  : Double = 4.63E-4
val time11a = withWarmer(new Warmer.Default) measure { e11 }
                                                  //> time11a  : Double = 4.43E-4
val time11b = withWarmer(new Warmer.Default) measure { e11 }
                                                  //> time11b  : Double = 4.69E-4
```

__lazy val vs. val vs. def__

```scala
//If you run the following program, "xzyz" gets printed as a side effect
 def expr = {
   val x = { print("x"); 1} //val gets instantiated when you first define it
   lazy val y = { print("y"); 2 } //lazy val gets instantiated only the first time you use it
   def z = { print("z"); 3 } //def gets instantiated everytime you use it
   z + y + x + z + y + x
 }                                         //> expr: => Int 
expr                                      //> xzyzres0: Int = 12
```

__List vs. Vector__

* The depth of the access is the depth of tree
* List - O(N)
	* If your access pattern requires you to always take head of the operation and take tail to process the left, then List 
* Vector - O(Log_32(N))
	* Good for bulk operations, such as map or a fold or filter (highly parallelizable operations). You can do it in chunks of 32.


__Map__

```scala
def showCapitalWithOption(country: String) = capitalOfCountry.get(country) match {
  case Some(capital) => capital
  case None => "missing data"
}
showCapitalWithOption("US")               //> res3: String = Washington
showCapitalWithOption("Andorra")          //> res4: String = missing data

val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern")
  
//With default function essentially does the same thing
val cap1 = capitalOfCountry withDefaultValue "<unknown>"
cap1("Andorra")                           //> res5: String = <unknown>

capitalOfCountry get "Andorra"                  //> res1: Option[String] = None
capitalOfCountry get "US"                      //> res2: Option[String] = Some(Washington)
```

__Repeated Variable__
```scala
//the * means it's a repeated variable
def this(bindings: (Int, Double)*) = this(bindings.toMap)
```

__Random Generators__
```scala
Give me 400 random ints
val r = new scala.util.Random
val lst = List.fill(400)(r.nextInt*2)
```

__Easier way to println__

```scala
println("a is "+a+" and b is "+b)     //> a is foo and b is foo
println(s"a is $a and b is $b")       //> a is foo and b is foo
println(s"c is $c")                   //> c is (bar,baz,2)

If you want to add an object inside a class, make sure you use
implicit object
```

__What’s the difference between `::` and `:::`__
`::` prepends a single item whereas `:::` prepends a complete list. So, if you put a List in front of `::` it is taken as one item, which results in a nested structure.

* `::` is called Cons
* `++` is the Concatenation notation

```scala
Vector(1,2) ++ Vector(3,4)                      //> res5: scala.collection.immutable.Vector[Int] = Vector(1, 2, 3, 4)
List(1,2) ++ List(3,4)                          //> res5: List[Int] = List(1, 2, 3, 4)
List(1,2) :: List(3,4)                          //> res5: List[Any] = List(List(1, 2), 3, 4)
List(1,2) ::: List(3,4)                         //> res5: List[Int] = List(1, 2, 3, 4)
(Stream(1,2) ++ Stream(3,4)).toList             //> res5: List[Int] = List(1, 2, 3, 4)
```

__Vector__

```scala
Vector(1,2) updated (0, 3)                //> res6: scala.collection.immutable.Vector[Int] = Vector(3, 2)
```

__Constructor__
In the constructor, you can actually have argument name to make things clearer

```scala
  val books: List[Book] = List (
  Book(title = "Structure and Interpretation of Computer Programs",
             authors = List("Abelson, Harald", "Sussman, Gerald J.”)))
 
  case class Book(title: String, authors: List[String])
```


Note that since=>associates to the right,A => (B => C)can be written asA => B => C.
A major theme in functional programming is separation of concerns.

__map2 vs. traverse vs. sequence__

```scala
def map2[EE >: E, B, C](b: Either[EE, B])(f: (A,B) => C): Either[EE, C] =
  for {
    aa <- this
    bb <- b
  } yield(f(aa,bb))   

def traverse[E,A,B](as: List[A])(f: A => Either[E,B]): Either[E, List[B]] = {
  as.foldRight[Either[E, List[B]]](Right(Nil))((h,t) => f(h).map2(t)(_ :: _)) 
}
def sequence_1[E, A](es: List[Either[E,A]]): Either[E, List[A]] = {
  traverse(es)(x => x)
}
def sequence[E, A](es: List[Either[E,A]]): Either[E, List[A]] = {
  es.foldRight[Either[E, List[A]]](Right(Nil))((h,t) => h.map2(t)(_ :: _))
}
```
__Tuple__

`
scala> (1,2)._1
res4: Int = 1
scala> (1,2)._2
res5: Int = 2
`

__Trait vs. Abstract Class__

Whenever you implement a reusable collection of behavior, you will have to decide whether you want to use a trait or an abstract class. There is no firm rule, but this section contains a few guidelines to consider. From [To Trait, or not to Trait](http://www.artima.com/pins1ed/traits.html#12.7):

* If the behavior will not be reused, then make it a concrete class. It is not reusable behavior after all.
* If it might be reused in multiple, unrelated classes, make it a trait. Only traits can be mixed into different parts of the class hierarchy.
* If you want to inherit from it in Java code, use an abstract class. Since traits with code do not have a close Java analog, it tends to be awkward to inherit from a trait in a Java class. Inheriting from a Scala class, meanwhile, is exactly like inheriting from a Java class. As one exception, a Scala trait with only abstract members translates directly to a Java interface, so you should feel free to define such traits even if you expect Java code to inherit from it. See Chapter 29 for more information on working with Java and Scala together.


Inside trait, we can use the keyword `Trait`. Functions inside trait don’t use polymorphic declarations
Look at [Scala API for Trait](http://docs.scala-lang.org/tutorials/tour/traits.html)

```scala
//Inside Trait
sealed trait Stream[+A]

def getSelf: Stream[A] = this match {
  case Cons(h,t) => Stream.cons(h(),t())
  case _ => Empty
}
```

Functions inside companion objects of the trait all have polymorphism in the signature, e.g., [A]

From the [Twitter Scala School Basics 2](https://twitter.github.io/scala_school/basics2.html)

* Objects are used to hold single instances of a class. Often used for factories. Classes and Objects can have the same name. The object is called a ‘Companion Object’. We commonly use Companion Objects for Factories.
* A Function is a set of traits. Specifically, a function that takes one argument is an instance of a Function1 trait. This trait defines the apply() syntactic sugar, allowing you to call an object like you would a function.
* methods in classes are methods
A nice short-hand for `extends Function1[Int, Int]` is `extends (Int => Int)`

```scala
class AddOne extends (Int => Int) {
  def apply(m: Int): Int = m + 1
}
```

```scala
//Inside companion Object of the Trait

case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]
object Stream

def getSelf[A](s: Stream[A]): Stream[A] = s match {
  case Cons(h,t) => Stream.cons(h(),t())
  case _ => Empty
}
def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
  lazy val head = hd
  lazy val tail = tl
  Cons(() => head, () => tail) 
}
```

* When in doubt, use Trait
Other than the fact that you cannot directly extend multiple abstract classes, but you can mixin multiple traits into a class, it's worth mentioning that traits are stackable, since super calls in a trait are dynamically bound (it is referring a class or trait mixed before current one). See Thomas's answer in [Difference between Abstract Class and Trait](https://stackoverflow.com/questions/2005681/difference-between-abstract-class-and-trait)

Trait vs Class

* functions inside Trait don’t use polymorphism in the function signature (e.g., [A])
* functions inside Trait can use keyword “this"


## Scala Style

__Naming Conventions__

* acc - accumulator
* cts - counts
* ints - list of ints
* xs - a sequence of x
* It’s a common convention to use xs, ys, as, or bs as variable names for a sequence of some sort, and x, y, z, a, or b as the name for a single element of a sequence. Another common naming convention is h for the first element of a list (the head of the list), t for the remaining ele- ments (the tail), and l for an entire list.
* 	tmp - temp
*  msg - message
* f - a higher order function
	
	> It’s a common convention to use names like f, g, and h for parameters to a higher- order function. In functional programming, we tend to use very short variable names, even one-letter names. This is usually because HOFs are so general that they have no opinion on what the argument should actually do. All they know about the argu- ment is its type. Many functional programmers feel that short names make code eas- ier to read, since it makes the structure of the code easier to see at a glance. ~ Function Programming in Scala

__Parentheses__
