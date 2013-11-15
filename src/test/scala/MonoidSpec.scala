package test.scala

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import main.scala.Monoid

/**
 * Created with IntelliJ IDEA.
 * User: brobinson3
 * Date: 11/15/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
object MonoidSpec extends Properties("Monoid laws"){

  val stringMonoid = new Monoid[String]{
    def zero = ""
    def combine(a: String, b: String):String = a + b
  }

  property("string monoid associativity") = forAll { (a: String, b: String, c: String) =>
    stringMonoid.combine(a,stringMonoid.combine(b,c)).equals(stringMonoid.combine(stringMonoid.combine(a,b),c)) }

  property("string monoid identity") = forAll { (a:String) =>
    stringMonoid.combine(stringMonoid.zero ,a) == a}

//  property("failure") = forAll{ (a: String, b: String) =>
//    a.reverse == b}
}
