package test.scala

import org.scalacheck.{Properties, Gen}
import org.scalacheck.Prop.forAll
import main.scala.Monoid


case class IntMonoid(a: Int, b: Int) extends Monoid[Int]{
  def zero = 0
  def combine(a: Int, b: Int): Int = a + b
}

case class StringMnd(s: String*) {
  def zero = ""
  def combine(a: String, b: String):String = a + b
}

object MonoidSpec extends Properties("Monoid laws"){

  val stringMonoid = new Monoid[String]{
    def zero = ""
    def combine(a: String, b: String):String = a + b
  }

  property("string monoid associativity") = forAll { (a: String, b: String, c: String) =>
    stringMonoid.combine(a,stringMonoid.combine(b,c)).equals(stringMonoid.combine(stringMonoid.combine(a,b),c)) }

  property("string monoid identity") = forAll { (a:String) =>
    stringMonoid.combine(stringMonoid.zero ,a) == a}

  val monoidIntGen: Gen[(IntMonoid, Int, Int)] = for {
    a <- Gen.choose(0, 1000)
    b <- Gen.choose(0, 1000)
  } yield (IntMonoid(a,b), a, b)

  property("IntMonoid associativity") = forAll(monoidIntGen) { (input: (IntMonoid, Int, Int)) =>
    input match {
      case(m, a, b) => m.combine(a,b) == m.combine(b,a)
    }
  }

  val stringMndGen: Gen[(StringMnd, String, String, String)] = for {
    s <- Gen.alphaStr
    t <- Gen.alphaStr
    u <- Gen.alphaStr
  } yield (StringMnd(s,t, u), s ,t, u)

  property("StringMnd associativity") = forAll(stringMndGen) { (input: (StringMnd, String, String, String)) =>
    input match {
      case(m, s, t, u) => m.combine(s, m.combine(t,u)).equals(m.combine(m.combine(s,t),u))
    }

  }

//  property("failure") = forAll{ (a: String, b: String) =>
//    a.reverse == b}
}
