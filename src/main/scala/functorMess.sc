

object mess {

class Boxed[A](val t: A) {
  //def fmap[A,B](f:A => B)(ab:Boxed[A]): (Boxed[A] => Boxed[B]) = ab => new Boxed(f(ab.t))
  def fmap[A,B](f:A => B): (Boxed[A] => Boxed[B]) = (ab:Boxed[A]) => new Boxed(f(ab.t))
  def id[A](a:A): Boxed[A] = new Boxed[A](a)
}
def length(a: String): Int = a.size
val boxedF = new Boxed()
//val liftedLength = boxedF.fmap(length)_
val liftedLength  = boxedF.fmap(length)
liftedLength(new Boxed("hello")).t
}
