package main.scala

trait Group[A] extends Monoid[A]{
  def subtract(a: A, b: A) : A
  def inverse(a: A) : A
}
