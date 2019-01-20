package de.htwg.se.Kalaha.view.gui

import de.htwg.se.Kalaha.util.Point

import scala.swing.Button

class FieldButton(point: Point) extends Button {

  def getPoint(): Point = {
    point
  }
}