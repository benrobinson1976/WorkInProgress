package matrix

import scala._


abstract class  Matrix(in: List[List[Double]]) {
  self =>

  def apply()
  def listOfRowColumns(self: List[List[Double]], that: List[List[Double]]): List[(List[Double],List[List[Double]])] = {
    for {
      row <- self
    } yield (row, that)
  }

  def rowColListToResultArray(row: List[Double], columns: List[List[Double]]): List[Double] = {
    for {
      s <- rowColumnList(row, columns)
    } yield mult(s._1, s._2)
  }

  def rowColumnList(row: List[Double], columns: List[List[Double]]): List[(List[Double], List[Double])] = {
    for {
      k <- columns.transpose
    } yield (row, k)
  }

  def mult(row: List[Double], column: List[Double]): Double = {
    row zip column map((w: (Double, Double)) => w._1 * w._2) reduceLeft(_+_)
  }

//  def product(that: Matrix): Matrix= {
//    val rs = for {
//      h <- listOfRowColumns(self, that)
//    } yield rowColListToResultArray(h._1, h._2)
//    Matrix.apply(rs)
//  }
}
//
//object Matrix {
//   def apply(in: List[List[Double]]): Matrix = {
//    new Matrix(in)
//  }

//  def unapply(in: Matrix): List[List[Double]] = {
//    in.
//  }

//}
