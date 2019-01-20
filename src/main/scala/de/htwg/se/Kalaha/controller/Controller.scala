package de.htwg.se.Kalaha.controller

import de.htwg.se.Kalaha.model.Gameboard
import de.htwg.se.Kalaha.observer.Observable

class Controller() extends Observable {
  var round = 0
  var board = new Gameboard
  var amoutStones = 0
  var undone = true

  def controllerInit(amountStonesStart: Int): Unit = {
    amoutStones = amountStonesStart
    board.boardInit(amountStonesStart)
  }
  def controllerInit(): Unit = {
    board.boardInit()
  }

  def updateStones(x: Int): Unit = {
    amoutStones = x
  }

  def move(inputIndex: Int): Unit = {
    var index = inputIndex
    print("index = " + index + "\n")
    val turn = round % 2
    print("Turn = " + turn + "\n")
    var idx = index
    var last = 0
    board.oldgb = board.gameboard.clone()
    val countStonesInMuld: Int = board.gameboard(idx)
    //print("Balls = " + countStonesInMuld + "\n")
    board.gameboard(idx) = 0
    for (i <- 1 until countStonesInMuld + 1) {
      if ((turn == 0 && (index + i) % 14 == 0) || (turn == 1 && (index + i) % 14 == 7)) {
        //print("turn: " + round % 2 + " i = " + (index + i) + " x = " + countStonesInMuld + " skip\n")

        //check if last hole > gameboard
        if (idx + i >= board.gameboard.length) {
          val y: Int = (idx + i - board.gameboard.length) % 14
          board.gameboard(y + 1) += 1
          idx += 1
        } else {
          board.gameboard(idx + i) += 1
        }
      } else {
        if (idx + i >= board.gameboard.length) {
          val y: Int = (idx + i - board.gameboard.length) % 14
          board.gameboard(y) += 1
        } else {
          board.gameboard(idx + i) += 1
        }
      }
      if (i == countStonesInMuld) {
        last = (idx + i) % 14
      }
    }
    checkWin()
    undone = false
    checkExtra(last)
    round += 1
    /*var s: String = ""
    for (i <- 0 until board.gameboard.length-1)
      s += board.gameboard(i)
    s += "\n"
    print(s)*/
  }

  def collectEnemyStones(last: Int): Unit = {
    var own = false
    if ((1 <= last) && (last <= 6) && round % 2 == 0) own = true
    if ((8 <= last) && (last <= 13) && round % 2 == 1) own = true
    //println("own= " + own)
    if (own) {
      val idx = 14 - last
      if (round % 2 == 0) {
        board.gameboard(7) += board.gameboard(idx)
        board.gameboard(7) += board.gameboard(last)
        board.gameboard(idx) = 0
        board.gameboard(last) = 0
      } else {
        board.gameboard(0) += board.gameboard(idx)
        board.gameboard(0) += board.gameboard(last)
        board.gameboard(idx) = 0
        board.gameboard(last) = 0
      }
    }
  }

  def checkExtra(last: Int): Unit = {
    //println("checkExtra!")
    if ((round % 2 == 1 && last == 0) || (round % 2 == 0 && last == 7)) {
      //print("New Turn")
      //tui.startTurn()
      //notifyObservers
      round -= 1
    }
    if (board.gameboard(last) == 1) {
      collectEnemyStones(last)
    }
  }

  def undo(): Unit = {
    if (undone) {
      throw new IllegalArgumentException("Es ist nur möglich einen Zug rückgängig zu machen")
    } else {
      var vboard = new Gameboard
      vboard.gameboard = board.gameboard.clone()
      board.gameboard = board.oldgb.clone()
      board.oldgb = vboard.gameboard.clone()
      round -= 1
      undone = true
      print("undo \n")
    }
  }
  
  def redo(): Unit = {
    if (!undone) {
      print(undone)
      throw new IllegalArgumentException("Kein REDO möglich")
    } else {
      var vboard = new Gameboard
      vboard.gameboard = board.gameboard.clone()
      board.gameboard = board.oldgb.clone()
      board.oldgb = vboard.gameboard.clone()
      round += 1
      print("redo \n")
      undone = false
    }
  }

  def reset(): Unit = {
    board.boardInit(amoutStones)
    round = 0
  }

  def checkWin(): Unit = {
    var x: Int = 0
    for (i <- 1 until 6 + 1) {
      x += board.gameboard(i)
    }
    var y: Int = 0
    for (i <- 1 until 6 + 1)
      y += board.gameboard(i + 7)

    if (x == 0 || y == 0) win
  }

  def win(): Unit = {
    var x: Int = 0
    for (i <- 1 until 6 + 1) {
      x += board.gameboard(i)
    }
    var y: Int = 0
    for (i <- 1 until 6 + 1)
      y += board.gameboard(i + 7)

    board.gameboard(7) += x
    board.gameboard(0) += y

    if (board.gameboard(7) > board.gameboard(0)) {
      print("P1: " + board.gameboard(7) + " P2: " + board.gameboard(0) + "\n")
      print("WIN PLAYER 1\n")
      exit()
    } else if (board.gameboard(0) > board.gameboard(7)) {
      print("P1: " + board.gameboard(7) + " P2: " + board.gameboard(0) + "\n")
      print("WIN PLAYER 2\n")
      exit()
    } else {
      print("TIE\n")
      exit()
    }
  }

  def exit(): Unit = {
    sys.exit(0)
  }
}
