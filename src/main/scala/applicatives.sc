import scala.Predef._


object session{
class Guy(a: String, b: String)
class ABoxed[Any](val t: Any) {
  override def toString() = t.toString
}
sealed trait Validation[E,X]
final case class Success[E,X](x:X) extends Validation[E,X]
final case class Failure[E,X](e:E) extends Validation[E,X]

def checkOne(s: String): Validation[List[String], String] = {
  if(s.length < 5)
    Success(s)
  else
    Failure(List("greater and 5 characters"))
}
def checkTwo(s: String): Validation[List[String], String] = {
  if(s.contains("He"))
    Success(s)
  else
    Failure(List("does not contain He"))
}

val g = (new Guy(_,_)).curried
val h = (a:String) => (b:String) => (c:String) => a + b + c
val j = (a: String) => (b: String) => a + b
val m = (a: String, b:String) => a + b
val k = (c: String => String) => (d: String) => c + d
  k compose j
  k compose m.curried
def pure[A](a:A):ABoxed[A] = new ABoxed(a)
def fmap[A,B](f: A => B): ABoxed[A] => ABoxed[B] = (a:ABoxed[A]) => new ABoxed[B](f(a.t))
def fmap2[A,B](f: A => B)(a: ABoxed[A]): ABoxed[B] = new ABoxed[B](f(a.t))
def apply[A,B](fb: ABoxed[A => B])(a: ABoxed[A]): ABoxed[B] = new ABoxed[B](fb.t(a.t))
def apply2[A,B](fb: ABoxed[A => B]): ABoxed[A] => ABoxed[B] = a => new ABoxed[B](fb.t(a.t))
val n = (a: String, b:String, c: String) => a + b + c
val ncurr =  n.curried
val f = (a:String) => (b:String) => a + b
val d = (d: String) => d
// fmap(f) _  won't work
fmap(d) // <function1>
fmap2(f) _
fmap(f)(pure("hi"))
fmap2(f)(pure("hi"))
apply((fmap(f))(pure("hi")))(pure("there"))
apply((fmap2(f))(pure("hi")))(pure("there"))
apply(apply(fmap2(ncurr)(pure("hey")))(pure("you")))(pure("guys"))
apply2(apply2(fmap2(ncurr)(pure("hey")))(pure("you")))(pure("guys"))
apply2(fmap2(ncurr)(pure("hey")))(pure("you")) //returns a <function1>

}