
object matrix {

  //TODO at 12/11/13 do you think we should get rid of the case classes???
  case class RowColumn(row: Seq[Double], column: Seq[Double])

   def mult(rc: RowColumn): Double = {
    rc.row zip rc.column map((w: (Double, Double)) => w._1 * w._2) reduceLeft(_+_)
   }
  case class RowColumns(row: Seq[Double], columns: Seq[Seq[Double]])
  //Seq[RowColumns] ???
  def rowColumnList(rcs: RowColumns): Seq[RowColumn] = {
     for {
       k <- rcs.columns.transpose
     } yield RowColumn(rcs.row, k)
  }
  case class ResultArray(row: Seq[Double])

  def rowColListToResultArray(rcs: RowColumns): ResultArray = {
    val rs = for {
      s <- rowColumnList(rcs)
    } yield mult(s)
    ResultArray.apply(rs)
  }
  case class Columns(columns: Seq[Seq[Double]])
  case class Rows(rows: Seq[Seq[Double]])
  case class Matrix2D(r: Rows, c: Columns)

  def listOfRowColumns(m: Matrix2D): Seq[RowColumns] = {
    for {
      row <- m.r.rows
    } yield RowColumns.apply(row, (m.c).columns)
  }
  val rows = Rows.apply(List(List(1.0, 2.0), List(1.5, 2.5)))
  val cols = Columns.apply(List(List(3.0, 1.0), List(0, .5)))
  val m2d = Matrix2D.apply(rows, cols)

  listOfRowColumns(m2d)





  val hope = for {
    h <- listOfRowColumns(m2d)
  } yield rowColListToResultArray(h)


  val rc1 = RowColumn.apply(List(1.3, 5.6734, 3.3), List(4.233, 0, 2.7))
  mult(rc1)
  val ta1 = Array(Array(1.3, 5.6734, 3.3),Array(4.233, 0, 2.7),Array(7.62,.2128,.3419))

  val ta2 = Array(Array(6.3,1.6, 0),Array(11.3453, 0.1231, 21.7),Array(3.23,1.564,5))



  val ta3 = ta2.transpose


  ta1 zip ta3





  val easy1  = Array(Array(1.0, 2.0), Array(1.5, 2.5))
  val easy2  = Array(Array(3.0, 1.0), Array(0, .5))
  val easy2t = easy2.transpose
  val zipped = easy1 zip easy2t



  //each row in matrix 1 needs to mult each column in matrix 2
  val list1 = List(1,2)
  val list2 = List(4,5)

  val something = for {
    i <- list1
    j <- list2
  } yield (i,j)


  val result = for (m <- easy1){
    for {
    n <- easy2t
    p <- m zip n
    } yield List(p)
  }




  //zipped.map((z: (Array[Double], Array[Double])) => z._1 zip z._2 ).

  //flatMap(f: (Array[(Double, Double)]) => Seq[Double])

  //for (i <- zipped) yield i

  //for (i <- List.range(0, easy1.size); j <- List.range(0, easy2.size))  println(i,j)









}

