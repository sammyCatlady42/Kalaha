package de.htwg.se.Kalaha.model.gameboardController.GameboardImpl

import de.htwg.se.Kalaha.model.gameboardController.GameboardInterface
import play.api.libs.json._

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

  def toJson:JsValue = {
    Json.obj(
      "gameboard" -> Json.obj(
        "round" -> JsNumber(round),
        "board" -> Json.arr(
          Json.obj(
            "0" -> gameboard(0),
            "1" -> gameboard(1),
            "2" -> gameboard(2),
            "3" -> gameboard(3),
            "4" -> gameboard(4),
            "5" -> gameboard(5),
            "6" -> gameboard(6),
            "7" -> gameboard(7),
            "8" -> gameboard(8),
            "9" -> gameboard(9),
            "10" -> gameboard(10),
            "11" -> gameboard(11),
            "12" -> gameboard(12),
            "13" -> gameboard(13),


          )
          //gameboard
        )
      )
    )
  }
}
