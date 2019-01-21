package de.htwg.se.Kalaha.view.gui

import java.awt.{Color, Font}

import de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl.Controller
import de.htwg.se.Kalaha.util.{Observer, Point}

import scala.language.postfixOps
import scala.swing._
import scala.swing.event._

class Gui(controller: Controller) extends Frame with Observer {

  controller.addObserver(this)

  val height = 400
  val width = 1000
  val row = 2
  val col = 6
  var str = "Spieler 1 ist am Zug"

  var fieldButtons = Array.ofDim[FieldButton](row, col)

  var p = new Publisher {}

  title = "Kalaha"
  preferredSize = new Dimension(width, height)

  setButtons()

  val textPanel: TextField = new TextField() {
    editable = false
    background = Color.decode("#cc2023")
    text = str
  }

  val kalaha1: TextField = new TextField() {
    font = new Font("Arial", 0, 150)
    editable = false
    background = Color.decode("#cc2023")
    text = controller.board.gameboard(controller.p1).toString
    preferredSize = new Dimension(200, 600)
  }

  val kalaha2: TextField = new TextField() {
    font = new Font("Arial", 0, 150)
    background = Color.decode("#6365ff")
    //print("p2: " + controller.board.gameboard(0).toString)
    text = controller.board.gameboard(0).toString
    preferredSize = new Dimension(200, 600)
    editable = false
  }

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
        reset
        redraw()
      })
      contents += new MenuItem(Action("Als JSON speichern") {
        controller.save
      })
      contents += new MenuItem(Action("Spiel laden") {
        controller.load
      })
      contents += new MenuItem(Action("Spielregeln") {
        help()
      })
      contents += new MenuItem(Action("Beenden") {
        exit()
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") {
        controller.undo
        redraw()
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo
        redraw()
      })
    }
    contents += new Menu("Options") {
      mnemonic = Key.O
      contents += new MenuItem(Action("Mit 4 Kugeln starten") {
        controller.updateStones(4)
        controller.reset
        redraw()
      })
      contents += new MenuItem(Action("Mit 6 Kugeln starten") {
        controller.updateStones(6)
        controller.reset
        redraw()
      })
    }
  }

  visible = true
  //redraw()

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
      case b: ButtonClicked =>
        if (controller.board.round % 2 == 0) {
          if (x == 0) {
            val dia = Dialog.showConfirmation(contents.head, "Falscher Spieler", "Hinweis", optionType = Dialog.Options.Default)
          } else {
            if(controller.board.gameboard(y + 1) == 0) {
              val dia = Dialog.showConfirmation(contents.head, "Feld darf nicht leer sein", "Hinweis", optionType = Dialog.Options.Default)
            } else {
              controller.move(y + 1)
              redraw()
            }
          }

        } else if (controller.board.round % 2 == 1) {
          if (x == 1) {
            val dia = Dialog.showConfirmation(contents.head, "Falscher Spieler", "Hinweis", optionType = Dialog.Options.Default)
          } else {
            if(controller.board.gameboard(13 - y) == 0) {
              val dia = Dialog.showConfirmation(contents.head, "Feld darf nicht leer sein", "Hinweis", optionType = Dialog.Options.Default)
            } else {
              controller.move(13 - y)
              redraw()
            }
          }
        }
    }
  }

  def redraw(): Unit = {
    if (controller.board.round % 2 == 0) {
      textPanel.background = Color.decode("#cc2023")
      str = "Spieler 1 ist am Zug"
      textPanel.text_=(str)
    } else {
      textPanel.background = Color.decode("#6365ff")
      str = "Spieler 2 ist am Zug"
      textPanel.text_=(str)
    }
    for (x <- 0 until col) {
      fieldButtons(0)(x).text = "" + controller.board.gameboard(13 - x)
      fieldButtons(1)(x).text = "" + controller.board.gameboard(x + 1)
    }
    kalaha1.text = controller.board.gameboard(controller.p1).toString
    kalaha2.text = "" + controller.board.gameboard(0)

    repaint
  }

  def exit(): Unit = {
    val dia = Dialog.showConfirmation(contents.head, "Sind sie sicher das sie beenden wollen?", "Beenden", optionType = Dialog.Options.YesNo)
    if (dia == Dialog.Result.Yes) {
      controller.exit()
    }
  }

  def reset(): Unit = {
    val dia = Dialog.showConfirmation(contents.head, "Sind sie sicher das sie neu starten wollen?", "Neues Spiel", optionType = Dialog.Options.YesNo)
    if (dia == Dialog.Result.Yes) {
      controller.reset()
    }
  }

  def checkWin(): Unit = {
    controller.checkWin()
    if (controller.p2win && controller.p1win) {
      val dia = Dialog.showConfirmation(contents.head, "Unentschieden!", "Spielende", optionType = Dialog.Options.Default)
      controller.exit()
    }
    if(controller.p1win) {
      val str = "Spieler 1 gewinnt mit " + controller.board.gameboard(controller.p1) + " Punkten! Spieler 2 hat " + controller.board.gameboard(0) + " Punkte."
        val dia = Dialog.showConfirmation(contents.head, str, "Spielende", optionType = Dialog.Options.Default)
        controller.exit()
    } else if (controller.p2win) {
      val str = "Spieler 2 gewinnt mit " + controller.board.gameboard(0) + " Punkten! Spieler 2 hat " + controller.board.gameboard(controller.p1) + " Punkte."
      val dia = Dialog.showConfirmation(contents.head, str, "Spielende", optionType = Dialog.Options.Default)
      controller.exit()
    }
  }

  override def update(): Unit = {
    checkWin()
    redraw()
  }

  def help(): Unit = {
    var str = "je 6 (oder 4) Kugeln werden in die 12 kleinen Mulden gelegt \n\n Gewinner ist, wer bei Spielende die meisten Kugeln in seinem Kalaha hat.\n\n"
    str += "Wer am Zuge ist, leert eine seiner Mulden und verteilt die Kugeln, jeweils eine, reihum im Gegenuhrzeigersinn in die nachfolgenden Mulden. "
    str += "Dabei wird auch das eigene Kalaha gefüllt. Das Gegner Kalaha wird ausgelassen.\n\n"
    str += "Fällt die letzte Kugel ins eigene Kalaha, ist der Spieler nochmals am Zuge.\n\n"
    str += "Fällt die letzte Kugel in eine leere Mulde auf der eigenen Seite,  "
    str += "wird diese Kugel und alle Kugeln in der Gegner Mulde gegenüber, ins eigene Kalaha "
    str += "gelegt und der Gegner hat den nächsten Zug.\n\n"
    str += "Das Spiel ist beendet, wenn alle Mulden eines Spielers leer sind. Der Gegner bekommt dann alle Kugeln aus seinen Mulden in sein Kalaha.\n\n"
    val dia = Dialog.showConfirmation(contents.head, str, "Spielregeln", optionType = Dialog.Options.Default)
  }
}
