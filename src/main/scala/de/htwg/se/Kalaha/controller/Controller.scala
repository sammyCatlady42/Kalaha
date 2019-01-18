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
    case x if 1 until board.stones+1 contains x => true
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
    print("index = " + index + "\n")
    print("Turn = " + round % 2 + "\n")
    var idx = index
    board.oldgb = board.gameboard.clone()
    val countStonesInMuld: Int = board.gameboard(index)
    board.gameboard(index) = 0
    for (i <- 1 until countStonesInMuld + 1) {
      if ((round % 2 == 0 && (index + i) % 14 == 0) || (round % 2 == 1 && (index + i) % 14 == 7)) {
        //print("turn: " + round % 2 + " i = " + (index + i) + " x = " + countStonesInMuld + " skip\n")
        if (index + i >= board.gameboard.length) {
          val y: Int = index + countStonesInMuld - board.gameboard.length
          board.gameboard(y + 1) += 1
        } else {
          board.gameboard(index + countStonesInMuld) += 1
        }
      } else {
        if (index + i >= board.gameboard.length) {
          val y: Int = index + i - board.gameboard.length
          board.gameboard(y) += 1
        } else {
          board.gameboard(index + i) += 1
        }
      }
    }
    round += 1
    //print(board.toString())
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
    for (i <- 0 until board.stones) {
      x += board.gameboard(i + 1)
      print(x)
    }
    var y: Int = 0
    for (i <- 0 until board.stones)
      y += board.gameboard(i + board.stones+1)

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
