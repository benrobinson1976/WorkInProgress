package clustering

import java.io.File

import akka.actor.{ActorLogging, Props, ActorSystem, Actor}
import breeze.linalg._
import breeze.linalg.svd.SVD
import scala.concurrent.Await
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

class AppCosineSimActor extends Actor with ActorLogging {
  def receive = {
    case mat: DenseMatrix[Double] => {
      log.info("Thanks for the matrix")
    }
  }
}

class AppSVDActor extends Actor with ActorLogging {
  def receive = {
    case msg: String => {
      val res = svdDecomp(readFile(msg))
      //svdDecomp(res)
      sender ! res
    }
    case _ => {
      log.info("Send me a file name")
    }
  }

  def readFile(file: String): DenseMatrix[Double] = {
    //dangerous operation, should be in it's own actor
    csvread(new File(file))
  }

  def svdDecomp(mat: DenseMatrix[Double]): DenseMatrix[Double] = {
    //another dangerous op
    val SVD(u, s, v) = svd(mat)
    val singVal = (s.toArray.filter(_ > 2.0))

    log.info("singVal : ", singVal)
    log.info("u : ", u.cols, u.rows)
    log.info("v : ", v.cols, v.rows)

    //turn concatV into a matrix with u.rows and v.cols
    //create u.rows and v.cols zero matrix
    val zeroM = DenseMatrix.zeros[Double](u.rows, v.cols)
    log.info(" zeroM before : ", zeroM)
    for (j <- 0 until singVal.length) {
      zeroM(j, j) = singVal(j)
    }

    u * zeroM * v
  }
}

object ClusterApp extends App {
  val system = ActorSystem("clustering")
  val svdActor = system.actorOf(Props[AppSVDActor], "svdActor")
  val cosActor = system.actorOf(Props[AppCosineSimActor], "cosActor")
  println("hi?")
  implicit val timeout = Timeout(5 seconds)
  val future = svdActor ? "src/main/resources/svd.csv"
  val res1 = Await.result(future, timeout.duration)

  system.shutdown()
}
