object matrixNoCC {
  case class Matrix2D(m1: List[List[Double]], m2: List[List[Double]])

  def mult(row: List[Double], column: List[Double]): Double = {
    row zip column map((w: (Double, Double)) => w._1 * w._2) reduceLeft(_+_)
  }

  def rowColumnList(row: List[Double], columns: List[List[Double]]): List[(List[Double], List[Double])] = {
    for {
      k <- columns.transpose
    } yield (row, k)
  }


  def rowColListToResultArray(row: List[Double], columns: List[List[Double]]): List[Double] = {
    for {
      s <- rowColumnList(row, columns)
    } yield mult(s._1, s._2)
  }

  def listOfRowColumns(m: Matrix2D): List[(List[Double],List[List[Double]])] = {
    for {
      row <- m.m1
    } yield (row, m.m2)
  }

  val m2d = Matrix2D.apply(List(List(1.0, 2.0), List(1.5, 2.5)), List(List(3.0, 1.0), List(0, .5)))


  val hope = for {
    h <- listOfRowColumns(m2d)
  } yield rowColListToResultArray(h._1, h._2)
}
