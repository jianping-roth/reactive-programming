package simulations

object sim {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(77); 
  println("Welcome to the Scala worksheet")
 import math.random
  type Action = () => Unit
  case class Event(time: Int, action: Action)

  trait Parameters {
    def InserterDelay = 2
    def AndGateDelay = 3
    def OrGateDelay = 5
  }
  abstract class Simulation {
   private var curtime = 0
   def currTime: Int = curtime
   
   private var agenda: List[Event] = List()
   private def insert(ag: List[Event], item: Event) : List[Event] = ag match {
    case first::rest if first.time >= item.time => first::insert(rest, item)
    case _ => item::ag
   }
   
   def afterDelay(delay: Int) (block: => Unit) : Unit = {
     val item = Event(currTime + delay, () => block)
     agenda = insert(agenda, item)
   }
   
   def run() {
    afterDelay(0) {
      println(s"=== simulation start, time = ${currTime} ===")
    }
    
    loop()
   }
   
   private def loop(): Unit = agenda match {
      case head::rest =>
        head.action()
        agenda = rest
        loop()
      case Nil =>
   }
  }
  
  class Wire {
    private var sigVal = false
    private var actions: List[Action] = List()
    def getSignal = sigVal
    def setSignal(s: Boolean) = {
      if (sigVal != s) {
        sigVal = s
        actions foreach (_())
      }
    }
    def addAction(a: Action) {
      actions == a::actions
      a()
    }
  };$skip(1311); 

  val w1, w2, w3 = new Wire;System.out.println("""w1  : simulations.sim.Wire = """ + $show(w1 ));System.out.println("""w2  : simulations.sim.Wire = """ + $show(w2 ));System.out.println("""w3  : simulations.sim.Wire = """ + $show(w3 ));$skip(48); val res$0 = 
  for {w <- List(w1, w2, w3)} yield w.getSignal;System.out.println("""res0: List[Boolean] = """ + $show(res$0));$skip(60); 
  
  val v = (for (p <- (1 until 10).toList) yield p).toSeq;System.out.println("""v  : scala.collection.immutable.Seq[Int] = """ + $show(v ));$skip(23); val res$1 = 
  for (p <- v) yield p;System.out.println("""res1: scala.collection.immutable.Seq[Int] = """ + $show(res$1));$skip(29); 
  val l : List[Int] = List();System.out.println("""l  : List[Int] = """ + $show(l ));$skip(20); val res$2 = 
  (300 * .01).toInt;System.out.println("""res2: Int = """ + $show(res$2));$skip(47); 
  def randomBelow(i: Int) = (random * i).toInt;System.out.println("""randomBelow: (i: Int)Int""");$skip(46); val res$3 = 
  
  for (i <- 1 to 100) yield randomBelow(1);System.out.println("""res3: scala.collection.immutable.IndexedSeq[Int] = """ + $show(res$3))}
}
