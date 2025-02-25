package domain

import domain.parsersBasicos._
import domain.parsersFiguras._
import domain.figuras._
import org.scalatest.freespec.AnyFreeSpec
import scala.util.{Failure, Success}

class TestFiguras extends AnyFreeSpec {
  "Triangulo" in{
    val punto2d_1 = new Punto2d(100,200)
    val punto2d_2 = new Punto2d(50,75)
    val punto2d_3 = new Punto2d(125,80)

    assert(parserTriangulo("triangulo[100 @ 200, 50 @ 75, 125 @ 80]") == Success(Triangulo(punto2d_1,punto2d_2,punto2d_3), ""))
  }

  "Rectangulo" in{
    val punto2d_1 = new Punto2d(100,200)
    val punto2d_2 = new Punto2d(50,50)
    assert(parserRectangulo("rectangulo[100 @ 200, 50 @ 50]")  == Success(Rectangulo(punto2d_1,punto2d_2), ""))
  }

  "Circulo" in{
    val punto2d_1 = new Punto2d(100,200)
    val radio = 250
    assert(parserCirculo("circulo[100 @ 200, 250]")  == Success(Circulo(punto2d_1,radio),""))
  }
}