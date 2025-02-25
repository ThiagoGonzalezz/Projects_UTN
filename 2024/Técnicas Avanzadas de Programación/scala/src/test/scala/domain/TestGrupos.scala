package domain

import domain.parsersBasicos._
import domain.parsersFiguras._
import domain.figuras._
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Failure, Success}

class TestGrupos extends AnyFreeSpec {

  "Grupo de una sola figura" in {
    val p1 = Punto2d(200, 50)
    val p2 = Punto2d(101, 335)
    val p3 = Punto2d(299, 335)
    val triangulo = new Triangulo(p1, p2, p3)
    assert(parserGrupo("grupo(triangulo[200 @ 50, 101 @ 335, 299 @ 335])") == Success(Grupo(List(triangulo)), ""))
  }

  "Grupo de dos figuras" in {
    val p1 =Punto2d(200, 50)
    val p2 =Punto2d(101, 335)
    val p3 =Punto2d(299, 335)
    val p4 =Punto2d(200, 350)
    val radio = 100

    val triangulo = Triangulo(p1, p2, p3)
    val circulo =Circulo(p4, radio)
    val a = parserGrupo("grupo(triangulo[200 @ 50, 101 @ 335, 299 @ 335], triangulo[200 @ 50, 101 @ 335, 299 @ 335])") == Success(Grupo(List(triangulo, triangulo)), "")
    assert(parserGrupo("grupo(triangulo[200 @ 50, 101 @ 335, 299 @ 335], triangulo[200 @ 50, 101 @ 335, 299 @ 335])") == Success(Grupo(List(triangulo, triangulo)), ""))
  }

  "Grupo de tres figuras" in {
    val p1 = Punto2d(200, 50)
    val p2 = Punto2d(101, 335)
    val p3 = Punto2d(299, 335)
    val p4 = Punto2d(200, 350)
    val radio = 100

    val triangulo = Triangulo(p1, p2, p3)
    val circulo = Circulo(p4, radio)

    assert(parserGrupo("grupo(triangulo[200 @ 50, 101 @ 335, 299 @ 335], triangulo[200 @ 50, 101 @ 335, 299 @ 335], circulo[200 @ 350, 100])") == Success(Grupo(List(triangulo, triangulo, circulo)), ""))
  }

  "Grupo de grupos" in {

    val triangulo1 = Triangulo(Punto2d(250, 150), Punto2d(150, 300), Punto2d(350, 300))
    val triangulo2 = Triangulo(Punto2d(150, 300), Punto2d(50, 450), Punto2d(250, 450))
    val triangulo3 = Triangulo(Punto2d(350, 300), Punto2d(250, 450), Punto2d(450, 450))

    val rectangulo1 = Rectangulo(Punto2d(460, 90), Punto2d(470, 100))
    val rectangulo2 = Rectangulo(Punto2d(430, 210), Punto2d(500, 220))
    val rectangulo3 = Rectangulo(Punto2d(430, 210), Punto2d(440, 230))
    val rectangulo4 = Rectangulo(Punto2d(490, 210), Punto2d(500, 230))
    val rectangulo5 = Rectangulo(Punto2d(450, 100), Punto2d(480, 260))

    val input = "grupo(grupo(triangulo[250 @ 150, 150 @ 300, 350 @ 300], triangulo[150 @ 300, 50 @ 450, 250 @ 450], triangulo[350 @ 300, 250 @ 450, 450 @ 450]), grupo(rectangulo[460 @ 90, 470 @ 100], rectangulo[430 @ 210, 500 @ 220], rectangulo[430 @ 210, 440 @ 230], rectangulo[490 @ 210, 500 @ 230], rectangulo[450 @ 100, 480 @ 260]))"

    val resultadoEsperado = Grupo(List(
      Grupo(List(triangulo1, triangulo2, triangulo3)),
      Grupo(List(rectangulo1, rectangulo2, rectangulo3, rectangulo4, rectangulo5))
    ))

    assert(parserGrupo(input) == Success(resultadoEsperado, ""))
  }

  "Grupo de falopa" in {

    val triangulo1 = Triangulo(Punto2d(250, 150), Punto2d(150, 300), Punto2d(350, 300))
    val triangulo2 = Triangulo(Punto2d(150, 300), Punto2d(50, 450), Punto2d(250, 450))
    val triangulo3 = Triangulo(Punto2d(350, 300), Punto2d(250, 450), Punto2d(450, 450))

    val rectangulo1 = Rectangulo(Punto2d(460, 90), Punto2d(470, 100))
    val rectangulo2 = Rectangulo(Punto2d(430, 210), Punto2d(500, 220))
    val rectangulo3 = Rectangulo(Punto2d(430, 210), Punto2d(440, 230))
    val rectangulo4 = Rectangulo(Punto2d(490, 210), Punto2d(500, 230))
    val rectangulo5 = Rectangulo(Punto2d(450, 100), Punto2d(480, 260))

    val escala = Escala(1.45,1.45, triangulo1)

    val input = "grupo(grupo(triangulo[250 @ 150, 150 @ 300, 350 @ 300], triangulo[150 @ 300, 50 @ 450, 250 @ 450], triangulo[350 @ 300, 250 @ 450, 450 @ 450], escala[1.45, 1.45](triangulo[250 @ 150, 150 @ 300, 350 @ 300])))"

    val resultadoEsperado = Grupo(List(
      Grupo(List(triangulo1, triangulo2, triangulo3)),
      escala
    ))

    assert(parserGrupo(input) == Success(resultadoEsperado, ""))
  }


/*
  "Grupo con figura, grupo y transformación" in {
    val triangulo1 = Triangulo(Punto2d(200, 50), Punto2d(101, 335), Punto2d(299, 335))
    val circulo = Circulo(Punto2d(200, 350), 100)
    val rectangulo1 = Rectangulo(Punto2d(460, 90), Punto2d(470, 100))
    val rectangulo2 = Rectangulo(Punto2d(460, 90), Punto2d(470, 100))

    val transformacion = Escala(2, 3, circulo)

    val grupo = Grupo(List(circulo, Grupo(List(rectangulo1, rectangulo2)), transformacion))


    val input = "grupo(grupo(circulo[200 @ 350, 100]), rectangulo[460 @ 90, 470 @ 100], escala[2, 3](circulo[200 @ 350, 100]))"

    val resultadoEsperado = Grupo(List(
      grupo,
      Escala(2, 3, circulo)
    ))


    var resultado = parserGrupo(input)

    assert(parserGrupo(input) == Success(resultadoEsperado, ""))
  }

 */
}
