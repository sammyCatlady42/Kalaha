package scala.de.htwg.se.Kalaha.model

import de.htwg.se.Kalaha.model.Gameboard
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameboardSpec extends WordSpec with Matchers {
  "A GameBoard" when {
    val board = Gameboard()
    "initialized" should {
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