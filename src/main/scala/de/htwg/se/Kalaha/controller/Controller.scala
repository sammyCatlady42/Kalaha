package de.htwg.se.Kalaha.controller

import de.htwg.se.Kalaha.model.Gameboard
import de.htwg.se.Kalaha.observer.Observable
import de.htwg.se.Kalaha.view.gui.Gui
import de.htwg.se.Kalaha.view.tui.Tui

class Controller() extends Observable {
  var round = 0
  var board = new Gameboard
  var amountStones = 0
  var undone = true
  var p1win = false
  var p2win = false
  val p1 = 7
  val p2 = 0

  def controllerInit(amountStonesStart: Int): Unit = {
    amountStones = amountStonesStart
    board.boardInit(amountStonesStart)
    notifyObservers
  }
  def controllerInit(): Unit = {
    amountStones = 6
    board.boardInit()
    val tui = new Tui(this)
    val gui = new Gui(this)
    tui.startGame()

  }

  def updateStones(x: Int): Unit = {
    amountStones = x
  }

  def move(inputIndex: Int): Unit = {
    val index = inputIndex
    //print("index = " + index + "\n")
    val turn = round % 2
    //print("Turn = " + turn + "\n")
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

    undone = false
    checkExtra(last)
    round += 1
    notifyObservers
  }

  def collectEnemyStones(last: Int): Unit = {
    var own = false
    if ((1 <= last) && (last <= 6) && round % 2 == 0) own = true
    if ((8 <= last) && (last <= 13) && round % 2 == 1) own = true
    //print("own= " + own + "\n")
    if (own) {
      val idx = 14 - last
      if (round % 2 == 0) {
        board.gameboard(p1) += board.gameboard(idx)
        board.gameboard(p1) += board.gameboard(last)
        board.gameboard(idx) = 0
        board.gameboard(last) = 0
      } else {
        board.gameboard(p2) += board.gameboard(idx)
        board.gameboard(p2) += board.gameboard(last)
        board.gameboard(idx) = 0
        board.gameboard(last) = 0
      }
    }
  }

  def checkExtra(last: Int): Unit = {
    //checkWin()
    //print("checkExtra!\n")
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
      val vBoard = new Gameboard
      vBoard.gameboard = board.gameboard.clone()
      board.gameboard = board.oldgb.clone()
      board.oldgb = vBoard.gameboard.clone()
      round -= 1
      undone = true
      print("undo \n")
    }
    notifyObservers
  }
  
  def redo(): Unit = {
    if (!undone) {
      print(undone)
      throw new IllegalArgumentException("Kein REDO möglich")
    } else {
      val vBoard = new Gameboard
      vBoard.gameboard = board.gameboard.clone()
      board.gameboard = board.oldgb.clone()
      board.oldgb = vBoard.gameboard.clone()
      round += 1
      print("redo \n")
      undone = false
    }
    notifyObservers
  }

  def reset(): Unit = {
    board.boardInit(amountStones)
    round = 0
    notifyObservers
  }

  def checkWin(): Unit = {
    var x: Int = 0
    for (i <- 1 until 6 + 1) {
      //print("i: " + i)
      x += board.gameboard(i)
    }
    var y: Int = 0
    for (i <- 1 until 6 + 1) {
      //print("i2: " + (i + 7))
      y += board.gameboard(i + 7)
    }
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
    //print("x: " + x + " y: " + y)

    board.gameboard(p1) += x
    board.gameboard(p2) += y

    if (board.gameboard(p1) > board.gameboard(p2)) {
      //print("P1: " + board.gameboard(7) + " P2: " + board.gameboard(0) + "\n")
      print("WIN PLAYER 1\n")
      p1win = true
    } else if (board.gameboard(p2) > board.gameboard(p1)) {
      //print("P1: " + board.gameboard(7) + " P2: " + board.gameboard(0) + "\n")
      print("WIN PLAYER 2\n")
        p2win = true
    } else {
      //print("P1: " + board.gameboard(7) + " P2: " + board.gameboard(0) + "\n")
      print("TIE\n")
      p2win = true
      p1win = true
    }
  }

  def exit(): Unit = {
    sys.exit(0)
  }
}
