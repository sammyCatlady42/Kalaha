package de.htwg.se.Kalaha.model.fileIoComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import de.htwg.se.Kalaha.model.fileIoComponent.FileIOInterface
import de.htwg.se.Kalaha.model.gameboardController.GameboardImpl.Gameboard
import play.api.libs.json.Json

class FileIO extends FileIOInterface {

  override def load: Option[Gameboard] = {
    var gridOption: Option[Gameboard] = None

    gridOption
  }

  override def save(board: Gameboard) : Unit = {
    val pw = new PrintWriter(new File("board.json"))
    pw.write(Json.prettyPrint(board.toJson).toString)
    pw.close
  }

  //def boardToJson(board: Gameboard) = board.toJson
}