package de.htwg.se.Kalaha.view.tui

import de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl.Controller
import de.htwg.se.Kalaha.util.Observer

class Tui(controller: Controller) extends Observer {
  val four = 4
  controller.addObserver(this)

  def startGame(): Unit = {
    welcomeMsg()
    //print("\nSpieler " + Console.RED + "1 " + Console.RESET + "ist an der Reihe.") // player
    showGameboard()
    navigate()
    showGameboard()
  }

  def askForAmountStonesStart(): Unit = {
    print("\nfour oder 6 Steine? : ")
    var input = 0
    try {
      input = readUserInput()
    } catch {
      case e: NumberFormatException =>
        print("\nBitte richtige Werte angeben.")
        askForAmountStonesStart()
    }
    input match {
      case 4 => controller.controllerInit(four)
      case 6 => controller.controllerInit(6)
      case _ =>
        print("\nBitte richtige Werte angeben.")
        askForAmountStonesStart()
    }
  }


  def checkPlayer(): Unit = {
    val turn = controller.board.round % 2
    if (turn == 0) {
      print("\nSpieler " + Console.RED + "1 " + Console.RESET + "ist an der Reihe.")
    } else {
      print("\nSpieler " + Console.BLUE + "2 " + Console.RESET + "ist an der Reihe.")
    }
  }

  def navigate(): Unit = {
    checkPlayer()
    printHelp()
    var input = ""
    try {
      input = scala.io.StdIn.readLine()
    } catch {
      case _: Throwable => print("Felher beim lesen")
    }
    input match {
      case "option" => askForAmountStonesStart()
      case "help" => help()
      case "p" => startTurn()
      case "undo" => {
        try {
          controller.undo
        } catch {
          case e: Throwable => print(e)
        }
      }
      case "redo" => {
        try {
          controller.redo
        } catch {
          case e: Throwable => print(e)
        }
      }
      case "reset" => controller.reset
      case "exit" => controller.exit
      case "show" => showGameboard()
      case _ => {
        print("Falsche Eingabe\n")
      }
    }
    navigate()
  }

  def startTurn(): Unit = {
    var turn = controller.board.round % 2
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
      case true =>
        if (turn == 1) {
          input += 7
        }
        controller.move(input)
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
  def checkInputIFValid(index: Int): Boolean = index match {
    case x if 1 until 6 + 1 contains x =>
      var idx = index
      if (controller.board.round % 2 == 1) {
        idx += 7
      }
      if (controller.board.gameboard(idx) > 0) {
        true
      } else {
        false
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

  def help(): Unit = {
    print("je 6 (oder 4) Kugeln werden in die 12 kleinen Mulden gelegt \n\n Gewinner ist, wer bei Spielende die meisten Kugeln in seinem Kalaha hat.\n\n"
      + "Wer am Zuge ist, leert eine seiner Mulden und verteilt die Kugeln, jeweils eine, reihum im Gegenuhrzeigersinn in die nachfolgenden Mulden. "
      + "Dabei wird auch das eigene Kalaha gefüllt. Das Gegner Kalaha wird ausgelassen.\n\n"
      + "Fällt die letzte Kugel ins eigene Kalaha, ist der Spieler nochmals am Zuge.\n\n"
      + "Fällt die letzte Kugel in eine leere Mulde auf der eigenen Seite,  wird diese Kugel und alle Kugeln in der Gegner Mulde gegenüber, ins eigene Kalaha "
      + "gelegt und der Gegner hat den nächsten Zug.\n\n"
      + "Das Spiel ist beendet, wenn alle Mulden eines Spielers leer sind. Der Gegner bekommt dann alle Kugeln aus seinen Mulden in sein Kalaha.\n\n")
  }

  def showGameboard(): Unit = {
    var board = controller.board

    val gameboardString = new Array[String](14)
    for (i <- gameboardString.indices) {
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
        Console.BLUE + "|    | " + Console.RESET + gameboardString(1) + " | " + gameboardString(2) + " | " + gameboardString(3) + " | " + gameboardString(four) + " | " + gameboardString(5) + " | " + gameboardString(6) + Console.RED + " |    |\n" + Console.RESET +
        Console.RED + "--------1----2----3----4----5----6-------\n" + Console.RESET
    print(s)
  }

  def checkWin(): Unit = {
    controller.checkWin()
    if (controller.p2win && controller.p1win) {
      print("Unentschieden!\n")
      //controller.exit()
    }
    if (controller.p1win) {
      print("Spieler 1 gewinnt mit " + controller.board.gameboard(7) + "Punkten!\n")
      //controller.exit()
    } else if (controller.p2win) {
      print("Spieler 2 gewinnt mit " + controller.board.gameboard(0) + "Punkten!\n")
      //controller.exit()
    }
  }

  def printHelp(): Unit = {
    print("\nMögliche Eingaben:\n")
    print("     p => Zug des aktuellen Spielers starten\n")
    print("     help => Spielregeln\n")
    print("     option => Feld mit four oder 6 Kugeln\n")
    print("     show => Anzeigen des Spielfelds\n")
    print("     undo => Letzten Zug rückgängig machen\n")
    print("     redo => Letzten Undo rückgängig machen\n")
    print("     reset => Spielfeld auf Anfang zurücksetzten\n")
    print("     exit => Beenden\n")
  }

  override def update(): Unit = {
    showGameboard()
    printHelp()
    //checkWin()
  }

  //ruft theoretisch nur funktionen auf
}
