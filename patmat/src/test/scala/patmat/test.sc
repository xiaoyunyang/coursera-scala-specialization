package patmat
import Huffman._
object test {

  val chars = List('a', 'a', 'b', 'a', 'a', 'c', 'b','4')
                                                  //> chars  : List[Char] = List(a, a, b, a, a, c, b, 4)
 	times(chars)                              //> res0: List[(Char, Int)] = List((a,4), (b,2), (c,1), (4,1))
  
  val m1 = times(string2Chars("Massachusetts"))   //> m1  : List[(Char, Int)] = List((M,1), (a,2), (s,4), (c,1), (h,1), (u,1), (e,
                                                  //| 1), (t,2))
  
  val m2 = times(string2Chars("Mississippi"))     //> m2  : List[(Char, Int)] = List((M,1), (i,4), (s,4), (p,2))
  val m3 = times(string2Chars("Mississpp"))       //> m3  : List[(Char, Int)] = List((M,1), (i,2), (s,4), (p,2))
                                                  
  val orderedM1 = makeOrderedLeafList(m1)         //> orderedM1  : List[patmat.Huffman.Leaf] = List(Leaf(M,1), Leaf(c,1), Leaf(e,1
                                                  //| ), Leaf(h,1), Leaf(u,1), Leaf(a,2), Leaf(t,2), Leaf(s,4))
  val orderedM2 = makeOrderedLeafList(m2)         //> orderedM2  : List[patmat.Huffman.Leaf] = List(Leaf(M,1), Leaf(p,2), Leaf(i,4
                                                  //| ), Leaf(s,4))
  val orderedM3 = makeOrderedLeafList(m3)         //> orderedM3  : List[patmat.Huffman.Leaf] = List(Leaf(M,1), Leaf(i,2), Leaf(p,2
                                                  //| ), Leaf(s,4))
  singleton(makeOrderedLeafList(times(string2Chars("ab"))))
                                                  //> res1: Boolean = false
  singleton(makeOrderedLeafList(times(string2Chars("a"))))
                                                  //> res2: Boolean = true
  singleton(makeOrderedLeafList(times(string2Chars("aaa"))))
                                                  //> res3: Boolean = true
                                                  
  combine(orderedM1)                              //> res4: List[patmat.Huffman.CodeTree] = List(Leaf(e,1), Leaf(h,1), Leaf(u,1), 
                                                  //| Fork(Leaf(M,1),Leaf(c,1),List(M, c),2), Leaf(a,2), Leaf(t,2), Leaf(s,4))
  combine(orderedM2)                              //> res5: List[patmat.Huffman.CodeTree] = List(Fork(Leaf(M,1),Leaf(p,2),List(M, 
                                                  //| p),3), Leaf(i,4), Leaf(s,4))
  combine(orderedM3)                              //> res6: List[patmat.Huffman.CodeTree] = List(Leaf(p,2), Fork(Leaf(M,1),Leaf(i,
                                                  //| 2),List(M, i),3), Leaf(s,4))
   
    
    
    
  createCodeTree(string2Chars("Massachusetts"))   //> res7: patmat.Huffman.CodeTree = Fork(Fork(Leaf(t,2),Fork(Leaf(u,1),Fork(Leaf
                                                  //| (e,1),Leaf(h,1),List(e, h),2),List(u, e, h),3),List(t, u, e, h),5),Fork(Fork
                                                  //| (Fork(Leaf(M,1),Leaf(c,1),List(M, c),2),Leaf(a,2),List(M, c, a),4),Leaf(s,4)
                                                  //| ,List(M, c, a, s),8),List(t, u, e, h, M, c, a, s),13)
  decode(frenchCode, secret)                      //> res8: List[Char] = List(h, u, f, f, m, a, n, e, s, t, c, o, o, l)
  encode(frenchCode)(decode(frenchCode, secret))  //> res9: List[patmat.Huffman.Bit] = List(0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0,
                                                  //|  1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 
                                                  //| 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1)
  secret                                          //> res10: List[patmat.Huffman.Bit] = List(0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0
                                                  //| , 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1,
                                                  //|  0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1)
           
      
}