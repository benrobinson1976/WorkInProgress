package test.scala

import org.scalacheck.Prop.forAll
import org.scalacheck.Prop._
import org.scalacheck.{Properties, Gen}


class Boxed[A](val t: A) {
  def fmap[A,B](f:A => B)(ab:Boxed[A]): (Boxed[A] => Boxed[B]) = ab => new Boxed(f(ab.t))
  def id[A](a:A): Boxed[A] = new Boxed[A](a)
}

object FunctorSpec extends Properties("Functor laws"){

  def length(a: String): Int = a.size
  val boxedF = new Boxed("hello")
  val liftedLength = boxedF.fmap(length)(boxedF)

}
