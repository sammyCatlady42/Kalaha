package de.htwg.se.Kalaha

import de.htwg.se.Kalaha.controller.Controller
import de.htwg.se.Kalaha.model.Player
import de.htwg.se.Kalaha.model.Gameboard
import de.htwg.se.Kalaha.view.tui.Tui

object Kalaha {
        def main(args: Array[String]): Unit = {
                val controller = new Controller
                controller.controllerInit(6)
                val tui = new Tui(controller)
                tui.startGame()
        }
}