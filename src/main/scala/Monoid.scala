package main.scala

/**
 * Created with IntelliJ IDEA.
 * User: brobinson3
 * Date: 11/15/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
trait Monoid[A] {
  def zero : A
  def combine(a: A, b: A) : A
}

