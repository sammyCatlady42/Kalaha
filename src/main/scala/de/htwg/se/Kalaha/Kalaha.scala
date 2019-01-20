package de.htwg.se.Kalaha

import de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl.Controller

object Kalaha {
        def main(args: Array[String]): Unit = {
                val controller = new Controller
                controller.controllerInit()
                //val tui = new Tui(controller)
                //tui.startGame()
                //val gui = new Gui(controller)
        }
}