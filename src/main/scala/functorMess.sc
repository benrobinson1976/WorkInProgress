
object mess {

class Boxed[A](val t: A) {
  def fmap[A,B](f:A => B)(ab: Boxed[A]): Boxed[B] = new Boxed(f(ab.t))
  def id[A](a:A): Boxed[A] = new Boxed[A](a)
  override def toString(): String = t.toString
}

def length(a: String): Int = a.size
def toUpper(k: String): String = k.toUpperCase
val boxedF = new Boxed()
val id1 = boxedF.id("")
val id2 = new Boxed[String]("")
id1.t.equals(id2.t)

val h1 = new Boxed[String]("good")
val h2 = new Boxed[String]("luck")
//h1.fmap(length)(h1)
//h2.fmap(toUpper)(h2)

//prove functor preserves structure
h1.fmap(length _ compose toUpper _)(h1).t equals (h1.fmap(length) _ compose h1.fmap(toUpper) _)(h1).t
  //identity map different from id, think this proves identity property
h1.fmap((x:String) => x)(h1).t equals h1.id("good").t
}
