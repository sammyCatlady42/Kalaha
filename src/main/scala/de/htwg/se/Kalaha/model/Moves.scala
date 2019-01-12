package de.htwg.se.Kalaha.model

class Moves {

  def roll (): Int = {
    val r = scala.util.Random
    r.nextInt(5) + 1
  }

  def fwd (Player: Player): Unit = {
    val r = roll()
    //globalmoveliste.add current location
    //move to new location
  }

  def undo (Player: Player): Unit = {
    //reset last move
  }

}


