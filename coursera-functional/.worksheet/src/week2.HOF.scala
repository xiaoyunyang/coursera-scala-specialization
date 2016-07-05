package week2

object HOF {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(146); 
	//take the sum of integers between a and b
	def sumIntsNaive(a: Int,b: Int): Int = if(a>b) 0 else a + sumInts(a+1, b);System.out.println("""sumIntsNaive: (a: Int, b: Int)Int""");$skip(90); 
	
	def sumCubesNaive(a: Int, b: Int): Int =
		if(a > b) 0 else cube(a) + sumCubes(a+1, b);System.out.println("""sumCubesNaive: (a: Int, b: Int)Int""");$skip(81); 
	
	/*** Higher Older functions ***/
	def sumInts(a: Int, b: Int) = sum(id, a, b);System.out.println("""sumInts: (a: Int, b: Int)Int""");$skip(48); 
	def sumCubes(a: Int, b: Int) = sum(cube, a, b);System.out.println("""sumCubes: (a: Int, b: Int)Int""");$skip(53); 
	def sumFactorials(a: Int, b: Int) = sum(fact, a, b);System.out.println("""sumFactorials: (a: Int, b: Int)Int""");$skip(36); 
	
	//where
	def id(x: Int): Int = x;System.out.println("""id: (x: Int)Int""");$skip(31); 
	def cube(x: Int): Int = x*x*x;System.out.println("""cube: (x: Int)Int""");$skip(53); 
	def fact(x: Int): Int = if(x==0) 1 else x*fact(x-1);System.out.println("""fact: (x: Int)Int""");$skip(96); 
  def sum(f: Int => Int, a: Int, b: Int): Int = {
		if(a > b) 0
		else f(a) + sum(f, a+1, b)
	};System.out.println("""sum: (f: Int => Int, a: Int, b: Int)Int""");$skip(56); val res$0 = 
	
	/*** Anonymous Functions ***/
	(x: Int) => x * x * x;System.out.println("""res0: Int => Int = """ + $show(res$0));$skip(132); 
	//Here, (x: Int) is the parameter of the function and x * x * x is its body
	
	def sumIntsAnon(a: Int, b: Int) = sum(x => x, a, b);System.out.println("""sumIntsAnon: (a: Int, b: Int)Int""");$skip(58); 
	def sumCubesAnon(a: Int, b: Int) = sum(x => x*x*x, a, b);System.out.println("""sumCubesAnon: (a: Int, b: Int)Int""");$skip(155); 
	
	
	def sum2(f: Int => Int, a: Int, b: Int): Int = {
		def loop(a: Int, acc: Int): Int = {
			if(a>b) acc
			else loop(a+1, f(a)+acc)
		}
		loop(a, 0)
	};System.out.println("""sum2: (f: Int => Int, a: Int, b: Int)Int""");$skip(17); val res$1 = 
	
	sum(id, 1, 4);System.out.println("""res1: Int = """ + $show(res$1));$skip(16); val res$2 = 
	sum2(id, 1, 4);System.out.println("""res2: Int = """ + $show(res$2));$skip(17); val res$3 = 
	sum(id, 13, 40);System.out.println("""res3: Int = """ + $show(res$3));$skip(18); val res$4 = 
	sum2(id, 13, 40);System.out.println("""res4: Int = """ + $show(res$4));$skip(196); 
	
	/*** Currying ***/
	//special form for writing HOFs
	def sum3(f: Int => Int): (Int, Int) => Int = {
		def sumF(a: Int, b: Int): Int = {
			if(a > b) 0
			else f(a) + sumF(a+1, b)
		}
		sumF
	};System.out.println("""sum3: (f: Int => Int)(Int, Int) => Int""");$skip(104); 
	def fact3: Int => Int = {
		def factF(x: Int): Int = {
			if(x==0) 1
			else x*factF(x)
		}
		factF
	};System.out.println("""fact3: => Int => Int""");$skip(29); 
	def sumInts3 = sum3(x => x);System.out.println("""sumInts3: => (Int, Int) => Int""");$skip(35); 
	def sumCubes3 =  sum3(x => x*x*x);System.out.println("""sumCubes3: => (Int, Int) => Int""");$skip(34); 
	def sumFactorials3 = sum3(fact3);System.out.println("""sumFactorials3: => (Int, Int) => Int""");$skip(176); val res$5 = 
	
	//sum3(cube) is equivalent to sumCubes, which returns (Int, Int) => Int.
	//We can apply sum3(cube) onto (1,10) just as we can apply sumCubes3 onto (1,10)
	sum3(cube)(1,10);System.out.println("""res5: Int = """ + $show(res$5));$skip(17); val res$6 = 
	sumCubes3(1,10);System.out.println("""res6: Int = """ + $show(res$6));$skip(384); 
	
	//This demonstrates that function application associates to the left
	//sum(cube)(1, 10) == (sum(cube))(1,10)
	
	//The type of sum4 is (Int => Int) => ( (Int, Int) => Int ) which simplifies to Int => Int => Int
	//because you associate to the right so it's equivalent to Int => (Int => Int)
	def sum4(f: Int => Int)(a: Int, b: Int): Int =
		if(a>b) 0
		else f(a) + sum4(f)(a+1, b);System.out.println("""sum4: (f: Int => Int)(a: Int, b: Int)Int""");$skip(59); 
		
	def sumOfCubes4(a: Int, b: Int): Int = sum4(cube)(a,b);System.out.println("""sumOfCubes4: (a: Int, b: Int)Int""");$skip(191); 
	
	//calculates the product of the values of afunction for the points on a given interval
	def product(f: Int => Int)(a: Int, b: Int): Int = {
		if(a>b) 1
		else f(a) * product(f)(a+1, b)
	};System.out.println("""product: (f: Int => Int)(a: Int, b: Int)Int""");$skip(54); 
	
	def factFromProduct(n: Int):Int = product(id)(1,n);System.out.println("""factFromProduct: (n: Int)Int""");$skip(281); 
	
	//generalizes both sum and product
	//z is base case or "zero"
	//Martin caslls this map reduce. f is "map", while g is "reduce"
	def binOperation(f: Int => Int)(g: (Int, Int) => Int, z: Int)(a: Int, b: Int): Int = {
		if(a>b) z
		else g(f(a), binOperation(f)(g, z)(a+1, b))
	};System.out.println("""binOperation: (f: Int => Int)(g: (Int, Int) => Int, z: Int)(a: Int, b: Int)Int""");$skip(88); 
	
	def sum5(f: Int => Int)(a: Int, b: Int): Int = binOperation(f)((x,y) => x+y, 0)(a,b);System.out.println("""sum5: (f: Int => Int)(a: Int, b: Int)Int""");$skip(90); 
	def product2(f: Int => Int)(a: Int, b: Int): Int = binOperation(f)((x,y) => x*y, 1)(a,b);System.out.println("""product2: (f: Int => Int)(a: Int, b: Int)Int""");$skip(20); val res$7 = 
	factFromProduct(9);System.out.println("""res7: Int = """ + $show(res$7));$skip(9); val res$8 = 
	fact(9);System.out.println("""res8: Int = """ + $show(res$8));$skip(16); val res$9 = 
	sum(cube, 2,9);System.out.println("""res9: Int = """ + $show(res$9));$skip(17); val res$10 = 
	sum2(cube, 2,9);System.out.println("""res10: Int = """ + $show(res$10));$skip(17); val res$11 = 
	sum3(cube)(2,9);System.out.println("""res11: Int = """ + $show(res$11));$skip(17); val res$12 = 
	sum4(cube)(2,9);System.out.println("""res12: Int = """ + $show(res$12));$skip(17); val res$13 = 
	sum5(cube)(2,9);System.out.println("""res13: Int = """ + $show(res$13));$skip(20); val res$14 = 
	product(cube)(2,9);System.out.println("""res14: Int = """ + $show(res$14));$skip(21); val res$15 = 
	product2(cube)(2,9);System.out.println("""res15: Int = """ + $show(res$15));$skip(120); 
	
	/* Another Example of Currying - also polymorphic functions */
	def printPair[A,B](a: A)(b: B): String = s"($a, $b)";System.out.println("""printPair: [A, B](a: A)(b: B)String""");$skip(49); 
	def printHello[B](b: B) = printPair("Hello")(b);System.out.println("""printHello: [B](b: B)String""");$skip(15); val res$16 = 
	printHello(1);System.out.println("""res16: String = """ + $show(res$16));$skip(15); val res$17 = 
	printHello(2);System.out.println("""res17: String = """ + $show(res$17));$skip(15); val res$18 = 
	printHello(3);System.out.println("""res18: String = """ + $show(res$18));$skip(90); 
	
	def email(username: String)(dn: String)(ext: String): String =
		s"$username@$dn.$ext";System.out.println("""email: (username: String)(dn: String)(ext: String)String""");$skip(69); 
	def andrewEmail(dn: String)(ext: String) = email("andrew")(dn)(ext);System.out.println("""andrewEmail: (dn: String)(ext: String)String""");$skip(34); 
	
	val google = ("google", "com");System.out.println("""google  : (String, String) = """ + $show(google ));$skip(30); 
	val yahoo = ("yahoo", "com");System.out.println("""yahoo  : (String, String) = """ + $show(yahoo ));$skip(37); 
	val looseleaf = ("looseleaf", "us");System.out.println("""looseleaf  : (String, String) = """ + $show(looseleaf ));$skip(49); 
	
	val websites = List(google, yahoo, looseleaf);System.out.println("""websites  : List[(String, String)] = """ + $show(websites ));$skip(44); val res$19 = 
	websites.map(x => andrewEmail(x._1)(x._2));System.out.println("""res19: List[String] = """ + $show(res$19));$skip(55); 
	//without curry
	def fun(z: Int, x: Int): Int = z + x;System.out.println("""fun: (z: Int, x: Int)Int""");$skip(34); val res$20 = 
	List(1,2,3) map (x => fun(5, x));System.out.println("""res20: List[Int] = """ + $show(res$20));$skip(29); val res$21 = 
	List(1,2,3) map (fun(5, _));System.out.println("""res21: List[Int] = """ + $show(res$21));$skip(55); 
	
	//with curry
	def fun2(z: Int)(x: Int): Int = z + x;System.out.println("""fun2: (z: Int)(x: Int)Int""");$skip(25); val res$22 = 
	List(1,2,3) map fun2(5);System.out.println("""res22: List[Int] = """ + $show(res$22))}
}
