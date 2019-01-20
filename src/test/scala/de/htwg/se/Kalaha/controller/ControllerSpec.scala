package scala.de.htwg.se.Kalaha.controller

import de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl.Controller
import de.htwg.se.Kalaha.model.gameboardController.GameboardImpl.Gameboard
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    val controller = new Controller

    controller.controllerInit(6)
    val test = controller.board
    val ausgabe = "06666660666666"
    val ausgabe2 = "06077771766666"
    val ausgabe1 = "00777771666666"
    val testfeld = "00003000000010"
    var testarray: Array[Int] = Array[Int](0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0)

    "testarray" should {
        test.setBoard(testarray)

      "player 2 collectEnemyStones" in {
        controller.checkExtra(4)
        print("\ncheckExtra 4 - " + test + "-w-" +testfeld)
        test.toString should be(testfeld)

        controller.move(4)
        print("\nmove 4 -" + test + "-w-" +testfeld)
        test.toString should be("00000111000010")

        controller.undo()
        print("\nundo -" + test + "-w-" +testfeld)
        test.toString should be(testfeld)

        controller.redo()
        print("\nredo -" + test + "-w-00000111000010")
        test.toString should be("00000111000010")

        controller.move(5)
        print("\nmove 5 -" + test + "-w-00000021000010")
        test.toString should be("00000021000010")
        controller.move(13)
        print("\nmove 13 -" + test + "-w-00000102000001" )
        test.toString should be("00000102000001")
//        controller.move(5)
//        print("\n" + test + "-w-11100012000001")
//        test.toString should be("11100012000001")
//        controller.move(6)
//        print("\n" + test + "-w- 21100012000000")
//        test.toString should be("21100012000000")

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
          controller.controllerInit(6)
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
      "move 2, undo, undo" in {
        controller.move(2)
        print("\n" + test + "---" + ausgabe2)
        test.toString should be(ausgabe2)

        controller.undo()
        test.toString should be(ausgabe)

        //        controller.undo()
        //        test.toString should be(ausgabe2)
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
    "move" in {}
    "move 2" in {}
    "move 3" in {}
    "not move an empty field" in {}
    "do an undo" in {}
    "do a redo" in {}
  }
}