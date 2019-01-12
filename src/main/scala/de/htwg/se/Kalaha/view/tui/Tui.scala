package de.htwg.se.Kalaha.view.tui

import de.htwg.se.Kalaha.controller.Controller

case class Tui(controller: Controller) {
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
