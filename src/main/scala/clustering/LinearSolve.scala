package clustering


import breeze.linalg.{DenseVector, DenseMatrix}
import breeze.linalg._
import breeze.numerics._
import breeze.math._
import clustering.FileActor
import akka.actor.{ActorLogging, Props, ActorSystem, Actor}
import scala.concurrent.Await
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class MasterActor extends Actor with ActorLogging {
  def receive = {

    case s: String => {
      val fileActor = context.actorOf(Props[FileActor], "fileActor")
      fileActor ! s
    }
    case mat: DenseMatrix[Double] => {
      log.info("got matrix from file")
      val conjGradActor = context.actorOf(Props[ConjugateGradientActor], "conjGradActor")
      conjGradActor ! mat
    }
    case _ => {
      log.info("Send a file")
    }
  }
}

class ConjugateGradientActor extends Actor with ActorLogging {
  val b = DenseVector(3.3, 1.2, 1.8, 4.9, 6.0, 3.1)

  def receive = {
    case mat: DenseMatrix[Double] => {
      log.info("mat rows {}", mat.rows)
      log.info("mat cols {}", mat.cols)
      val x1 = mat \ b
      log.info("First use breeze to solve {}", x1)

      conjGrad(mat)
    }


      def conjGrad(mat: DenseMatrix[Double]): DenseVector[Double] = {
        //solve mat*x = b for x
        //initial guess for x
        val xi = DenseVector.zeros[Double](6)
        val ri = b - mat * xi
        val pi = ri

        def inner(r: DenseVector[Double], p: DenseVector[Double], x: DenseVector[Double], count: Int): DenseVector[Double] = {
          if (count == 5) x
          val rt = (r.t * r)
          log.info("p {}", p.length)
          val pMat = mat * p
          val ptMat = p.t * pMat
          log.info("ptMat {}", ptMat)
          // val pdot = ptMat * p
          val alpha = rt / ptMat

          val scaleAlpha = p * alpha
          val x_hat: DenseVector[Double] = x + scaleAlpha
          val matP = mat * p
          val scaleAlpha2 = matP * alpha
          val r_hat: DenseVector[Double] = r - scaleAlpha2
          val beta: Double = (r_hat.t * r_hat) / (r.t * r)
          val betaP = p * beta
          val p_hat: DenseVector[Double] = r_hat + betaP
          log.info(" x_hat {}", x_hat)
          val incr = count + 1
          inner(r_hat, p_hat, x_hat, incr)
        }
        inner(ri, pi, xi, 0)
      }


  }
}

object LinearSolve extends App {
  val system = ActorSystem("linsolve")
  implicit val timeout = Timeout(2 seconds)

  val masterActor = system.actorOf(Props[MasterActor], "masterActor")
  masterActor ! "src/main/resources/conjgrad.csv"
  Thread.sleep(1000)

  //  val conjugateGradActor = system.actorOf(Props[ConjugateGradientActor], "conjugateGradActor")
  //  import system.dispatcher
  //  val fileActor2 = system.actorOf(Props[FileActor], "fileActor2")
  //  val fut = fileActor2 ? "src/main/resources/svd.csv"
  //
  //  val res = Await.result(fut, timeout.duration)
  system.shutdown()
}
