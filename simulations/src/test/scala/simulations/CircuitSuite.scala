package simulations

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalacheck.Prop._

@RunWith(classOf[JUnitRunner])
class CircuitSuite extends CircuitSimulator with FunSuite {
  val InverterDelay = 1
  val AndGateDelay = 3
  val OrGateDelay = 5
  val DemuxGateDelay = 7
  
  test("andGate example") {
    val in1, in2, out = new Wire
    andGate(in1, in2, out)
    in1.setSignal(false)
    in2.setSignal(false)
    run
    
    assert(out.getSignal === false, "and 1")

    in1.setSignal(true)
    run
    
    assert(out.getSignal === false, "and 2")

    in2.setSignal(true)
    run
    
    assert(out.getSignal === true, "and 3")
  }

  //
  // to complete with tests for orGate, demux, ...
  //

    test("orGate example") {
        val in1, in2, out = new Wire
        orGate(in1, in2, out)
        in1.setSignal(false)
        in2.setSignal(false)
        run
        
        assert(out.getSignal === false, "or 1")
    
        in1.setSignal(true)
        run
        
        assert(out.getSignal === true, "or 2")
    
        in2.setSignal(true)
        run
    
        assert(out.getSignal === true, "or 3")
        
        in1.setSignal(false)
        run
    
        assert(out.getSignal === true, "or 4")
    }
    
    test("orGate2 example") {
        val in1, in2, out = new Wire
        orGate2(in1, in2, out)
        in1.setSignal(false)
        in2.setSignal(false)
        run
        
        assert(out.getSignal === false, "or 1")
    
        in1.setSignal(true)
        run
        
        assert(out.getSignal === true, "or 2")
    
        in2.setSignal(true)
        run
    
        assert(out.getSignal === true, "or 3")
        
        in1.setSignal(false)
        run
    
        assert(out.getSignal === true, "or 4")
    }
    
    test("demux 0 control") {
        val in = new Wire

        val c = List()
        val out = List(new Wire)
        
        demux(in: Wire, c: List[Wire], out: List[Wire])
        in.setSignal(true)

        run
        
        assert(out(0).getSignal === in.getSignal, "1 out(0) == in")
        
        in.setSignal(false)
        run
        
        assert(out(0).getSignal === in.getSignal, "2 out(0) == in")
    }
    
    test("demux 2 control") {
       val in = new Wire

        val c = List(new Wire, new Wire)
        val out = List(new Wire, new Wire, new Wire, new Wire) 
      
        demux(in: Wire, c: List[Wire], out: List[Wire])
        in.setSignal(true)
        c(0).setSignal(true)
        run
                             
        assert(out(0).getSignal === false, "0 false")
        assert(out(1).getSignal === false, "1 fales")
        assert(out(2).getSignal === true, "2 true")
        assert(out(3).getSignal === false, "3 false") 
        
        c(0).setSignal(false)
        run
        
        assert(out(0).getSignal === true, "0 is true")
        assert(out(1).getSignal === false, "1 is false")
        assert(out(2).getSignal === false, "2 is false")
        assert(out(3).getSignal === false, "3 is false")
        
        c(0).setSignal(true) 
        c(1).setSignal(true)
        run
 
        assert(out(0).getSignal === false, "0 false")
        assert(out(1).getSignal === false, "1 false")
        assert(out(2).getSignal === false, "2 false")
        assert(out(3).getSignal === true, "3 true") 
        
        c(0).setSignal(false) 
        c(1).setSignal(false)
        run
                             
        assert(out(0).getSignal === true, "0 is true")
        assert(out(1).getSignal === false, "1 is false")
        assert(out(2).getSignal === false, "2 is false")
        assert(out(3).getSignal === false, "3 is false")
        
        c(1).setSignal(true)
        run

        assert(out(0).getSignal === false, "out(0) is false")
        assert(out(1).getSignal === true, "out(1) is true")
        assert(out(2).getSignal === false, "out(2) is true")
        assert(out(3).getSignal === false, "out(3) is false")
    }
}
