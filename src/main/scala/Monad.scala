
trait Monad[M[_]] {
  def unit[A](a: A): M[A]
  def map[A, B] (a2b: A => B): M[A] => M[B]
  def flatten[A](ma2a: M[M[A]]): M[A]
  def flatMap[A,B](ma: M[A], a2mb: A => M[B]): M[B] = {
    flatten(map(a2mb)(ma))
  }
}
class MyIntBox[T](val t: T)

trait Functor[F[_]] {
  self =>
  def fmap[A,B](f: A => B)(fa: F[A]):F[B]
}




//
//class BoxMonad[MyBox[T]] extends Monad {
//  def unit[T](a:T): MyBox[T] = MyBox.apply(a)
//
//  def map[Int, String](a: String, b: Int): MyBox[String] => MyBox[Int] = {
//
//  }
//}


object MTest {

}
