package simulations

import common._

class Wire {
  private var sigVal = false
  private var actions: List[Simulator#Action] = List()

  def getSignal: Boolean = sigVal
  
  def setSignal(s: Boolean) {
    if (s != sigVal) {
      sigVal = s
      actions.foreach(action => action())
    }
  }

  def addAction(a: Simulator#Action) {
    actions = a :: actions
    a()
  }
}

abstract class CircuitSimulator extends Simulator {

  val InverterDelay: Int
  val AndGateDelay: Int
  val OrGateDelay: Int
  val DemuxGateDelay: Int
  
  def probe(name: String, wire: Wire) {
    wire addAction {
      () => afterDelay(0) {
        println(
          "  " + currentTime + ": " + name + " -> " +  wire.getSignal)
      }
    }
  }

  def inverter(input: Wire, output: Wire) {
    def invertAction() {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) { output.setSignal(!inputSig) }
    }
    input addAction invertAction
  }

  def andGate(a1: Wire, a2: Wire, output: Wire) {
    def andAction() {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      afterDelay(AndGateDelay) { output.setSignal(a1Sig & a2Sig) }
    }
    a1 addAction andAction
    a2 addAction andAction
  }

  //
  // to complete with orGates and demux...
  //

  def orGate(a1: Wire, a2: Wire, output: Wire) {
     def orAction() {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      afterDelay(OrGateDelay) { output.setSignal(a1Sig | a2Sig) }
    }
    a1 addAction orAction
    a2 addAction orAction
  }
      
  def orGate2(a1: Wire, a2: Wire, output: Wire) {
      val b1, b2, c1 = new Wire
      inverter(a1, b1)
      inverter(a2, b2)
      andGate(b1, b2, c1)
      inverter(c1, output)
  }
  
  def demux(in: Wire, c: List[Wire], out: List[Wire]) {
      def doDemux(outIndex: Int, acc: Wire, c: List[Wire]) {
		  c match {
		    case Nil => 
		      andGate(acc, in, out(outIndex))
		    case head::tail =>
		      var index = outIndex*2
		      val newAcc, newAcc2 = new Wire
		      if (head.getSignal == true) {
		         index = index +1
		         andGate(acc, head, newAcc)
		      } else {
		        inverter(head, newAcc2);
		        andGate(acc, newAcc2, newAcc)
		      }
		      
		      doDemux(index, newAcc, tail)
        }
      }
	   
      def start() {
          afterDelay(DemuxGateDelay) { 
	          for {o <- out} yield o.setSignal(false)
	          if (in.getSignal == true) {
	             doDemux(0, in, c)
	          }
          }
      }
              
      for (ctrl <- c) {
        ctrl addAction start
      }

      in addAction start
   }
}

object Circuit extends CircuitSimulator {
  val InverterDelay = 1
  val AndGateDelay = 3
  val OrGateDelay = 5
  val DemuxGateDelay = 7

  def andGateExample {
    val in1, in2, out = new Wire
    andGate(in1, in2, out)
    probe("in1", in1)
    probe("in2", in2)
    probe("out", out)
    in1.setSignal(false)
    in2.setSignal(false)
    run

    in1.setSignal(true)
    run

    in2.setSignal(true)
    run
  }

  //
  // to complete with orGateExample and demuxExample...
  //
  def orGateExample {
   val in1, in2, out = new Wire
    orGate(in1, in2, out)
    probe("in1", in1)
    probe("in2", in2)
    probe("out", out)
    in1.setSignal(false)
    in2.setSignal(false)
    run

    in1.setSignal(true)
    run

    in2.setSignal(true)
    run
    
    in1.setSignal(false)
    run
  }
  
  def orGate2Example {
   val in1, in2, out = new Wire
    orGate2(in1, in2, out)
    probe("in1", in1)
    probe("in2", in2)
    probe("out", out)
    in1.setSignal(false)
    in2.setSignal(false)
    run

    in1.setSignal(true)
    run

    in2.setSignal(true)
    run
    
    in1.setSignal(false)
    run
  }
}

object CircuitMain extends App {
  // You can write tests either here, or better in the test class CircuitSuite.
  Circuit.andGateExample
}
