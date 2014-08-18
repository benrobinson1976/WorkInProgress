package clustering

import java.io.File

import akka.actor.{ActorLogging, Props, ActorSystem, Actor}
import breeze.linalg._
import breeze.linalg.svd.SVD
import clustering.ClusterAppProtocol.{SVDRequest, FileFromActor}
import scala.concurrent.Await
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object ClusterAppProtocol {

  case class SVDRequest(fileName: String)

  case object FileFromActor

  //case class GetFile(fileName: String)
}


class AppCosineSimActor extends Actor with ActorLogging {
  def receive = {
    case mat: DenseMatrix[Double] => {
      log.info("Thanks for the matrix, let's do a sample vector cosine similarity")
      val v1 = mat(::, 1)
      val v2 = mat(::, 2)
      val res: Double = (v1 dot v2) / (norm(v1) * norm(v2))
      sender ! res
    }
    case _ => {
      log.info("Send me a matrix")
    }
  }
}

class FileActor extends Actor with ActorLogging {
  def receive = {
    case msg: String => {
      log.info("Thanks for sending me {} ", msg)
      sender ! csvread(new File(msg))
    }
    case _ => {
      log.info("I didn't get a file")
    }
  }
}

class AppSVDActor extends Actor with ActorLogging {
  implicit val lowerBnd = 2.0

  import ClusterAppProtocol._

  def receive = {

    case SVDRequest(fileName) => {
      log.info("Sending request {} to fileActor", fileName)
      val fileActor = context.actorOf(Props[FileActor], "fileActor")
      fileActor ! fileName
    }
    case mat: DenseMatrix[Double] => {
      log.info("Sending request to svdCosineActor")
      val svdCosineActor = context.actorOf(Props[AppCosineSimActor], "svdCosineActor")
      svdCosineActor ! svdDecomp(mat)
    }
    case result: Double => {
      //this sends it back to CosineActor!!!!!
      log.info("DONE: cosine similarity {} ", result)
    }
    case _ => {
      log.info("Send me a file name")
    }
  }

  def svdDecomp(mat: DenseMatrix[Double]): DenseMatrix[Double] = {
    //another dangerous op
    log.info("SVD work for matrix {}", mat)
    val SVD(u, s, v) = svd(mat)
    val singVal = (s.toArray.filter(_ > lowerBnd))
    //turn concatV into a matrix with u.rows and v.cols
    //create u.rows and v.cols zero matrix
    val zeroM = DenseMatrix.zeros[Double](u.rows, v.cols)
    //log.info(" zeroM before {} ", zeroM)
    for (j <- 0 until singVal.length) {
      zeroM(j, j) = singVal(j)
    }

    u * zeroM * v
  }
}

object ClusterApp extends App {
  val system = ActorSystem("clustering")
  val svdActor = system.actorOf(Props[AppSVDActor], "svdActor")

  implicit val timeout = Timeout(2 seconds)
  //  svdActor ! SVDRequest.apply("src/main/resources/svd.csv")
  //  Thread.sleep(1000)
  val future = svdActor ? SVDRequest.apply("src/main/resources/svd.csv")
  Await.result(future, timeout.duration)
  //println(res1)



  system.shutdown()
}
