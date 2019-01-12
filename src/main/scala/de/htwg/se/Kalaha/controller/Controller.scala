package de.htwg.se.Kalaha.controller

import de.htwg.se.Kalaha.model.Gameboard

class Controller {
  var round = 0
  var board = new Gameboard

  def controllerInit(): Unit = {
    board.boardInit
  }

  def startGame(): Unit = {
   // print("Spieler " + player + " ist an der Reihe.")

  }

  def startTurn(): Unit = {
    print("\nWähle eine Mulde : ")
    var input = 0
    try {
      input = readUserInput()
    }catch {
      case e: NumberFormatException =>
        print("\nBitte richtige Werte angeben.")
        startTurn()
    }
    checkInputIFValid(input) match {
      case false =>
        print("\nBitte richtige Werte angeben.")
        startTurn()
      case true => move(input)
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
    case x if 1 until 7 contains x => true
    case _ => false
  }

  //    if (index == 0 || index == 7){
  //      //TODO: error message
  //      print("Bad Input\n")
  //      for (i <- 0 until board.gameboard.length-1)
  //        print(board.gameboard(i))
  //      print("\n")
  //      return
  //    }


  def move(index: Int): Unit = {
    print("index = " + index)

    board.oldgb = board.gameboard.clone()
    val x: Int = board.gameboard(index)
    board.gameboard(index) = 0
    var v = 0
    for (i <- 1 until x + 1) {
      if ((round % 2 == 0 && i == 7) || (round % 2 == 1 && i == 1)) {
        print("turn: " + round % 2 + " i = " + i + " x = " + x + " skip\n")
        //v += 1
        if (index + i + 1 >= board.gameboard.length) {
          val y: Int = index + x - board.gameboard.length
          board.gameboard(y + 1 + v) += 1
        } else {
          board.gameboard(index + x + v) += 1
        }
      } else {
        if (index + i + 1 >= board.gameboard.length) {
          val y: Int = index + i - board.gameboard.length
          board.gameboard(y + 1 + v) += 1
        } else {
          board.gameboard(index + i + v) += 1
        }
      }
    }
    round += 1
    print(board.toString())
  }

  def undo: Unit = {
    board.gameboard = board.oldgb.clone()
    round -= 1
    print("undo \n")
    print(board.toString)
  }

  def reset: Unit = {
    board.boardInit
  }

  def win: Int = {
    var x: Int = 0
    for (i <- 0 until 6) {
      x += board.gameboard(i + 1)
      print(x)
    }
    var y: Int = 0
    for (i <- 0 until 6)
      y += board.gameboard(i + 7)

    if (x == 0) {
      1
    } else if (y == 0) {
      2
    } else {
      0
    }
  }

  def setWinp1: Unit = {
    var winboard: Array[Int] = Array[Int](0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 6, 6)
    board.gameboard = winboard.clone
  }
}
