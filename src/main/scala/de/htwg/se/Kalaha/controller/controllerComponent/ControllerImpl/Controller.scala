package de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl

import de.htwg.se.Kalaha.controller.controllerComponent.ControllerInterface
import de.htwg.se.Kalaha.model.gameboardController.GameboardImpl.Gameboard
import de.htwg.se.Kalaha.util.{Observable, UndoManager}
import de.htwg.se.Kalaha.view.gui.Gui
import de.htwg.se.Kalaha.view.tui.Tui

class Controller() extends Observable with ControllerInterface{
  var board = new Gameboard
  var amountStones = 0
  var undone = false
  var p1win = false
  var p2win = false
  val p1 = 7
  val p2 = 0
  private val undoManager = new UndoManager

  //val injector = Guice.createInjector(new GameboardModule)
  //val fileIo = injector.instance[FileIOInterface]

  def controllerInit(amountStonesStart: Int): Unit = {
    amountStones = amountStonesStart
    board.boardInit(amountStonesStart)
   // notifyObservers
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
    val turn = board.round % 2
    //print("Turn = " + turn + "\n")
    var idx = index
    var last = 0
    board.oldgb = board.gameboard.clone()
    val countStonesInMuld: Int = board.gameboard(idx)
    //print("Balls = " + countStonesInMuld + "\n")
    board.gameboard(idx) = 0
    for (i <- 1 until countStonesInMuld + 1) {
      if ((turn == 0 && (index + i) % 14 == 0) || (turn == 1 && (index + i) % 14 == p1)) {
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
    notifyObservers
    checkExtra(last)

    board.round += 1
  }

  def collectEnemyStones(last: Int): Unit = {
    var own = false
    if ((1 <= last) && (last <= 6) && board.round % 2 == 0) own = true
    if ((8 <= last) && (last <= 13) && board.round % 2 == 1) own = true
    //print("\nown= " + own)
    if (own) {
      val idx = 14 - last
      if (board.round % 2 == 0) {
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
    if ((board.round % 2 == 1 && last == 0) || (board.round % 2 == 0 && last == 7)) {
      //print("New Turn")
      //tui.startTurn()
      //notifyObservers

      board.round -= 1
      notifyObservers
    }
    if (board.gameboard(last) == 1) {
      collectEnemyStones(last)
      notifyObservers
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
          board.round -= 1
          undone = true
          print("undo \n")
       }
    //undoManager.undoStep
    notifyObservers
  }

  def redo(): Unit = {
    if(undone) {
    val vBoard = new Gameboard
    vBoard.gameboard = board.gameboard.clone()
    board.gameboard = board.oldgb.clone()
    board.oldgb = vBoard.gameboard.clone()
    board.round += 1
    print("redo \n")
    undone = false
    }
    //undoManager.redoStep
    notifyObservers
  }

  def reset(): Unit = {
    board.boardInit(amountStones)
    board.round = 0
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
    if (x == 0 || y == 0) win()
  }

  def win(): Unit = {
    var x: Int = 0
    for (i <- 1 until 6 + 1) {
      x += board.gameboard(i)
    }
    var y: Int = 0
    for (i <- 1 until 6 + 1)
      y += board.gameboard(i + p1)

    board.gameboard(p1) += x
    board.gameboard(p2) += y

    match {
      case a if board.gameboard(p1) > board.gameboard(p2) =>
        //print("P1: " + board.gameboard(p1) + " P2: " + board.gameboard(2) + "\n")
        print("WIN PLAYER 1\n")
        p1win = true
      case a if board.gameboard(p2) > board.gameboard(p1) =>
        //print("P1: " + board.gameboard(p1) + " P2: " + board.gameboard(p2) + "\n")
        print("WIN PLAYER 2\n")
        p2win = true
      case _ =>
        print("TIE\n")
        p2win = true
        p1win = true
    }
  }

  def exit(): Unit = {
    sys.exit(0)
  }

  def save: Unit = {
    //fileIo.save(Gameboard)

  }
}
