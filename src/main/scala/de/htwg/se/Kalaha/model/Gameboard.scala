package de.htwg.se.Kalaha.model

case class Gameboard() {
  val SIZE = 14
  var stones = 6
  var startboard: Array[Int] = Array[Int](0, stones, stones, stones, stones, stones, stones, 0, stones, stones, stones, stones, stones, stones)
  var gameboard = new Array[Int](SIZE)
  var oldgb = new Array[Int](SIZE)

  def boardInit(amountStonesStart: Int): Unit = {
    stones = amountStonesStart
    for (i <- 1 until SIZE) {
      //print(gameboard(i))
      if (startboard(i) != 0) startboard(i) = amountStonesStart
    }
    gameboard = startboard.clone()
  }

  def boardInit(): Unit = {
    gameboard = startboard.clone()
  }

  def setBoard(newBoard: Array[Int]): Unit = {
    gameboard = newBoard.clone()
  }

//  def setBoardwithString(newBoard: String): Unit = {
//    var game = new Array[Int](SIZE)
//    for (i <- gameboard.indices) {
//      print("\n hier ich " + i + "----" + newBoard.charAt(i).toInt)
//      game(i) = newBoard.charAt(i).toInt
//    }
//    gameboard = game.clone()
//  }


  override def toString: String = {
    var s: String = ""
    for (i <- gameboard.indices)
      s += gameboard(i)
    s
  }
}
