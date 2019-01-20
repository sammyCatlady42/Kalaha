package de.htwg.se.Kalaha.model.gameboardController.GameboardImpl

import de.htwg.se.Kalaha.model.gameboardController.GameboardInterface

case class Gameboard() extends GameboardInterface {
  var round = 0
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

  override def toString: String = {
    var s: String = ""
    for (i <- gameboard.indices)
      s += gameboard(i)
    s
  }

  /*def toJson:JsValue = {
    Json.obj(
      "gameboard" -> Json.obj(
        "round" -> JsNumber(round),
        "board" -> JsValue(
          gameboard
        )
      )
    )
  }*/
}
