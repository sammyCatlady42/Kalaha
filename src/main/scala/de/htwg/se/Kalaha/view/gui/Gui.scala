package de.htwg.se.Kalaha.view.gui

import java.awt.{Color, Font}

import de.htwg.se.Kalaha.controller.Controller
import de.htwg.se.Kalaha.util.Point

import scala.language.postfixOps
import scala.swing._
import scala.swing.event._

class Gui(controller: Controller) extends Frame {

  //val observable = new Observable
  //addObserver(observable)

  val height = 600
  val width = 900
  val row = 2
  val col = 6
  var str = "Spieler 1 ist am Zug"

  var fieldButtons = Array.ofDim[FieldButton](row, col)

  title = "Kalaha"
  preferredSize = new Dimension(width, height)

  setButtons()

  contents = new BorderPanel {
    add(textPanel, BorderPanel.Position.North)
    add(kalaha1, BorderPanel.Position.East)
    add(kalaha2, BorderPanel.Position.West)
    add(gridPanel, BorderPanel.Position.Center)
  }
  buttonActionListener()

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Neues Spiel") {
        controller.reset
        update()
      })
      contents += new MenuItem(Action("Beenden") {
        controller.exit()
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") {
        controller.undo
        update()
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo
        update()
      })
    }
    contents += new Menu("Options") {
      mnemonic = Key.O
      contents += new MenuItem(Action("Mit 4 Kugeln starten") {
        controller.updateStones(4)
        controller.reset
        update()
      })
      contents += new MenuItem(Action("Mit 6 Kugeln starten") {
        controller.updateStones(6)
        controller.reset
        update()
      })
    }
  }

  visible = true
  update()

  def label1: Label = new Label() {
    if (controller.round % 2 == 0) {
      println("textfeld player 1")
      background = Color.decode("#cc2023")
      text = str
    } else {
      println("textfeld player 2")
      background = Color.decode("#6365ff")
      text = "Spieler 2 ist am Zug"
    }
  }

  def textPanel: TextField = new TextField() {
    //ignoreRepaint = false
    //editable = true

var tester  = controller.round % 2 == 0
    print("rounnnnnnd                 - " + tester)
    if (tester) {
      //println("textfeld")
      background = Color.decode("#cc2023")
      text = "Spieler 1 ist am Zug"
    } else {
      background = Color.decode("#6365ff")
      text = "Spieler 2 ist am Zug"

    }
//    print("set text to  -------------------------" + textPanel.text)
    //editable = false
  }

  def kalaha1: TextField = new TextField() {
    font = new Font("Arial", 0, 150)
    //editable = false
    background = Color.decode("#cc2023")
    text = controller.board.gameboard(7).toString
    preferredSize = new Dimension(100, 600)
  }

  def kalaha2: TextField = new TextField() {
    font = new Font("Arial", 0, 150)
    background = Color.decode("#6365ff")
    print("p2: " + controller.board.gameboard(0).toString)
    text = controller.board.gameboard(0).toString
    preferredSize = new Dimension(100, 600)
    //editable = false
  }

  def gridPanel: GridPanel = new GridPanel(row, col) {
    preferredSize = new Dimension(600, 600)
    for (x <- 0 until row) {
      for (y <- 0 until col) {
        contents += fieldButtons(x)(y)
      }
    }
    print("-------------------------test")

  }

  def setButtons(): Unit = {
    for (x <- 0 until col) {
      fieldButtons(0)(x) = new FieldButton(Point(0, x))
      fieldButtons(0)(x).background = Color.decode("#6365ff")
      fieldButtons(0)(x).text = "" + controller.board.gameboard(13 - x)
      fieldButtons(0)(x).font = new Font("Arial", 0, 50)
      fieldButtons(1)(x) = new FieldButton(Point(1, x))
      fieldButtons(1)(x).background = Color.decode("#cc2023") //java.awt.Color.RED
      fieldButtons(1)(x).text = "" + controller.board.gameboard(x + 1)
      fieldButtons(1)(x).font = new Font("Arial", 0, 50)
    }
  }

  def buttonActionListener(): Unit = {
    for {
      x <- 0 until row
      y <- 0 until col
    } fieldButtons(x)(y).reactions += {
      case _: ButtonClicked =>
        if (controller.round % 2 == 0) {
          println("Hallo")
          if (x == 0) {
            println("Falsch")
            // TODO: Popup: Falsch
          } else {
            controller.move(y + 1)
            update()
          }

        } else if (controller.round % 2 == 1) {
          if (x == 1) {
            // TODO: Popup: Falsch
          } else {
            controller.move(13 - y)
            update()
          }
        }

    }
  }

  def update(): Unit = {
    if (controller.round % 2 == 1) {
      label1.background = Color.decode("#cc2023")
      //str = "Spieler 1 ist am Zug"

      //label1.text_=(str)
      //label1.revalidate()
      //label1.repaint()
    } else {
      label1.background = Color.decode("#6365ff")
      //str = "Spieler 2 ist am Zug"
      //label1.revalidate()
      //label1.repaint()
    }
    for (x <- 0 until col) {
      fieldButtons(0)(x).text = "" + controller.board.gameboard(13 - x)
      fieldButtons(1)(x).text = "" + controller.board.gameboard(x + 1)
    }
    kalaha1.text = controller.board.gameboard(7).toString
    //kalaha1.repaint()
    kalaha2.text = "" + controller.board.gameboard(0)
    //kalaha2.repaint()


textPanel.text = "test"

    println("repaint")
    repaint()
  }
}
