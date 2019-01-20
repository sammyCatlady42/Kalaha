package de.htwg.se.Kalaha.util

trait Command {

  def doStep: Unit
  def undoStep: Unit
  def redoStep: Unit

}