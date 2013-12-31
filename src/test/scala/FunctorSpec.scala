package test.scala

import org.scalacheck.Prop._
import org.scalacheck.{Properties, Gen}
import test.scala.Boxed


case class Boxed[A](val t: A) {
  def fmap[A,B](f:A => B)(ab: Boxed[A]): Boxed[B] = new Boxed(f(ab.t))
  def id[A](a:A): Boxed[A] = new Boxed[A](a)
  override def toString(): String = t.toString
}

object FunctorSpec extends Properties("Functor laws"){

  def length(a: String): Int = a.size
  def toUpper(k: String): String = k.toUpperCase

  val functorGen: Gen[(Boxed[String], String)] = for {
    a <- Gen.alphaStr
    } yield (Boxed[String](a), a)

  property("Functor preserves structure of homomorphism") = forAll(functorGen) { (input: (Boxed[String], String)) =>
    input match {
      case(m, x) => m.fmap(length _ compose toUpper _)(m).t equals (m.fmap(length) _ compose m.fmap(toUpper) _)(m).t
    }
  }

  property("Functor preserves identity morphism") = forAll(functorGen) { (input: (Boxed[String], String)) =>
    input match {
      case(m, x) => m.fmap((x:String) => x)(m).t equals m.id(x).t
    }
  }

}
