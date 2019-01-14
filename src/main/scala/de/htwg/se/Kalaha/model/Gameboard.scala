package de.htwg.se.Kalaha.model

case class Gameboard() {
  val SIZE = 14
  val stones = 6
  var startboard : Array[Int] = Array[Int] (0, stones, stones, stones, stones, stones, stones, 0, stones, stones, stones, stones, stones, stones)
  var gameboard = new Array[Int] (SIZE)
  var oldgb = new Array[Int] (SIZE)

  def boardInit: Unit = {
    gameboard = startboard.clone()
  }

  override def toString: String = {
    var s: String = ""
    for (i <- 0 until gameboard.length-1)
      s += gameboard(i)
    s += "\n"
    s
  }
}
