package test.scala

import org.scalacheck.Prop.forAll
import org.scalacheck.Prop._
import org.scalacheck.{Properties, Gen}
import main.scala.Group

case class IntMod7(ints: Int*) extends Group[Int]{
  def zero = 7
  def combine(a: Int, b: Int): Int = {
    (a + b) % zero
  }

  def subtract(a: Int, b: Int): Int = {
    (a - b) % zero
  }

  def inverse(n: Int): Int = {
    //may produce negative number
    zero - (n % zero)
  }
}

object GroupSpec extends Properties("Group laws"){

  val intMod7 = new Group[Int] {
    def zero = 7
    def combine(a: Int, b: Int):Int = {
      val c: Int = a % zero
      val d: Int = b % zero
      //println(a, b)
      //(c + d) % zero
      (a + b) % zero
    }
    def subtract(a: Int, b: Int):Int = {
      (a - b) % zero
    }

    def inverse(n: Int): Int = {
      //must solve for (n combine x) mod zero IDEQ to zero
      zero - (n % zero)
    }
  }

  property("group identity") = forAll{(a: Int) =>
    (a > 0) ==> (intMod7.combine(a, intMod7.zero) == (a % intMod7.zero))

  }

  property("associativity of addition, test will fail") = forAll{(a: Int, b: Int, c: Int) =>
    (a > 0 && b >0 && c > 0) ==> (intMod7.combine(a, intMod7.combine(b,c)) == intMod7.combine(intMod7.combine(a,b),c))
  }

  val arbIntMod7Gen: Gen[(IntMod7, Int, Int, Int)] = for {
    i <- Gen.choose(0, 100000)
    j <- Gen.choose(0, 100000)
    k <- Gen.choose(0, 100000)
  } yield (IntMod7(i,j,k), i, j, k)

  property("associativity of group operation") = forAll(arbIntMod7Gen){(input: (IntMod7, Int, Int, Int)) =>
    input match {
      case(m, a, b, c) => m.combine(a, m.combine(b,c)) == m.combine(m.combine(a,b), c)
    }
  }

  val arbIntMod7GenInv: Gen[(IntMod7, Int)] = for {
    i <- Gen.choose(1, 100)
  } yield (IntMod7(i), i)

  property("existence of inverse") = forAll(arbIntMod7GenInv){(input: (IntMod7, Int)) =>
    input match {
      //in modulo arithmetic 7 identically equivalent to 0
      case(m, a) => m.combine(m.inverse(a), (a % m.zero)) == m.zero % m.zero
    }
  }

  property("idempotency of identity property") = forAll(arbIntMod7GenInv){(input: (IntMod7, Int)) =>
    input match {
      case(m, a) => m.combine(a, m.zero) == a % m.zero
    }
  }

  //TODO one step subgroup test

}
