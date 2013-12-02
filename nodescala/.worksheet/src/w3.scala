object w3 {
import scala.language.postfixOps
import scala.util.{Try, Success, Failure}
import scala.collection._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.async.Async.{async, await}
import nodescala._;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(284); val res$0 = 

  List(1);System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(146); 
  val working = Future.run() { ct =>
    Future {
	    while (ct.nonCancelled) {
        //print("working ")
	    }
	    println(" done")
	  }
	};System.out.println("""working  : nodescala.Subscription = """ + $show(working ));$skip(94); 
	         
 	Future.delay(3 seconds) onSuccess {
	  case _ =>
	      working.unsubscribe()
	};$skip(27); 
	
	var p = Promise[Unit]();System.out.println("""p  : scala.concurrent.Promise[Unit] = """ + $show(p ));$skip(38); 
 
  val f = Future[Int] {
	    7
	  };System.out.println("""f  : scala.concurrent.Future[Int] = """ + $show(f ));$skip(90); 
	 
  val f1 = Future[Int] {
	   while (true) {
	    //println("done")
	    }
	    10
	  };System.out.println("""f1  : scala.concurrent.Future[Int] = """ + $show(f1 ));$skip(8); val res$1 = 
  f.now;System.out.println("""res1: Int = """ + $show(res$1));$skip(115); 
	
  val df = Future[Int]{
	  //Await.ready(Promise().future, 1 seconds)
	  blocking {Thread.sleep(60000)}
	  5
  };System.out.println("""df  : scala.concurrent.Future[Int] = """ + $show(df ));$skip(32); 
 blocking {Thread.sleep(60003)};$skip(9); val res$2 = 

 df.now;System.out.println("""res2: Int = """ + $show(res$2))}
}
