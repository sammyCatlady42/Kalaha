package de.htwg.se.Kalaha.model.fileIoComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl.Controller
import de.htwg.se.Kalaha.model.fileIoComponent.FileIOInterface
import de.htwg.se.Kalaha.model.gameboardController.GameboardImpl.Gameboard
import play.api.libs.json.{JsValue, Json}

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load(Controller: Controller): Unit = {
    val source: String = Source.fromFile("board.json").getLines.mkString
    val json: JsValue = Json.parse(source)

    val round = (json \ "gameboard" \ "round").get.toString().toInt
    val boardjson = Json.parse("" + (json \ "gameboard" \\ "board").head + "")
    val test = boardjson.validate[List[JsValue]].get
    var x = 0
    var field = new Array[Int](14)
    for (i <- test) {
      print(i + " x : " + x)
      val t = (i \ x).get.toString().toInt
      field(x) = t
      x = x + 1
    }
    Controller.board.round = round
    Controller.board.setBoard(field)
    //val board: List[JsValue] = Json.parse(boardjson).as[List[JsValue]]
    //val eins = board.
    /*val zwei = (json \ "gameboard" \ "board" \ "2").get.toString().toInt
    val drei = (json \ "gameboard" \ "board" \ "3").get.toString().toInt
    val vier = (json \ "gameboard" \ "board" \ "4").get.toString().toInt
    val fünf = (json \ "gameboard" \ "board" \ "5").get.toString().toInt
    val sechs = (json \ "gameboard" \ "board" \ "6").get.toString().toInt*/

    print("Was Geht " + round )
    Controller.notifyObservers

  }

  override def save(board: Gameboard) : Unit = {
    val pw = new PrintWriter(new File("board.json"))
    pw.write(Json.prettyPrint(board.toJson).toString)
    pw.close
  }

  //def boardToJson(board: Gameboard) = board.toJson
}