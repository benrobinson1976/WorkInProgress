package main.scala

trait Group[A] {
  def zero : A
  def combine(a: A, b: A) : A
  def subtract(a: A, b: A) : A
}
