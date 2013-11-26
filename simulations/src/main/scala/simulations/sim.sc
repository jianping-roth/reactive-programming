package simulations

object sim {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
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
  }

  val w1, w2, w3 = new Wire                       //> w1  : simulations.sim.Wire = simulations.sim$$anonfun$main$1$Wire$1@82a6f16
                                                  //| 
                                                  //| w2  : simulations.sim.Wire = simulations.sim$$anonfun$main$1$Wire$1@19e3118
                                                  //| a
                                                  //| w3  : simulations.sim.Wire = simulations.sim$$anonfun$main$1$Wire$1@a94884d
                                                  //| 
  for {w <- List(w1, w2, w3)} yield w.getSignal   //> res0: List[Boolean] = List(false, false, false)
  
  val v = (for (p <- (1 until 10).toList) yield p).toSeq
                                                  //> v  : scala.collection.immutable.Seq[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
  for (p <- v) yield p                            //> res1: scala.collection.immutable.Seq[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                                  //| 
  val l : List[Int] = List()                      //> l  : List[Int] = List()
  (300 * .01).toInt                               //> res2: Int = 3
  def randomBelow(i: Int) = (random * i).toInt    //> randomBelow: (i: Int)Int
  
  for (i <- 1 to 100) yield randomBelow(1)        //> res3: scala.collection.immutable.IndexedSeq[Int] = Vector(0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                  //|  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
}