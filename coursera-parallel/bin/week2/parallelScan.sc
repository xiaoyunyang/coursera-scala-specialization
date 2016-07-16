package week2

import common._

object parallelScan {
  List(1,3,8).scanLeft(100)((s,x) => s + x)       //> res0: List[Int] = List(100, 101, 104, 112)
  List(1,3,8).scanLeft(100)((s,x) => s + x)       //> res1: List[Int] = List(100, 101, 104, 112)

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
	}                                         //> scanLeftSeq: [A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A])(implic
                                                  //| it evidence$2: Manifest[A])Unit

	def reduceSeg[A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => A): A = {
		???
	}                                         //> reduceSeg: [A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => A)A
                                                  //| 
	def mapSeg[A, B](inp: Array[A], left: Int, right: Int, fi: (Int, A) => B, out: Array[B]): Unit = {
		???
	}                                         //> mapSeg: [A, B](inp: Array[A], left: Int, right: Int, fi: (Int, A) => B, out:
                                                  //|  Array[B])Unit

	def scanLeftViaMapReduce[A](inp: Array[A], a0: A, f: (A,A) => A, out: Array[A]) = {
		val fi = (i: Int, v: A) => reduceSeg(inp, 0, i, a0, f)
		mapSeg(inp, 0, inp.length, fi, out)
		val last = inp.length - 1
		out(last + 1) = f(out(last), inp(last))
	}                                         //> scanLeftViaMapReduce: [A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A
                                                  //| ])Unit
	
	val inp = Array(1,3,8)                    //> inp  : Array[Int] = Array(1, 3, 8)
	val out = Array(0,0,0,0)                  //> out  : Array[Int] = Array(0, 0, 0, 0)
	val out2 = Array(0,0,0,0)                 //> out2  : Array[Int] = Array(0, 0, 0, 0)
	val out3 = Array(0,0,0,0)                 //> out3  : Array[Int] = Array(0, 0, 0, 0)
	val out4 = Array(0,0,0,0)                 //> out4  : Array[Int] = Array(0, 0, 0, 0)
	def f = (s: Int, x: Int) => s + x         //> f: => (Int, Int) => Int
	
	
	scanLeftSeq(inp, 100, f, out)
	out                                       //> res2: Array[Int] = Array(100, 101, 104, 112)
	
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
	}                                         //> reduceRes: [A](t: week2.ScanTree[A], f: (A, A) => A)week2.ScanTreeRes[A]
	
	val t1 = ScanNode(ScanNode(ScanLeaf(1), ScanLeaf(3)), ScanNode(ScanLeaf(8), ScanLeaf(50)))
                                                  //> t1  : week2.ScanNode[Int] = ScanNode(ScanNode(ScanLeaf(1),ScanLeaf(3)),Scan
                                                  //| Node(ScanLeaf(8),ScanLeaf(50)))
  val plus = (x: Int, y: Int) => x + y            //> plus  : (Int, Int) => Int = <function2>
  reduceRes(t1, plus)                             //> res3: week2.ScanTreeRes[Int] = ScanNodeRes(ScanNodeRes(ScanLeafRes(1),4,Sca
                                                  //| nLeafRes(3)),62,ScanNodeRes(ScanLeafRes(8),58,ScanLeafRes(50)))
	/* Parallel reduce that preserves the computation tree
	 *
	 */
	def upsweep[A](t: ScanTree[A], f: (A,A) => A): ScanTreeRes[A] = t match {
		case ScanLeaf(v) => ScanLeafRes(v)
		case ScanNode(l,r) => {
			val (tL, tR) = parallel(upsweep(l, f), upsweep(r,  f))
			ScanNodeRes(tL, f(tL.res, tR.res), tR)
		}
	}                                         //> upsweep: [A](t: week2.ScanTree[A], f: (A, A) => A)week2.ScanTreeRes[A]


	//'a0' is reduce of all elements left of the tree 't'
	def downsweep[A](t: ScanTreeRes[A], a0: A, f: (A, A) => A): ScanTree[A] = t match {
		case ScanLeafRes(a) => ScanLeaf(f(a0, a))
		case ScanNodeRes(l, _, r) => {
			val (tL, tR) = parallel(downsweep(l, a0, f), downsweep(r, f(a0, l.res), f))
			ScanNode(tL, tR)
		}
	}                                         //> downsweep: [A](t: week2.ScanTreeRes[A], a0: A, f: (A, A) => A)week2.ScanTre
                                                  //| e[A]

  def prepend[A](x: A, t: ScanTree[A]): ScanTree[A] = t match {
  	case ScanLeaf(v) => ScanNode(ScanLeaf(x), ScanLeaf(v))
  	case ScanNode(l, r) => ScanNode(prepend(x, l), r)
  }                                               //> prepend: [A](x: A, t: week2.ScanTree[A])week2.ScanTree[A]
  
  def scanLeftPar1[A](t: ScanTree[A], a0: A, f: (A, A) => A): ScanTree[A] = {
  	val tRes = upsweep(t, f)
  	val scan1 = downsweep(tRes, a0, f)
  	prepend(a0, scan1)
  }                                               //> scanLeftPar1: [A](t: week2.ScanTree[A], a0: A, f: (A, A) => A)week2.ScanTre
                                                  //| e[A]
  
  //Test
  t1                                              //> res4: week2.ScanNode[Int] = ScanNode(ScanNode(ScanLeaf(1),ScanLeaf(3)),Scan
                                                  //| Node(ScanLeaf(8),ScanLeaf(50)))
	upsweep(t1, plus)                         //> res5: week2.ScanTreeRes[Int] = ScanNodeRes(ScanNodeRes(ScanLeafRes(1),4,Sca
                                                  //| nLeafRes(3)),62,ScanNodeRes(ScanLeafRes(8),58,ScanLeafRes(50)))
	downsweep(upsweep(t1, plus), 100, plus)   //> res6: week2.ScanTree[Int] = ScanNode(ScanNode(ScanLeaf(101),ScanLeaf(104)),
                                                  //| ScanNode(ScanLeaf(112),ScanLeaf(162)))
  scanLeftPar1(t1, 100, plus)                     //> res7: week2.ScanTree[Int] = ScanNode(ScanNode(ScanNode(ScanLeaf(100),ScanLe
                                                  //| af(101)),ScanLeaf(104)),ScanNode(ScanLeaf(112),ScanLeaf(162)))
   
	
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
 	}                                         //> scanLeftSeg: [A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => 
                                                  //| A, out: Array[A])Unit
	def reduceSeg1[A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => A): A = {
		var a = a0
		var i = left
		while(i < right){
			a = f(a, inp(i))
			i += 1
		}
		a
	}                                         //> reduceSeg1: [A](inp: Array[A], left: Int, right: Int, a0: A, f: (A, A) => A
                                                  //| )A
	val threshold = 2                         //> threshold  : Int = 2
	
	def upsweep2[A](inp: Array[A], from: Int, to: Int, f: (A,A) => A): ScanTreeRes[A] = {
		if(to - from < threshold)
			ScanLeaf2(from, to, reduceSeg1(inp, from + 1, to, inp(from), f))
		else {
			val mid = from + (to - from) / 2
			val (tL, tR) = parallel(upsweep2(inp, from, mid, f), upsweep2(inp, mid, to, f))
			ScanNode2(tL, f(tL.res, tR.res), tR)
		}
	}                                         //> upsweep2: [A](inp: Array[A], from: Int, to: Int, f: (A, A) => A)week2.ScanT
                                                  //| reeRes[A]
	
	def downsweep2[A](inp: Array[A], a0: A,
										f: (A, A) => A, t: ScanTreeRes[A], out: Array[A]): Unit = t match {
		case ScanLeaf2(from, to, res) => scanLeftSeg(inp, from, to, a0, f, out)
		case ScanNode2(l, _, r) => {
			val (_, _) = parallel(downsweep2(inp, a0, f, l, out), downsweep2(inp, f(a0, l.res), f, r, out))
		}
	}                                         //> downsweep2: [A](inp: Array[A], a0: A, f: (A, A) => A, t: week2.ScanTreeRes[
                                                  //| A], out: Array[A])Unit

	def scanLeftPar2[A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A]) = {
		val t = upsweep2(inp, 0, inp.length, f)
		downsweep2(inp, a0, f, t, out)
		out(0) = a0 // prepends a0
	}                                         //> scanLeftPar2: [A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A])Unit
	//Test parallel
	upsweep2(inp, 0, inp.length, plus)        //> res8: week2.ScanTreeRes[Int] = ScanNode2(ScanLeaf2(0,1,1),12,ScanNode2(Scan
                                                  //| Leaf2(1,2,3),11,ScanLeaf2(2,3,8)))
	downsweep2(inp, 100, plus, upsweep2(inp, 0, inp.length, plus), out2)
  out2                                            //> res9: Array[Int] = Array(0, 101, 104, 112)
  scanLeftPar2(inp, 100, plus, out3)
  out3                                            //> res10: Array[Int] = Array(100, 101, 104, 112)
  
	var inp2 = (0 until 20).toArray           //> inp2  : Array[Int] = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
                                                  //| , 15, 16, 17, 18, 19)
      
  val out5 = (0 until 21).toArray                 //> out5  : Array[Int] = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
                                                  //| , 15, 16, 17, 18, 19, 20)
  val out6 = (0 until 21).toArray                 //> out6  : Array[Int] = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
                                                  //| , 15, 16, 17, 18, 19, 20)
  
  
  val up = upsweep2(inp2 , 0, inp2.length, plus)  //> up  : week2.ScanTreeRes[Int] = ScanNode2(ScanNode2(ScanNode2(ScanNode2(Scan
                                                  //| Leaf2(0,1,0),1,ScanLeaf2(1,2,1)),10,ScanNode2(ScanLeaf2(2,3,2),9,ScanNode2(
                                                  //| ScanLeaf2(3,4,3),7,ScanLeaf2(4,5,4)))),45,ScanNode2(ScanNode2(ScanLeaf2(5,6
                                                  //| ,5),11,ScanLeaf2(6,7,6)),35,ScanNode2(ScanLeaf2(7,8,7),24,ScanNode2(ScanLea
                                                  //| f2(8,9,8),17,ScanLeaf2(9,10,9))))),190,ScanNode2(ScanNode2(ScanNode2(ScanLe
                                                  //| af2(10,11,10),21,ScanLeaf2(11,12,11)),60,ScanNode2(ScanLeaf2(12,13,12),39,S
                                                  //| canNode2(ScanLeaf2(13,14,13),27,ScanLeaf2(14,15,14)))),145,ScanNode2(ScanNo
                                                  //| de2(ScanLeaf2(15,16,15),31,ScanLeaf2(16,17,16)),85,ScanNode2(ScanLeaf2(17,1
                                                  //| 8,17),54,ScanNode2(ScanLeaf2(18,19,18),37,ScanLeaf2(19,20,19))))))
	
	
	downsweep2(inp2, 100, plus, up, out5)
  out5                                            //> res11: Array[Int] = Array(0, 100, 101, 103, 106, 110, 115, 121, 128, 136, 1
                                                  //| 45, 155, 166, 178, 191, 205, 220, 236, 253, 271, 290)
  scanLeftPar2(inp2, 100, plus, out5)
  out5                                            //> res12: Array[Int] = Array(100, 100, 101, 103, 106, 110, 115, 121, 128, 136,
                                                  //|  145, 155, 166, 178, 191, 205, 220, 236, 253, 271, 290)
  scanLeftSeq(inp2, 100, plus, out6)
	out6                                      //> res13: Array[Int] = Array(100, 100, 101, 103, 106, 110, 115, 121, 128, 136,
                                                  //|  145, 155, 166, 178, 191, 205, 220, 236, 253, 271, 290)
  
  
  inp2.scanLeft(100)(plus)                        //> res14: Array[Int] = Array(100, 100, 101, 103, 106, 110, 115, 121, 128, 136,
                                                  //|  145, 155, 166, 178, 191, 205, 220, 236, 253, 271, 290)
  
	
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
													