package week2

import common._

object parallelScan {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(97); val res$0 = 
  List(1,3,8).scanLeft(100)((s,x) => s + x);System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(44); val res$1 = 
  List(1,3,8).scanLeft(100)((s,x) => s + x);System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(247); 

	/** scanLeft sequential */
	def scanLeftSeq[A: Manifest](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A]) = {
		val len = inp.length
		var a = a0
		out(0) = a
		var i = 0
		while(i < len) {
			a = f(a, inp(i))
			i += 1
			out(i) = a
		}
	};System.out.println("""scanLeftSeq: [A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A])(implicit evidence$2: Manifest[A])Unit""");$skip(96); 

	def reduceSeg[A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => A): A = {
		???
	};System.out.println("""reduceSeg: [A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => A)A""");$skip(109); 
	def mapSeg[A, B](inp: Array[A], left: Int, right: Int, fi: (Int, A) => B, out: Array[B]): Unit = {
		???
	};System.out.println("""mapSeg: [A, B](inp: Array[A], left: Int, right: Int, fi: (Int, A) => B, out: Array[B])Unit""");$skip(254); 

	def scanLeftViaMapReduce[A](inp: Array[A], a0: A, f: (A,A) => A, out: Array[A]) = {
		val fi = (i: Int, v: A) => reduceSeg(inp, 0, i, a0, f)
		mapSeg(inp, 0, inp.length, fi, out)
		val last = inp.length - 1
		out(last + 1) = f(out(last), inp(last))
	};System.out.println("""scanLeftViaMapReduce: [A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A])Unit""");$skip(26); 
	
	val inp = Array(1,3,8);System.out.println("""inp  : Array[Int] = """ + $show(inp ));$skip(26); 
	val out = Array(0,0,0,0);System.out.println("""out  : Array[Int] = """ + $show(out ));$skip(27); 
	val out2 = Array(0,0,0,0);System.out.println("""out2  : Array[Int] = """ + $show(out2 ));$skip(27); 
	val out3 = Array(0,0,0,0);System.out.println("""out3  : Array[Int] = """ + $show(out3 ));$skip(27); 
	val out4 = Array(0,0,0,0);System.out.println("""out4  : Array[Int] = """ + $show(out4 ));$skip(35); 
	def f = (s: Int, x: Int) => s + x;System.out.println("""f: => (Int, Int) => Int""");$skip(35); 
	
	
	scanLeftSeq(inp, 100, f, out);$skip(5); val res$2 = 
	out;System.out.println("""res2: Array[Int] = """ + $show(res$2));$skip(872); 
	
	//scanLeftPar(inp, 100, 0, inp.length, f, out2)
	//out2
	
	/** scanLeft parallel */
	
	/* The following doesn't work
	 * Can scanLeft be made to have infinite parallelism? No.
	def scanLeftPar[A: Manifest](inp: Array[A], a0: A, left: Int, right: Int, f: (A, A) => A, out: Array[A]): Unit = {
		if(right-left < threshold) {
			scanLeftSeq(inp, a0, f, out)
		} else {
			val mid = left + (right - left) / 2
			val a0L = inp(0)
			val a0R = inp(mid)
			parallel(scanLeftPar(inp, a0L, 0, mid, f, out), scanLeftPar(inp, a0R, mid, inp.length, f, out))
		}
	}
	*/
	
	//A reduceRes function that transforms ScanTree into ScanTreeRes
	def reduceRes[A](t: ScanTree[A], f: (A, A) => A): ScanTreeRes[A] = t match {
		case ScanLeaf(v) => ScanLeafRes(v)
		case ScanNode(l, r) => {
			val (tL, tR) = (reduceRes(l, f), reduceRes(r, f))
			ScanNodeRes(tL, f(tL.res, tR.res), tR)
		}
	};System.out.println("""reduceRes: [A](t: week2.ScanTree[A], f: (A, A) => A)week2.ScanTreeRes[A]""");$skip(94); 
	
	val t1 = ScanNode(ScanNode(ScanLeaf(1), ScanLeaf(3)), ScanNode(ScanLeaf(8), ScanLeaf(50)));System.out.println("""t1  : week2.ScanNode[Int] = """ + $show(t1 ));$skip(39); 
  val plus = (x: Int, y: Int) => x + y;System.out.println("""plus  : (Int, Int) => Int = """ + $show(plus ));$skip(22); val res$3 = 
  reduceRes(t1, plus);System.out.println("""res3: week2.ScanTreeRes[Int] = """ + $show(res$3));$skip(310); 
	/* Parallel reduce that preserves the computation tree
	 *
	 */
	def upsweep[A](t: ScanTree[A], f: (A,A) => A): ScanTreeRes[A] = t match {
		case ScanLeaf(v) => ScanLeafRes(v)
		case ScanNode(l,r) => {
			val (tL, tR) = parallel(upsweep(l, f), upsweep(r,  f))
			ScanNodeRes(tL, f(tL.res, tR.res), tR)
		}
	};System.out.println("""upsweep: [A](t: week2.ScanTree[A], f: (A, A) => A)week2.ScanTreeRes[A]""");$skip(325); 


	//'a0' is reduce of all elements left of the tree 't'
	def downsweep[A](t: ScanTreeRes[A], a0: A, f: (A, A) => A): ScanTree[A] = t match {
		case ScanLeafRes(a) => ScanLeaf(f(a0, a))
		case ScanNodeRes(l, _, r) => {
			val (tL, tR) = parallel(downsweep(l, a0, f), downsweep(r, f(a0, l.res), f))
			ScanNode(tL, tR)
		}
	};System.out.println("""downsweep: [A](t: week2.ScanTreeRes[A], a0: A, f: (A, A) => A)week2.ScanTree[A]""");$skip(180); 

  def prepend[A](x: A, t: ScanTree[A]): ScanTree[A] = t match {
  	case ScanLeaf(v) => ScanNode(ScanLeaf(x), ScanLeaf(v))
  	case ScanNode(l, r) => ScanNode(prepend(x, l), r)
  };System.out.println("""prepend: [A](x: A, t: week2.ScanTree[A])week2.ScanTree[A]""");$skip(173); 
  
  def scanLeftPar1[A](t: ScanTree[A], a0: A, f: (A, A) => A): ScanTree[A] = {
  	val tRes = upsweep(t, f)
  	val scan1 = downsweep(tRes, a0, f)
  	prepend(a0, scan1)
  };System.out.println("""scanLeftPar1: [A](t: week2.ScanTree[A], a0: A, f: (A, A) => A)week2.ScanTree[A]""");$skip(17); val res$4 = 
  
  //Test
  t1;System.out.println("""res4: week2.ScanNode[Int] = """ + $show(res$4));$skip(19); val res$5 = 
	upsweep(t1, plus);System.out.println("""res5: week2.ScanTreeRes[Int] = """ + $show(res$5));$skip(41); val res$6 = 
	downsweep(upsweep(t1, plus), 100, plus);System.out.println("""res6: week2.ScanTree[Int] = """ + $show(res$6));$skip(30); val res$7 = 
  scanLeftPar1(t1, 100, plus);System.out.println("""res7: week2.ScanTree[Int] = """ + $show(res$7));$skip(290); 
   
	
	/** Implementation using Array */
	def scanLeftSeg[A](inp: Array[A], left: Int, right: Int,
										 a0: A, f: (A, A) => A, out: Array[A]) = {
 		if(left < right) {
			var i = left
			var a = a0
 			while(i < right) {
 				a = f(a, inp(i))
				i += 1
				out(i) = a
 			}
 		}
 	};System.out.println("""scanLeftSeg: [A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => A, out: Array[A])Unit""");$skip(176); 
	def reduceSeg1[A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => A): A = {
		var a = a0
		var i = left
		while(i < right){
			a = f(a, inp(i))
			i += 1
		}
		a
	};System.out.println("""reduceSeg1: [A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => A)A""");$skip(19); 
	val threshold = 2;System.out.println("""threshold  : Int = """ + $show(threshold ));$skip(360); 
	
	def upsweep2[A](inp: Array[A], from: Int, to: Int, f: (A,A) => A): ScanTreeRes[A] = {
		if(to - from < threshold)
			ScanLeaf2(from, to, reduceSeg1(inp, from + 1, to, inp(from), f))
		else {
			val mid = from + (to - from) / 2
			val (tL, tR) = parallel(upsweep2(inp, from, mid, f), upsweep2(inp, mid, to, f))
			ScanNode2(tL, f(tL.res, tR.res), tR)
		}
	};System.out.println("""upsweep2: [A](inp: Array[A], from: Int, to: Int, f: (A, A) => A)week2.ScanTreeRes[A]""");$skip(332); 
	
	def downsweep2[A](inp: Array[A], a0: A,
										f: (A, A) => A, t: ScanTreeRes[A], out: Array[A]): Unit = t match {
		case ScanLeaf2(from, to, res) => scanLeftSeg(inp, from, to, a0, f, out)
		case ScanNode2(l, _, r) => {
			val (_, _) = parallel(downsweep2(inp, a0, f, l, out), downsweep2(inp, f(a0, l.res), f, r, out))
		}
	};System.out.println("""downsweep2: [A](inp: Array[A], a0: A, f: (A, A) => A, t: week2.ScanTreeRes[A], out: Array[A])Unit""");$skip(186); 

	def scanLeftPar2[A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A]) = {
		val t = upsweep2(inp, 0, inp.length, f)
		downsweep2(inp, a0, f, t, out)
		out(0) = a0 // prepends a0
	};System.out.println("""scanLeftPar2: [A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A])Unit""");$skip(53); val res$8 = 
	//Test parallel
	upsweep2(inp, 0, inp.length, plus);System.out.println("""res8: week2.ScanTreeRes[Int] = """ + $show(res$8));$skip(70); 
	downsweep2(inp, 100, plus, upsweep2(inp, 0, inp.length, plus), out2);$skip(7); val res$9 = 
  out2;System.out.println("""res9: Array[Int] = """ + $show(res$9));$skip(37); 
  scanLeftPar2(inp, 100, plus, out3);$skip(7); val res$10 = 
  out3;System.out.println("""res10: Array[Int] = """ + $show(res$10));$skip(36); 
  
	var inp2 = (0 until 20).toArray;System.out.println("""inp2  : Array[Int] = """ + $show(inp2 ));$skip(41); 
      
  val out5 = (0 until 21).toArray;System.out.println("""out5  : Array[Int] = """ + $show(out5 ));$skip(34); 
  val out6 = (0 until 21).toArray;System.out.println("""out6  : Array[Int] = """ + $show(out6 ));$skip(55); 
  
  
  val up = upsweep2(inp2 , 0, inp2.length, plus);System.out.println("""up  : week2.ScanTreeRes[Int] = """ + $show(up ));$skip(43); 
	
	
	downsweep2(inp2, 100, plus, up, out5);$skip(7); val res$11 = 
  out5;System.out.println("""res11: Array[Int] = """ + $show(res$11));$skip(38); 
  scanLeftPar2(inp2, 100, plus, out5);$skip(7); val res$12 = 
  out5;System.out.println("""res12: Array[Int] = """ + $show(res$12));$skip(37); 
  scanLeftSeq(inp2, 100, plus, out6);$skip(6); val res$13 = 
	out6;System.out.println("""res13: Array[Int] = """ + $show(res$13));$skip(33); val res$14 = 
  
  
  inp2.scanLeft(100)(plus);System.out.println("""res14: Array[Int] = """ + $show(res$14))}
  
	
}

/** Trees storing our input collection only have values in leaves */
sealed abstract class ScanTree[A] {
	def apply(a: A) = new ScanLeaf(a)

	def apply(l: ScanTree[A], r: ScanTree[A]) = new ScanNode(l, r)
}
case class ScanLeaf[A](a: A) extends ScanTree[A]
case class ScanNode[A](l: ScanTree[A], r: ScanTree[A]) extends ScanTree[A]

/** Trees storing intermediate values also have res values in nodes */
//It's like the huffman encoding
sealed abstract class ScanTreeRes[A] {
	val res: A
	def apply[A](l: ScanTreeRes[A], res: A, r: ScanTreeRes[A]): ScanTreeRes[A] = {
		new ScanNodeRes(l, res, r)
	}
	def apply(from: Int, to: Int, res: A) = new ScanLeaf2(from, to, res)
	def apply(l: ScanTreeRes[A], res: A, r: ScanTreeRes[A]) = new ScanNode2(l, res, r)
	def apply[A](res: A): ScanTreeRes[A] = {
		new ScanLeafRes(res)
	}
}
case class ScanLeafRes[A](override val res: A) extends ScanTreeRes[A]
case class ScanNodeRes[A](l: ScanTreeRes[A],
													override val res: A, r: ScanTreeRes[A]) extends ScanTreeRes[A]
													
//immediate tree for array reduce
case class ScanLeaf2[A](from: Int,
												to: Int, override val res: A) extends ScanTreeRes[A]
case class ScanNode2[A](l: ScanTreeRes[A],
												override val res: A, r: ScanTreeRes[A]) extends ScanTreeRes[A]
													