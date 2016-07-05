package week2

object HOF {
	//take the sum of integers between a and b
	def sumIntsNaive(a: Int,b: Int): Int = if(a>b) 0 else a + sumInts(a+1, b)
                                                  //> sumIntsNaive: (a: Int, b: Int)Int
	
	def sumCubesNaive(a: Int, b: Int): Int =
		if(a > b) 0 else cube(a) + sumCubes(a+1, b)
                                                  //> sumCubesNaive: (a: Int, b: Int)Int
	
	/*** Higher Older functions ***/
	def sumInts(a: Int, b: Int) = sum(id, a, b)
                                                  //> sumInts: (a: Int, b: Int)Int
	def sumCubes(a: Int, b: Int) = sum(cube, a, b)
                                                  //> sumCubes: (a: Int, b: Int)Int
	def sumFactorials(a: Int, b: Int) = sum(fact, a, b)
                                                  //> sumFactorials: (a: Int, b: Int)Int
	
	//where
	def id(x: Int): Int = x                   //> id: (x: Int)Int
	def cube(x: Int): Int = x*x*x             //> cube: (x: Int)Int
	def fact(x: Int): Int = if(x==0) 1 else x*fact(x-1)
                                                  //> fact: (x: Int)Int
  def sum(f: Int => Int, a: Int, b: Int): Int = {
		if(a > b) 0
		else f(a) + sum(f, a+1, b)
	}                                         //> sum: (f: Int => Int, a: Int, b: Int)Int
	
	/*** Anonymous Functions ***/
	(x: Int) => x * x * x                     //> res0: Int => Int = <function1>
	//Here, (x: Int) is the parameter of the function and x * x * x is its body
	
	def sumIntsAnon(a: Int, b: Int) = sum(x => x, a, b)
                                                  //> sumIntsAnon: (a: Int, b: Int)Int
	def sumCubesAnon(a: Int, b: Int) = sum(x => x*x*x, a, b)
                                                  //> sumCubesAnon: (a: Int, b: Int)Int
	
	
	def sum2(f: Int => Int, a: Int, b: Int): Int = {
		def loop(a: Int, acc: Int): Int = {
			if(a>b) acc
			else loop(a+1, f(a)+acc)
		}
		loop(a, 0)
	}                                         //> sum2: (f: Int => Int, a: Int, b: Int)Int
	
	sum(id, 1, 4)                             //> res1: Int = 10
	sum2(id, 1, 4)                            //> res2: Int = 10
	sum(id, 13, 40)                           //> res3: Int = 742
	sum2(id, 13, 40)                          //> res4: Int = 742
	
	/*** Currying ***/
	//special form for writing HOFs
	def sum3(f: Int => Int): (Int, Int) => Int = {
		def sumF(a: Int, b: Int): Int = {
			if(a > b) 0
			else f(a) + sumF(a+1, b)
		}
		sumF
	}                                         //> sum3: (f: Int => Int)(Int, Int) => Int
	def fact3: Int => Int = {
		def factF(x: Int): Int = {
			if(x==0) 1
			else x*factF(x)
		}
		factF
	}                                         //> fact3: => Int => Int
	def sumInts3 = sum3(x => x)               //> sumInts3: => (Int, Int) => Int
	def sumCubes3 =  sum3(x => x*x*x)         //> sumCubes3: => (Int, Int) => Int
	def sumFactorials3 = sum3(fact3)          //> sumFactorials3: => (Int, Int) => Int
	
	//sum3(cube) is equivalent to sumCubes, which returns (Int, Int) => Int.
	//We can apply sum3(cube) onto (1,10) just as we can apply sumCubes3 onto (1,10)
	sum3(cube)(1,10)                          //> res5: Int = 3025
	sumCubes3(1,10)                           //> res6: Int = 3025
	
	//This demonstrates that function application associates to the left
	//sum(cube)(1, 10) == (sum(cube))(1,10)
	
	//The type of sum4 is (Int => Int) => ( (Int, Int) => Int ) which simplifies to Int => Int => Int
	//because you associate to the right so it's equivalent to Int => (Int => Int)
	def sum4(f: Int => Int)(a: Int, b: Int): Int =
		if(a>b) 0
		else f(a) + sum4(f)(a+1, b)       //> sum4: (f: Int => Int)(a: Int, b: Int)Int
		
	def sumOfCubes4(a: Int, b: Int): Int = sum4(cube)(a,b)
                                                  //> sumOfCubes4: (a: Int, b: Int)Int
	
	//calculates the product of the values of afunction for the points on a given interval
	def product(f: Int => Int)(a: Int, b: Int): Int = {
		if(a>b) 1
		else f(a) * product(f)(a+1, b)
	}                                         //> product: (f: Int => Int)(a: Int, b: Int)Int
	
	def factFromProduct(n: Int):Int = product(id)(1,n)
                                                  //> factFromProduct: (n: Int)Int
	
	//generalizes both sum and product
	//z is base case or "zero"
	//Martin caslls this map reduce. f is "map", while g is "reduce"
	def binOperation(f: Int => Int)(g: (Int, Int) => Int, z: Int)(a: Int, b: Int): Int = {
		if(a>b) z
		else g(f(a), binOperation(f)(g, z)(a+1, b))
	}                                         //> binOperation: (f: Int => Int)(g: (Int, Int) => Int, z: Int)(a: Int, b: Int)
                                                  //| Int
	
	def sum5(f: Int => Int)(a: Int, b: Int): Int = binOperation(f)((x,y) => x+y, 0)(a,b)
                                                  //> sum5: (f: Int => Int)(a: Int, b: Int)Int
	def product2(f: Int => Int)(a: Int, b: Int): Int = binOperation(f)((x,y) => x*y, 1)(a,b)
                                                  //> product2: (f: Int => Int)(a: Int, b: Int)Int
	factFromProduct(9)                        //> res7: Int = 362880
	fact(9)                                   //> res8: Int = 362880
	sum(cube, 2,9)                            //> res9: Int = 2024
	sum2(cube, 2,9)                           //> res10: Int = 2024
	sum3(cube)(2,9)                           //> res11: Int = 2024
	sum4(cube)(2,9)                           //> res12: Int = 2024
	sum5(cube)(2,9)                           //> res13: Int = 2024
	product(cube)(2,9)                        //> res14: Int = 2036334592
	product2(cube)(2,9)                       //> res15: Int = 2036334592
	
	/* Another Example of Currying - also polymorphic functions */
	def printPair[A,B](a: A)(b: B): String = s"($a, $b)"
                                                  //> printPair: [A, B](a: A)(b: B)String
	def printHello[B](b: B) = printPair("Hello")(b)
                                                  //> printHello: [B](b: B)String
	printHello(1)                             //> res16: String = (Hello, 1)
	printHello(2)                             //> res17: String = (Hello, 2)
	printHello(3)                             //> res18: String = (Hello, 3)
	
	def email(username: String)(dn: String)(ext: String): String =
		s"$username@$dn.$ext"             //> email: (username: String)(dn: String)(ext: String)String
	def andrewEmail(dn: String)(ext: String) = email("andrew")(dn)(ext)
                                                  //> andrewEmail: (dn: String)(ext: String)String
	
	val google = ("google", "com")            //> google  : (String, String) = (google,com)
	val yahoo = ("yahoo", "com")              //> yahoo  : (String, String) = (yahoo,com)
	val looseleaf = ("looseleaf", "us")       //> looseleaf  : (String, String) = (looseleaf,us)
	
	val websites = List(google, yahoo, looseleaf)
                                                  //> websites  : List[(String, String)] = List((google,com), (yahoo,com), (loose
                                                  //| leaf,us))
	websites.map(x => andrewEmail(x._1)(x._2))//> res19: List[String] = List(andrew@google.com, andrew@yahoo.com, andrew@loos
                                                  //| eleaf.us)
	//without curry
	def fun(z: Int, x: Int): Int = z + x      //> fun: (z: Int, x: Int)Int
	List(1,2,3) map (x => fun(5, x))          //> res20: List[Int] = List(6, 7, 8)
	List(1,2,3) map (fun(5, _))               //> res21: List[Int] = List(6, 7, 8)
	
	//with curry
	def fun2(z: Int)(x: Int): Int = z + x     //> fun2: (z: Int)(x: Int)Int
	List(1,2,3) map fun2(5)                   //> res22: List[Int] = List(6, 7, 8)
}