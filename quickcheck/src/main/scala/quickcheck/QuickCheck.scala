package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {
  
  def consistentMin(h: H) : Boolean = {
    if (isEmpty(h)) true 
    else {
         val min1 = findMin(h)
         val h2 = deleteMin(h)
         if (isEmpty(h2)) true else min1 <= findMin(h2) && consistentMin(h2)
    }
  }
  
  def same(h1: H, h2: H) : Boolean = {
      if (isEmpty(h1) && isEmpty(h2)) true
      else if (isEmpty(h2) || isEmpty(h1)) false
      else findMin(h1) == findMin(h2) && same(deleteMin(h1), deleteMin(h2))
  }
  
  def min(a1: A, a2: A) : A = if (a1 < a2) a1 else a2
  
  def getMin(h: H) : A = {
    def doGet(h: H, minV: A) : A = {
      if (isEmpty(h)) minV else doGet(deleteMin(h), min(minV, findMin(h)))
    }
    
    doGet(h, findMin(h))
  }
   
  def getSize(h: H) : Int = {
    def doGet(h: H, s: Int) : Int = if (isEmpty(h)) s else doGet(deleteMin(h), s+1)
    doGet(h, 0)
  }
  
  def reCreateNewHeap(h: H) : H = {
    def doCreate(oldH: H, newH: H) : H = {
        if (isEmpty(oldH)) newH else doCreate(deleteMin(oldH), insert(findMin(oldH), newH))
    }
    
    doCreate(h, empty)
  }
  
  property("min1") = forAll { a: A =>
    findMin(insert(a, empty)) == a
  }
  
  property("min2") = forAll { (n1: A, n2: A) =>
  	findMin(insert(n1, insert(n2, empty))) == min(n1, n2)
  }
   
  property("min3 ") = forAll { (h1: H, h2: H) =>
     (!isEmpty(h1) && !isEmpty(h2)) ==> {
	     findMin(meld(h1, h2)) == min(findMin(h1), findMin(h2))
     }
  }
  
  property("should be sorted") = forAll { h: H =>
      consistentMin(h)
  }
    
  property("should be sorted with my heap generator") = forAll(genHeap) { h => 
      consistentMin(h)
  }
  
  property("should be sorted after merging 2 heaps") = forAll { (h1: H, h2: H) => 
      consistentMin(meld(h1, h2))
  }
    
  property("should be the same heap when merging a heap with itself") = forAll { h: H =>
    same(reCreateNewHeap(h), h)
  }
    
  property("empty") = forAll { a: A =>
      isEmpty(deleteMin(insert(a, empty)))
  }
  
  property("not empty") = forAll { a: A =>
    !isEmpty(insert(a, empty))
  }
   
  property("can have duplicates") = forAll { a: A =>
    val h = insert(a, insert(a, empty))
    findMin(h) == a
    getSize(h) == 2
    val h2 = deleteMin(h)
    getSize(deleteMin(h)) == 1
    isEmpty(deleteMin(h2))
  }
  
  property("meld can contain duplicated") = forAll { a: A =>
     val h = meld(insert(a, empty), insert(a, empty))
     getSize(h) == 2
     findMin(h) == a
  }
   
  property("meld distinct") = forAll { (a: A, b: A) =>
     findMin(meld(insert(b, empty), insert(a, empty))) == min(a, b)
  }
    
  property("merge 2") = forAll { h: H =>
     same(meld(h, empty), h)
  }
   
  property("empty") = forAll { (h: H, a: A) =>
     findMin(insert(a, h)) == getMin(insert(a, h))
  }
    
  property("check size") = forAll { (h1: H, h2: H) =>
     val sizeMeld = getSize(meld(h1, h2))
     sizeMeld >= getSize(h1) && sizeMeld >= getSize(h2)
  }
    
  def emptyHeap: Gen[H] = value(empty)
  def nonEmptyHeap : Gen[H] = for {n <- Arbitrary.arbitrary[A]
                                   h <- genHeap } yield insert(n, h)
                                   
  lazy val genHeap: Gen[H] = for {heap <- Gen.oneOf( emptyHeap, nonEmptyHeap)} yield heap

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)
}
