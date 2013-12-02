object w32 {
import scala.language.postfixOps
import scala.util.{Try, Success, Failure}
import scala.collection._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.async.Async.{async, await}
import nodescala._;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(322); 


   val myServer = new NodeScala.Default(8090);System.out.println("""myServer  : nodescala.NodeScala.Default = """ + $show(myServer ));$skip(104); 
   val homeSubscription = myServer.start("/home") {
	  req => "Have a nice day!".split(" ").iterator
	};System.out.println("""homeSubscription  : nodescala.package.Subscription = """ + $show(homeSubscription ));$skip(32); 
	
	homeSubscription.unsubscribe}
}
