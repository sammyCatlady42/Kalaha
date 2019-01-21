package de.htwg.se.Kalaha.model.fileIoComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}
import de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl.Controller
import de.htwg.se.Kalaha.controller.controllerComponent.ControllerInterface
import de.htwg.se.Kalaha.model.fileIoComponent.FileIOInterface
import de.htwg.se.Kalaha.model.gameboardController.GameboardImpl.Gameboard
import play.api.libs.json.{JsValue, Json}

import scala.io.Source

class FileIO extends FileIOInterface {
  var round = 0
  var boardArray = new Array[Int](14)

  override def load(controller: Controller): Unit = {
    val source1: String = Source.fromFile("D:\\board.json").getLines.mkString
    val json1: JsValue = Json.parse(source1)
    loadRound(json1, controller)
    loadBoard(json1, controller)
    controller.board.round = round
    controller.board.setBoard(boardArray)

    controller.notifyObservers
  }

  def loadRound(json: JsValue, controller: ControllerInterface): Unit = {
    round = (json \ "gameboard" \ "round").get.toString().toInt
  }

  def loadBoard(json: JsValue, controller: ControllerInterface): Unit = {
    val board = (json \ "gameboard" \ "board").get.toString()
    val jsonList: List[JsValue] = Json.parse(board).as[List[JsValue]]

    for (feld <- jsonList) {
      for (i: Int <- 0 until 14) {
        boardArray(i) = (feld \ i.toString).get.toString().toInt
      }
    }
  }

  override def save(board: Gameboard): Unit = {
    val pw = new PrintWriter(new File("D:\\board.json"))
    pw.write(Json.prettyPrint(board.toJson).toString)
    pw.close()
    print("Spielstand wurde als Json gespeichert.")
  }

  //def boardToJson(board: Gameboard) = board.toJson
}