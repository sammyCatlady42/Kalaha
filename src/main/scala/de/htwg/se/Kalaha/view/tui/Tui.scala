package de.htwg.se.Kalaha.view.tui

import de.htwg.se.Kalaha.controller.Controller
import de.htwg.se.Kalaha.observer.Observable

class Tui(controller: Controller) extends Observable{

  //controller.addObserver(this)

  def startGame(): Unit = {
    welcomeMsg()
    askForAmountStonesStart()
    //print("\nSpieler " + Console.RED + "1 " + Console.RESET + "ist an der Reihe.") // player
    showGameboard()
    navigate()
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
      case _ =>
        print("\nBitte richtige Werte angeben.")
        askForAmountStonesStart()
    }
  }

  def navigate(): Unit = {
    val turn = controller.round % 2
    if (turn == 0) {
      print("\nSpieler " + Console.RED + "1 " + Console.RESET + "ist an der Reihe.")
    } else {
      print("\nSpieler " + Console.BLUE + "2 " + Console.RESET + "ist an der Reihe.")
    }
    print("\nMögliche Eingaben:\n")
    print("     p => Zug des aktuellen Spielers starten\n")
    print("     show => Anzeigen des Spielfelds\n")
    print("     undo => Letzten Zug rückgängig machen\n")
    print("     redo => Letzten Undo rückgängig machen\n")
    print("     reset => Spielfeld auf Anfang zurücksetzten\n")
    print("     exit => Beenden\n")
    var input = ""
    try {
      input = scala.io.StdIn.readLine()
    } catch {
      case _ => print("Felher beim lesen")
    }
    input match {
      case "p" => startTurn()
      case "undo" => {
        try {
          controller.undo
        } catch {
          case e => print(e)
        }
      }
      case "redo" => {
        try {
          controller.redo
        } catch {
          case e => print(e)
        }
      }
      case "reset" => controller.reset
      case "exit" => controller.exit
      case "show" => showGameboard()
      case _  => {
        print("Falsche Eingabe\n")
      }
    }
    navigate()
  }

  def startTurn(): Unit = {
    var turn = controller.round % 2
    if (turn == 0) {
      print("\nSpieler " + Console.RED + "1 " + Console.RESET + "ist an der Reihe.")
    } else {
      print("\nSpieler " + Console.BLUE + "2 " + Console.RESET + "ist an der Reihe.")
    }
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
    showGameboard()
    navigate()
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
    case x if 1 until 6 + 1 contains x => {
      var idx = index
      if (controller.round % 2 == 1) {
        idx += 7
      }
      if (controller.board.gameboard(idx) > 0) {
        true
      } else {
        false
      }
    }
    case _ => false
  }

  def welcomeMsg(): Unit = {
    var s = ""
    s += "\n"
    s += "Welcome to Kalaha!! :D\n" +
      "Spielregeln: ....."
    //TODO: Helpinfos und spielregeln
    print(s)
  }

  def showGameboard(): Unit = {
    var board = controller.board

    val gameboardString = new Array[String](14)
    for (i <- 0 until gameboardString.length) {
      if (board.gameboard(i) < 10) {
        gameboardString(i) = " " + board.gameboard(i)
      }
      else {
        gameboardString(i) = "" + board.gameboard(i)
      }
    }
    var s = ""
    s += "\n"
    s +=
      Console.BLUE + "--------6----5----4----3----2----1-------\n" + Console.RESET +
        Console.BLUE + "|    | " + Console.RESET + gameboardString(13) + " | " + gameboardString(12) + " | " + gameboardString(11) + " | " + gameboardString(10) + " | " + gameboardString(9) + " | " + gameboardString(8) + Console.RED + " |    |\n" + Console.RESET +
        Console.BLUE + "| " + gameboardString(0) + " |" + Console.RESET + "-----------------------------" + Console.RED + "| " + gameboardString(7) + " |\n" + Console.RESET +
        Console.BLUE + "|    | " + Console.RESET + gameboardString(1) + " | " + gameboardString(2) + " | " + gameboardString(3) + " | " + gameboardString(4) + " | " + gameboardString(5) + " | " + gameboardString(6) + Console.RED + " |    |\n" + Console.RESET +
        Console.RED + "--------1----2----3----4----5----6-------\n" + Console.RESET
    print(s)
  }

  //ruft theoretisch nur funktionen auf
}
