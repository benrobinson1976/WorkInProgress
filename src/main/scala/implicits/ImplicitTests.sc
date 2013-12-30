/**
 * Created with IntelliJ IDEA.
 * User: brobinson3
 * Date: 12/18/13
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */

object implicits {
  def findAnInt(implicit x: Int) = x

  implicit val test = 5

  findAnInt(test)
}
