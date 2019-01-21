package scala.de.htwg.se.Kalaha.model

import de.htwg.se.Kalaha.model.gameboardController.GameboardImpl.Gameboard
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

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
        print("Feld " + 0 + "-- Inhalt " + board.startboard(0) + "\n")
        board.startboard(0) should be(0)
        print("Feld " + 7 + "-- Inhalt " + board.startboard(7) + "\n")
        board.startboard(7) should be(0)
      }
      "have no field with 0 from 1 to 6" in {
        for (y <- 1 to 6) {
          print("Feld " + y + "-- Inhalt " + board.startboard(y) + "\n")
          board.startboard(y) shouldNot be(0)
        }
      }
      "have no field with 0 from 8 to 13" in {
        for (y <- 1 to 6) {
          print("Feld " + (y + 7) + "-- Inhalt " + board.startboard(y + 7) + "\n")
          board.startboard(y + 7) shouldNot be(0)
        }
      }
    }
  }
}