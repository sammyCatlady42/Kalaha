package de.htwg.se.Kalaha.view.tui

import de.htwg.se.Kalaha.controller.Controller
import de.htwg.se.Kalaha.observer.Observable

class Tui(controller: Controller) extends Observable{

  controller.addObserver(this)

  def startGame(): Unit = {
    welcomeMsg()
    askForAmountStonesStart()
    print("\nSpieler " + Console.RED + "1 " + Console.RESET + "ist an der Reihe.") // player
    showGameboard()
    startTurn()
    showGameboard()
  }

  def askForAmountStonesStart(): Unit = {
    print("\n4 oder 6 Steine? : ")
    var input = 0
    try {
      input = readUserInput()
    } catch {
      case e: NumberFormatException =>
        print("\nBitte richtige Werte angeben.")
        askForAmountStonesStart()
    }
    input match {
      case 4 => controller.controllerInit(4)
      case 6 => controller.controllerInit(6)
    }
  }

  def startTurn(): Unit = {
    print("\nWähle eine Mulde : ")
    var input = 0
    try {
      input = readUserInput()
    } catch {
      case e: NumberFormatException =>
        print("\nBitte Zahlenwerte angeben.")
        startTurn()
    }
    checkInputIFValid(input) match {
      case false =>
        print("\nBitte richtige Werte angeben.")
        startTurn()
      case true => controller.move(input)
    }
  }

  def readUserInput(): Int = {
    val a = scala.io.StdIn.readInt()
    print("The value of a is " + a)
    a
  }

  /** *
    * checkInputIFValid
    *
    * @param index userinput
    */
  def checkInputIFValid(index: Int): Any = index match {
    case x if 1 until 6 + 1 contains x => true
    case _ => false
  }

  def welcomeMsg(): Unit ={
    var s = ""
    s += "\n"
    s += "Welcome to Kalaha!! :D\n" +
      "Spielregeln: ....."
    //TODO: Helpinfos und spielregeln
    print(s)
  }

  def showGameboard(): Unit = {
    var board = controller.board
    var s = ""
    s += "\n"
    s +=
      Console.BLUE +"------1---2---3---4---5---6------\n" + Console.RESET +
      "|   | " + board.gameboard(13) + " | " + board.gameboard(12) + " | " + board.gameboard(11) + " | " + board.gameboard(10) + " | " + board.gameboard(9) + " | " + board.gameboard(8) + " |   |\n" +
      "| " + board.gameboard(0) + " |-----------------------| " + board.gameboard(7) + " |\n" +
      "|   | " + board.gameboard(1) + " | " + board.gameboard(2) + " | " + board.gameboard(3) + " | " + board.gameboard(4) + " | " + board.gameboard(5) + " | " + board.gameboard(6) + " |   |\n" +
      Console.RED +"------1---2---3---4---5---6------\n" + Console.RESET
    print(s)
  }

  //ruft theoretisch nur funktionen auf
}
