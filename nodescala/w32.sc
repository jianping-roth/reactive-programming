object w32 {
import scala.language.postfixOps
import scala.util.{Try, Success, Failure}
import scala.collection._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.async.Async.{async, await}
import nodescala._


   val myServer = new NodeScala.Default(8090)     //> myServer  : nodescala.NodeScala.Default = nodescala.NodeScala$Default@2c641e
                                                  //| 9a
   val homeSubscription = myServer.start("/home") {
	  req => "Have a nice day!".split(" ").iterator
	}                                         //> homeSubscription  : nodescala.package.Subscription = nodescala.NodeScala$Lis
                                                  //| tener$Default$$anon$1@7d2452e8
	
	homeSubscription.unsubscribe
}