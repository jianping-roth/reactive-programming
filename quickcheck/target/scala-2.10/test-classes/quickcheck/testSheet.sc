package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

object testSheet {
  alphaStr
  def n = Choose.chooseLong.choose(1, 100)
  chooseNum(1, 100)
  alphaLowerChar
  choose(97,122) map (it => it.toChar )
 
  alphaLowerChar.label
  choose(1, 5)
  listOf()
  val isEmpty = Gen.oneOf(true, false)
  
  lazy val genHeap: Gen[H] = ???

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)
  
  for {isEmpty <- Gen.oneOf(true, false)
       heap <- arbHeap} yield
}