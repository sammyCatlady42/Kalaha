package de.htwg.se.Kalaha.model.fileIoComponent

import de.htwg.se.Kalaha.model.gameboardController.GameboardImpl.Gameboard

trait FileIOInterface {

  def load:Option[Gameboard]
  def save(board:Gameboard):Unit

}
