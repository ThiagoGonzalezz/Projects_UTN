package domain

import domain.parsersBasicos._
import domain.parsersFiguras._
import domain.figuras._
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Failure, Success}

class TestTransformaciones extends AnyFreeSpec{

  "Color con figura adentro se parsea bien" in {
    val punto = new Punto2d(100, 200)
    val circulo = Circulo(punto, 250)
    val resultado = parserFigura("color[200,200,200](circulo[100 @ 200, 250])")

    assert(resultado == Success((Color(200, 200, 200, circulo), "")))
  }

  "Color con transformacion adentro se parsea bien" in {
    val punto = new Punto2d(100, 200)
    val circulo = Circulo(punto, 250)
    val resultado = parserFigura("color[200,200,200](rotacion[1](circulo[100 @ 200, 250]))")

    assert(resultado == Success((Color(200, 200, 200, Rotacion(1, circulo)), "")))
  }

  "Color con grupo adentro se parsea bien" in {
    val punto = new Punto2d(100, 200)
    val circulo = Circulo(punto, 250)
    val resultado = parserFigura("color[200,200,200](grupo(circulo[100 @ 200, 250], circulo[100 @ 200, 250]))")

    assert(resultado == Success((Color(200, 200, 200, Grupo(List(circulo, circulo))), "")))
  }

  "Escala con figura adentro se parsea bien" in {
    val punto = new Punto2d(100, 200)
    val circulo = Circulo(punto, 250)
    val resultado = parserFigura("escala[2,3](circulo[100 @ 200, 250])")

    assert(resultado == Success((Escala(2, 3, circulo), "")))
  }

  "Traslacion con figura adentro se parsea bien" in {
    val punto = new Punto2d(100, 200)
    val circulo = Circulo(punto, 250)
    val resultado = parserFigura("traslacion[2,3](circulo[100 @ 200, 250])")

    assert(resultado == Success((Traslacion(2, 3, circulo), "")))
  }

  "Traslacion con transformacion adentro se parsea bien" in {
    val punto = new Punto2d(100, 200)
    val circulo = Circulo(punto, 250)
    val resultado = parserFigura("traslacion[20,20](rotacion[1](circulo[100 @ 200, 250]))")

    assert(resultado == Success((Traslacion(20, 20, Rotacion(1, circulo)), "")))
  }

  "Rotacion con figura adentro se parsea bien" in {
    val punto = new Punto2d(100, 200)
    val circulo = Circulo(punto, 250)
    val resultado = parserFigura("rotacion[3](circulo[100 @ 200, 250])")

    assert(resultado == Success((Rotacion(3, circulo), "")))
  }

  "Rotacion con transformacion adentro se parsea bien" in {
    val punto = new Punto2d(100, 200)
    val circulo = Circulo(punto, 250)
    val resultado = parserFigura("rotacion[20](rotacion[1](circulo[100 @ 200, 250]))")

    assert(resultado == Success((Rotacion(20, Rotacion(1, circulo)), "")))
  }

  "Rotacion con grupo adentro se parsea bien" in {
    val punto = new Punto2d(100, 200)
    val circulo = Circulo(punto, 250)
    val resultado = parserFigura("rotacion[20](grupo(circulo[100 @ 200, 250], circulo[100 @ 200, 250]))")

    assert(resultado == Success((Rotacion(20, Grupo(List(circulo, circulo))), "")))
  }

}