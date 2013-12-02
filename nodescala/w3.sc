object w3 {
import scala.language.postfixOps
import scala.util.{Try, Success, Failure}
import scala.collection._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.async.Async.{async, await}
import nodescala._

  List(1)                                         //> res0: List[Int] = List(1)
  val working = Future.run() { ct =>
    Future {
	    while (ct.nonCancelled) {
        //print("working ")
	    }
	    println(" done")
	  }
	}                                         //> working  : nodescala.Subscription = nodescala.package$CancellationTokenSourc
                                                  //| e$$anon$1@3b6f0be8
	         
 	Future.delay(3 seconds) onSuccess {
	  case _ =>
	      working.unsubscribe()
	}
	
	var p = Promise[Unit]()                   //> p  : scala.concurrent.Promise[Unit] = scala.concurrent.impl.Promise$DefaultP
                                                  //| romise@48ff2413
 
  val f = Future[Int] {
	    7
	  }                                       //> f  : scala.concurrent.Future[Int] = scala.concurrent.impl.Promise$DefaultPro
                                                  //| mise@3df78040
	 
  val f1 = Future[Int] {
	   while (true) {
	    //println("done")
	    }
	    10
	  }                                       //> f1  : scala.concurrent.Future[Int] = scala.concurrent.impl.Promise$DefaultPr
                                                  //| omise@e49d67c
  f.now                                           //> res1: Int = 7
	
  val df = Future[Int]{
	  //Await.ready(Promise().future, 1 seconds)
	  blocking {Thread.sleep(60000)}
	  5
  }                                               //> df  : scala.concurrent.Future[Int] = scala.concurrent.impl.Promise$DefaultPr
                                                  //| omise@245e13ad
 blocking {Thread.sleep(60003)}                   //>  done

 df.now                                           //> res2: Int = 5
}