/**
 * Created by brobinson3 on 3/10/14.
 */

def factorial(i:Int): Int = {
  System.out.println("starting factorial")
  def helper(n: Int, accumulator: Int): Int = {
    //System.out.println("hello: " + n)
    if (n == 1) accumulator
    else helper(n - 1, n* accumulator)
  }
  if(i>16){
    System.out.println("Please choose a number between 1 and 16");1
  } else {
    helper(i, 1)
  }
}
factorial(17)




def testFibonacci(f:Int):Int = {

  def fibHelp(prev:Int, prev2:Int, iter: Int): Int = {
    System.out.println("prev: " + prev)
    if(iter == f) prev
    else fibHelp(prev2 + prev, prev, iter + 1)
  }
  fibHelp(1,1,1)
}
testFibonacci(30)




































