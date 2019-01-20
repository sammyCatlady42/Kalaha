package scala.de.htwg.se.Kalaha.controller

import de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl.Controller
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  val zero = 0
  val one = 1
  val two = 2
  val four = 4
  val five = 5
  val six = 6
  val ten = 10
  
  "A controller" when {
    val controller = new Controller
    controller.controllerInit(six)
    val startFeld = "06666660666666"
        "in game general" should {
          "reset" in {
            controller.reset()
            controller.board.toString should be(startFeld)
          }
          "exit" in {
            //      controller.exit() should be sys.exit(0)
          }
          "do a redo but fails" in {
            try {
              controller.undone = false
              controller.redo()
              fail()
            }
            catch {
              case _: IllegalArgumentException => // Expected, so continue
            }
          }
          "do an undo but fails" in {
            try {
              controller.undone = true
              controller.undo()
              fail()
            }
            catch {
              case _: IllegalArgumentException => // Expected, so continue
            }
          }
        }
  }
  "A controller" when {
    val controller = new Controller
    controller.controllerInit(six)
    val startFeld = "06666660666666"
    "in game player 1" should {
      "move player 1" in {
        controller.move(two)
        controller.board.toString should be("06077771766666")
      }
      "undo after move" in {
        controller.undo()
        controller.board.toString should be(startFeld)
      }
      "redo player 1" in {
        controller.redo()
        controller.board.toString should be("06077771766666")
      }
      "move player 1 but fails cause empty field" in {
        controller.move(two)
        controller.board.toString should be("06077771766666")
      }
    }
  }
  "A controller" when {
    val controller = new Controller
    controller.controllerInit(six)
    val startFeld = "06666660666666"
    "in game player 2" should {
      controller.controllerInit(six)
      controller.board.round = five
      "move player 2" in {
        controller.move(ten)
        controller.board.toString should be("17766660660777")
      }
      "undo after move" in {
        controller.undo()
        controller.board.toString should be(startFeld)
      }
      "redo player 2" in {
        controller.redo()
        controller.board.toString should be("17766660660777")
      }
      "move player 2 but fails cause empty field" in {
        print(controller.board.round)
        controller.move(ten)
        controller.board.toString should be("17766660660777")
      }
    }
  }
  "A BIG game" when {
    val controller = new Controller
    val startFeld = "15000013000001200"
    val startboard: Array[Int] = Array[Int](15, zero, zero, zero, zero, 13, zero, zero, zero, zero, zero, 12, zero, zero)
    controller.board.setBoard(startboard)
    "in BIG game player 2" should {
      controller.board.round = five
      "to string" in {
        controller.board.toString should be(startFeld)
      }
      "move player 2" in {
        controller.move(11)
        controller.board.toString should be("1811101410110011")
      }
      "undo after move" in {
        controller.undo()
        controller.board.toString should be(startFeld)
      }
      "redo player 2" in {
        controller.redo()
        controller.board.toString should be("1811101410110011")
      }
      "move player 2 but fails cause empty field" in {
        print(controller.board.round)
        controller.move(11)
        controller.board.toString should be("1811101410110011")
      }
    }
  }
  "A BIG game" when {
    val controller = new Controller
    val startFeld = "15000012000001200"
    val after = "1511100131101311"
    val startboard: Array[Int] = Array[Int](15, zero, zero, zero, zero, 12, zero, zero, zero, zero, zero, 12, zero, zero)
    controller.board.setBoard(startboard)
    "in BIG game player 1" should {
      controller.board.round = four
      "to string" in {
        controller.board.toString should be(startFeld)
      }
      "move" in {
        controller.move(five)
        controller.board.toString should be(after)
      }
      "undo after move" in {
        controller.undo()
        controller.board.toString should be(startFeld)
      }
      "redo" in {
        controller.redo()
        controller.board.toString should be(after)
      }
      "move but fails cause empty field" in {
        print(controller.board.round)
        controller.move(five)
        controller.board.toString should be(after)
      }
    }
  }


  "A controller" when {
    "player 2 wins" should {
      val controller = new Controller
      val startFeld = "152000002000100"
      val winField = "180000002000000"
      val startboard: Array[Int] = Array[Int](15, two, zero, zero, zero, zero, zero, 2, zero, zero, zero, 1, zero, zero)
      controller.board.setBoard(startboard)
      "checkWin but nobodys win" in{
        controller.checkWin()
        controller.board.toString should be (startFeld)
      }
      "checkWin" in{
        controller.move(11)
        controller.move(12)
        controller.checkWin()
        controller.board.toString should be (winField)
      }
    }
  }
  "A controller" when {
    "player 1 wins" should {
      val controller = new Controller
      val startFeld = "152000002000100"
      val winField = "500000015000000"
      val startboard: Array[Int] = Array[Int](two, two, zero, zero, zero, zero, zero, 15, zero, zero, zero, 1, zero, zero)
      controller.board.setBoard(startboard)
      "checkWin" in{
        controller.move(11)
        controller.move(12)
        controller.checkWin()
        controller.board.toString should be (winField)
      }
    }
  }
  "A controller" when{
    "new" should {
      val controller = new Controller
      val startFeld6 = "06666660666666"
      "controllerInit"in{
        controller.controllerInit(six)
        controller.board.toString should be(startFeld6)
      }
      "updateStones"in{
        controller.updateStones(four)
        controller.amountStones should be(four)
      }
    }
  }
  "A controller" when {
    "Tie" should {
      val controller = new Controller
      val startFeld = "22000005000100"
      val winField = "50000005000000"
      val startboard: Array[Int] = Array[Int](two, two, zero, zero, zero, zero, zero, 5, zero, zero, zero, 1, zero, zero)
      controller.board.setBoard(startboard)
      "checkWin" in{
        controller.move(11)
        controller.move(12)
        controller.checkWin()
        controller.board.toString should be (winField)
      }
    }
  }
}