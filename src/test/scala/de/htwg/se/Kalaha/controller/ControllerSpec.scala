package scala.de.htwg.se.Kalaha.controller

import de.htwg.se.Kalaha.controller.Controller
import de.htwg.se.Kalaha.model.Gameboard
import de.htwg.se.Kalaha.view.tui.Tui
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


  "A Controller" when {
    val controller = new Controller

    controller.controllerInit(six)
    val test = controller.board
    val ausgabe = "06666660666666"
    val ausgabe2 = "06077771766666"
    val ausgabe1 = "00777771666666"
    val testfeld = "00003000000010"

    var testarray: Array[Int] = Array[Int](0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0)

    "testarray" should {
      test.setBoard(testarray)

//      "player 2 collectEnemyStones" in {
//        //        controller.round = 5
//
//        controller.checkExtra(four)
//        print("\ncheckExtra 4 - " + test + "-w-" + testfeld)
//        test.toString should be(testfeld)
//
//        controller.move(four)
//        print("\nmove 4 -" + test + "-w-" + testfeld)
//        test.toString should be("00000111000010")
//
//        controller.undo()
//        print("\nundo -" + test + "-w-" + testfeld)
//        test.toString should be(testfeld)
//
//        controller.redo()
//        print("\nredo -" + test + "-w-00000111000010")
//        test.toString should be("00000111000010")
//
//        controller.move(five)
//        print("\nmove 5 -" + test + "-w-00000021000010")
//        test.toString should be("00000021000010")
//        controller.move(12)
//        print("\nmove 12 -" + test + "-w-10000021000000")
//        test.toString should be("10000021000000")
//
//        //        controller.round = 4
//        //        controller.checkExtra(0)
//        //        print("\ncheckextra 12-" + test + "-w-10000021000000")
//        //        test.toString should be("10000021000000")
//        //
//        //
//        //
//        //        controller.collectEnemyStones(0)
//        //        print("\ncollectEnemyStones 12-" + test + "-w-10000003000000")
//        //        test.toString should be("10000003000000")
//        var tui = new Tui(controller)
//        tui.showGameboard()
//
//
//      }
    }

    "bla" should {
      "ok " in {
        var tui = new Tui(controller)
        //        controller.move(6)
        //        print("\nmove 6-" + test + "-w- 21100012000000")
        //        test.toString should be("21100012000000")
        //
        //        controller.checkExtra(6)
        //        print("\n" + test + "-w-10000021000000")
        //        test.toString should be("10000021000000")
        //
        tui.showGameboard()
      }
    }
    "Gameboard " should {
      //      "get new initialized by String" in{
      //        test.setBoardwithString(testfeld)
      //        test.toString should be(testfeld)
      //      }
      "get new initialized by Array" in {
        test.setBoard(testarray)
        test.toString should be(testfeld)
      }
      "new set" should {
        "with init 4" in {
          controller.controllerInit(4)
          val te = controller.board
          val ausgabe = "04444440444444"
          print("\n" + te + "---" + ausgabe)
          te.toString should be(ausgabe)
        }
        "with init 6" in {
          controller.controllerInit(six)
          val te = controller.board
          val ausgabe = "06666660666666"
          print("\n" + te + "---" + ausgabe)
          te.toString should be(ausgabe)
        }
      }
      "move 2, undo, redo" in {
        controller.move(2)
        print("\n" + test + "---" + ausgabe2)
        test.toString should be(ausgabe2)

        controller.undo()
        test.toString should be(ausgabe)

        //        controller.redo()
        //        test.toString should be(ausgabe2)
      }
      "player 1 move 2, undo, undo" in {
        controller.controllerInit(six)
        val te = controller.board
        controller.move(2)
        print("\n" + test + "---" + ausgabe2)
        test.toString should be(ausgabe2)
        controller.undo()
        test.toString should be(ausgabe)


        try {
          controller.undo()
          fail()
        }
        catch {
          case _: IllegalArgumentException => // Expected, so continue
        }

      }
      "move 1, undo, redo" in {
        controller.move(1)
        print("\n" + test + "---" + ausgabe1)
        test.toString should be(ausgabe1)

        controller.undo()
        test.toString should be(ausgabe)

        controller.redo()
        test.toString should be(ausgabe1)
      }
      "reset" in {
        controller.reset()
        print("\n" + test + "---" + ausgabe)
        test.toString should be(ausgabe)
      }

    }

    "on exit" in {
      //      controller.exit() should be sys.exit(0)
    }
    "on reset" in {
    }


  }
  "started" should {
    val controller = new Controller
//    "new" in {
//      controller.controllerInit()
//      controller.board.toString should be("06666660666666")
//    }
    "set updateStones" in {
      controller.updateStones(five)
      controller.amountStones should be(five)
    }


    "player 1 collectEnemyStones" in {
      controller.round = four
      val testArray: Array[Int] = Array[Int](0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 1, 0)
      //      val testBoard = new Gameboard

      controller.board.setBoard(testArray)
      controller.move(six)
      controller.board.toString should be("00000001111120")
    }
    "move" in {}
    "move 2" in {}
    "move 3" in {}
    "not move an empty field" in {}
    "do an undo" in {}
    "do a redo" in {}
    "do an undo but fails" in {}
    "do a redo but fails" in {}
  }
}