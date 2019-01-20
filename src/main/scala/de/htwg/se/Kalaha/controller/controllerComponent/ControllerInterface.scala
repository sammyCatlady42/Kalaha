package de.htwg.se.Kalaha.controller.controllerComponent

trait ControllerInterface {

  def controllerInit(amountStonesStart: Int): Unit

  def controllerInit(): Unit

  def updateStones(x: Int): Unit

  def move(inputIndex: Int): Unit

  def collectEnemyStones(last: Int): Unit

  def checkExtra(last: Int): Unit

  def undo(): Unit

  def redo(): Unit

  def reset(): Unit

  def checkWin(): Unit

  def win(): Unit

  def exit(): Unit

  def save: Unit

}
