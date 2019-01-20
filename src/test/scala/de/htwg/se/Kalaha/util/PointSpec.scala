package de.htwg.se.Kalaha.util

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PointSpec extends WordSpec with Matchers {
  val x = 3
  val y = 5
  "A Point" when {
    "new " should {
      val testPoint = Point(x, y)
      "getX" in {
        testPoint.getX() should be(x)
      }
      "getY" in {
        testPoint.getY() should be(y)
      }
    }
  }
}
