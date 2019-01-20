package de.htwg.se.Kalaha.controller.controllerComponent.ControllerImpl

import de.htwg.se.Kalaha.model.gameboardController.GameboardImpl.Gameboard
import de.htwg.se.Kalaha.util.Command

class SetCommand (controller: Controller, index: Int) extends Command {

  override def doStep: Unit = {
    controller.move(index)
    //undone = false
  }

  override def undoStep: Unit = {
    print("---------------------------------------------------------------------- undone:")
   // if (!undone) {
      val vBoard = new Gameboard
      vBoard.gameboard = controller.board.gameboard.clone()
      controller.board.gameboard = controller.board.oldgb.clone()
      controller.board.oldgb = vBoard.gameboard.clone()
      controller.board.round -= 1
      //undone = true
      print("undo \n")
   // }
    // if (undone) {
    //   throw new IllegalArgumentException("Es ist nur möglich einen Zug rückgängig zu machen")
    // } else {
//    val vBoard = new Gameboard
//    vBoard.gameboard = board.gameboard.clone()
//    board.gameboard = board.oldgb.clone()
//    board.oldgb = vBoard.gameboard.clone()
//    board.round -= 1
//    undone = true
//    print("undo \n")
    // }
  }

  override def redoStep: Unit = {
    //print("---------------------------------------------------------------------- redone:" + undone)
    //if(undone) {
      val vBoard = new Gameboard
      vBoard.gameboard = controller.board.gameboard.clone()
      controller.board.gameboard = controller.board.oldgb.clone()
      controller.board.oldgb = vBoard.gameboard.clone()
      controller.board.round += 1
      print("redo \n")
      //undone = false
    //}
  }

}
