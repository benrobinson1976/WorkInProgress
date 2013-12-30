package main.scala

trait Functor[T[_]] {
  def fmap[A,B](f:A => B): (T[A] => T[B])
  def id[A](a:A):T[A]
}
