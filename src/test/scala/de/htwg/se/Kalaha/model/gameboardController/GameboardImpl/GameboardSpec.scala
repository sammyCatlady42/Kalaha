package de.htwg.se.Kalaha.model.gameboardController.GameboardImpl

import de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl.Controller
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsNumber, JsValue, Json}

@RunWith(classOf[JUnitRunner])
class GameboardSpec extends WordSpec with Matchers {
  "A GameBoard" when {
    val board = Gameboard()
    "initialized" should {
      "new init" in {
        board.boardInit()
        board.toString should be("06666660666666")
      }
      "new init with 6" in {
        board.boardInit(6)
        board.toString should be("06666660666666")
      }
      "new init with 4" in {
        board.boardInit(4)
        board.toString should be("04444440444444")
      }
      "set with array" in {
        var startboard: Array[Int] = Array[Int](15, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1)
        board.setBoard(startboard)
        board.toString should be("151111110111111")
      }

      "have empty space" in {
        board.startboard(0) should be(0)
        board.startboard(7) should be(0)
      }
      "have no field with 0 from 1 to 6" in {
        for (y <- 1 to 6) {
          board.startboard(y) shouldNot be(0)
        }
      }
      "have no field with 0 from 8 to 13" in {
        for (y <- 1 to 6) {
          board.startboard(y + 7) shouldNot be(0)
        }
      }
      "ToJson" in {
        val controller = new Controller
        val startFeld = "15000013000001200"
        val startboard: Array[Int] = Array[Int](15, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0)
        controller.board.setBoard(startboard)
        val testJson =
          Json.obj(
            "gameboard" -> Json.obj(
              "round" -> JsNumber(0),
              "board" -> Json.arr(
                Json.obj(
                  "0" -> 15,
                  "1" -> 2,
                  "2" -> 0,
                  "3" -> 0,
                  "4" -> 0,
                  "5" -> 0,
                  "6" -> 0,
                  "7" -> 2,
                  "8" -> 0,
                  "9" -> 0,
                  "10" -> 0,
                  "11" -> 1,
                  "12" -> 0,
                  "13" -> 0
                ))))
        controller.board.toJson should be(testJson)
      }
    }
  }
}