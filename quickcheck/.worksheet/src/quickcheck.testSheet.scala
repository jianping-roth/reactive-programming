package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

object testSheet {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(137); val res$0 = 
  alphaStr;System.out.println("""res0: org.scalacheck.Gen[String] = """ + $show(res$0));$skip(43); 
  def n = Choose.chooseLong.choose(1, 100);System.out.println("""n: => org.scalacheck.Gen[Long]""");$skip(20); val res$1 = 
  chooseNum(1, 100);System.out.println("""res1: org.scalacheck.Gen[Int] = """ + $show(res$1));$skip(17); val res$2 = 
  alphaLowerChar;System.out.println("""res2: org.scalacheck.Gen[Char] = """ + $show(res$2));$skip(40); val res$3 = 
  choose(97,122) map (it => it.toChar );System.out.println("""res3: org.scalacheck.Gen[Char] = """ + $show(res$3));$skip(25); val res$4 = 
 
  alphaLowerChar.label;System.out.println("""res4: String = """ + $show(res$4));$skip(15); val res$5 = 
  choose(1, 5);System.out.println("""res5: org.scalacheck.Gen[Int] = """ + $show(res$5));$skip(11); val res$6 = 
  listOf();System.out.println("""res6: org.scalacheck.Gen[List[Unit]] = """ + $show(res$6));$skip(39); 
  val isEmpty = Gen.oneOf(true, false);System.out.println("""isEmpty  : org.scalacheck.Gen[Boolean] = """ + $show(isEmpty ));$skip(36); 
  
  lazy val genHeap: Gen[H] = ???;System.out.println("""genHeap  : org.scalacheck.Gen[<error>] = <lazy>""");$skip(64); 

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap);System.out.println("""arbHeap  : org.scalacheck.Arbitrary[<error>] = <lazy>""");$skip(76); val res$7 = 
  
  for {isEmpty <- Gen.oneOf(true, false)
       heap <- arbHeap} yield
};System.out.println("""res7: org.scalacheck.Gen[Null] = """ + $show(res$7))}
