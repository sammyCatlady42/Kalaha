package de.htwg.se.Kalaha.model.gameboardController

trait GameboardInterface {

  def boardInit(amountStonesStart: Int): Unit

  def boardInit(): Unit

  def setBoard(newBoard: Array[Int]): Unit

  override def toString: String

}
