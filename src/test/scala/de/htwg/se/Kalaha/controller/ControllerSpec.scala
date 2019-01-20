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
          "do a redo but fails" in {
            try {
              controller.redo()
              fail()
            }
            catch {
              case _: IllegalArgumentException => // Expected, so continue
            }
          }
          "do an undo but fails" in {
            try {
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
      "move player 1 but fails cause empty field" in {
        controller.move(two)
        controller.board.toString should be("06077771766666")
      }
      "undo player 1" in {
        controller.undo()
        controller.board.toString should be(startFeld)
      }
      "redo player 1" in {
        controller.redo()
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
      "move player 2 but fails cause empty field" in {
        print(controller.board.round)
        controller.move(ten)
        controller.board.toString should be("17766660660777")
      }
      "undo player 2" in {
        controller.undo()
        controller.board.toString should be(startFeld)
      }
      "redo player 2" in {
        controller.redo()
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
      "move player 2 but fails cause empty field" in {
        print(controller.board.round)
        controller.move(11)
        controller.board.toString should be("1811101410110011")
      }
      "undo player 2" in {
        controller.undo()
        controller.board.toString should be(startFeld)
      }
      "redo player 2" in {
        controller.redo()
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
      "move but fails cause empty field" in {
        print(controller.board.round)
        controller.move(five)
        controller.board.toString should be(after)
      }
      "undo" in {
        controller.undo()
        controller.board.toString should be(startFeld)
      }
      "redo" in {
        controller.redo()
        controller.board.toString should be(after)
      }
    }
  }

//  "A Controller" when {
//    val controller = new Controller
//
//    controller.controllerInit(six)
//    val test = controller.board
//    val ausgabe = "06666660666666"
//    val ausgabe2 = "06077771766666"
//    val ausgabe1 = "00777771666666"
//    val testfeld = "00003000000010"
//
//    var testarray: Array[Int] = Array[Int](0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0)
//
//    "started" should {
//      val controller = new Controller
//      //          "new" in {
//      //            controller.controllerInit()
//      //            controller.board.toString should be("06666660666666")
//      //          }
//      "set updateStones" in {
//        controller.updateStones(five)
//        controller.amountStones should be(five)
//      }
//
//
//      "player 1 collectEnemyStones" in {
//        controller.board.round = four
//        val testArray: Array[Int] = Array[Int](0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 1, 0)
//        //      val testBoard = new Gameboard
//
//        controller.board.setBoard(testArray)
//        controller.move(six)
//        controller.board.toString should be("00000001111120")
//      }
//
//    }
//
//
//    "testarray" should {
//      test.setBoard(testarray)
//
//      //      "player 2 collectEnemyStones" in {
//      //        //        controller.board.round = 5
//      //
//      //        controller.checkExtra(four)
//      //        print("\ncheckExtra 4 - " + test + "-w-" + testfeld)
//      //        test.toString should be(testfeld)
//      //
//      //        controller.move(four)
//      //        print("\nmove 4 -" + test + "-w-" + testfeld)
//      //        test.toString should be("00000111000010")
//      //
//      //        controller.undo()
//      //        print("\nundo -" + test + "-w-" + testfeld)
//      //        test.toString should be(testfeld)
//      //
//      //        controller.redo()
//      //        print("\nredo -" + test + "-w-00000111000010")
//      //        test.toString should be("00000111000010")
//      //
//      //        controller.move(five)
//      //        print("\nmove 5 -" + test + "-w-00000021000010")
//      //        test.toString should be("00000021000010")
//      //        controller.move(12)
//      //        print("\nmove 12 -" + test + "-w-10000021000000")
//      //        test.toString should be("10000021000000")
//      //
//      //        //        controller.board.round = 4
//      //        //        controller.checkExtra(0)
//      //        //        print("\ncheckextra 12-" + test + "-w-10000021000000")
//      //        //        test.toString should be("10000021000000")
//      //        //
//      //        //
//      //        //
//      //        //        controller.collectEnemyStones(0)
//      //        //        print("\ncollectEnemyStones 12-" + test + "-w-10000003000000")
//      //        //        test.toString should be("10000003000000")
//      //        var tui = new Tui(controller)
//      //        tui.showGameboard()
//      //
//      //
//      //      }
//    }
//
//    "bla" should {
//      "ok " in {
//        var tui = new Tui(controller)
//        //        controller.move(6)
//        //        print("\nmove 6-" + test + "-w- 21100012000000")
//        //        test.toString should be("21100012000000")
//        //
//        //        controller.checkExtra(6)
//        //        print("\n" + test + "-w-10000021000000")
//        //        test.toString should be("10000021000000")
//        //
//        tui.showGameboard()
//      }
//    }
//    "Gameboard " should {
//      //      "get new initialized by String" in{
//      //        test.setBoardwithString(testfeld)
//      //        test.toString should be(testfeld)
//      //      }
//      "get new initialized by Array" in {
//        test.setBoard(testarray)
//        test.toString should be(testfeld)
//      }
//      "new set" should {
//        "with init 4" in {
//          controller.controllerInit(4)
//          val te = controller.board
//          val ausgabe = "04444440444444"
//          print("\n" + te + "---" + ausgabe)
//          te.toString should be(ausgabe)
//        }
//        "with init 6" in {
//          controller.controllerInit(six)
//          val te = controller.board
//          val ausgabe = "06666660666666"
//          print("\n" + te + "---" + ausgabe)
//          te.toString should be(ausgabe)
//        }
//      }
//      "move 2, undo, redo" in {
//        controller.move(2)
//        print("\n" + test + "---" + ausgabe2)
//        test.toString should be(ausgabe2)
//
//        controller.undo()
//        test.toString should be(ausgabe)
//
//        //        controller.redo()
//        //        test.toString should be(ausgabe2)
//      }
//      "player 1 move 2, undo, undo" in {
//        controller.controllerInit(six)
//        val te = controller.board
//        controller.move(2)
//        print("\n" + test + "---" + ausgabe2)
//        test.toString should be(ausgabe2)
//        controller.undo()
//        test.toString should be(ausgabe)
//
//
//        try {
//          controller.undo()
//          fail()
//        }
//        catch {
//          case _: IllegalArgumentException => // Expected, so continue
//        }
//
//      }
//      "move 1, undo, redo" in {
//        controller.move(1)
//        print("\n" + test + "---" + ausgabe1)
//        test.toString should be(ausgabe1)
//
//        controller.undo()
//        test.toString should be(ausgabe)
//
//        controller.redo()
//        test.toString should be(ausgabe1)
//      }
//
//
//    }
//
//    "on exit" in {
//      //      controller.exit() should be sys.exit(0)
//    }
//    "on reset" in {
//    }
//
//
//  }

}