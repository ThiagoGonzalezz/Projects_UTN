package domain

import domain.parsersBasicos.*
import domain.parsersFiguras.*
import domain.figuras.*
import org.scalatest.freespec.AnyFreeSpec
import tadp.poc.drawing.DrawingAST.Cuadrado

import scala.util.{Failure, Success}

class TestSimplificar extends AnyFreeSpec{
  val punto1 = Punto2d(20.5, 30)
  val punto2 = Punto2d(30.5, 50)
  val punto3 = Punto2d(40.5, 30)

  val triangulo = new Triangulo(punto1, punto2, punto3)

  val rectangulo = new Rectangulo(punto1, punto3)

  val circulo = new Circulo(punto3, 60)

  //TRIANGULO

  "Triangulo se devuelve a si mismo al simplificarse" in {
    assert(triangulo.simplificar == triangulo);
  }

  //RECTANGULO

  "Rectangulo se devuelve a si mismo al simplificarse" in {
    assert(rectangulo.simplificar == rectangulo);
  }

  //CIRCULO

  "Circulo se devuelve a si mismo al simplificarse" in {
    assert(circulo.simplificar == circulo);
  }


  //GRUPO

  "Grupo simplifica la transformacion cuando todos sus elementos estan transformados por la misma" in{
    val grupo = Grupo(List(Color(244, 233, 222, triangulo), Color(244, 233, 222, circulo)))
    val grupoSimplificado = Color(244, 233, 222, Grupo(List(triangulo, circulo)))

    assert(grupo.simplificar == grupoSimplificado)
  }

  "Grupo simplifica la transformacion cuando todos sus elementos estan transformados por la misma RECURSIVO" in {
    val grupo1 = Grupo(List(Color(244, 233, 222, triangulo), Color(244, 233, 222, circulo)))
    val grupo2 = Grupo(List(Color(0, 0, 0, grupo1), Color(0,0,0, triangulo)))
    val grupoSimplificado = Color(0, 0, 0, Grupo(List(Color(244, 233, 222, Grupo(List(triangulo, circulo))), triangulo)))

    assert(grupo2.simplificar == grupoSimplificado)
  }

  "Grupo no simplifica la transformacion cuando no todos los elementos estan transformados por la misma" in{
    val grupo = Grupo(List(Color(244, 233, 222, triangulo), Color(116, 183, 42, circulo)))

    assert(grupo.simplificar == grupo)
  }

  "Los elementos internos del grupo se simplifican" in{
    val grupo = Grupo(List(Rotacion(0, triangulo), Rotacion(0, circulo)))

    assert(grupo.simplificar == Grupo(List(triangulo, circulo)))
  }

  "Los elementos internos del grupo se simplifican recursivo" in {
    val grupo = Grupo(List(Escala(1,1, Rotacion(0, triangulo)), Escala(1,1, Rotacion(0, circulo))))

    assert(grupo.simplificar == Grupo(List(triangulo, circulo)))
  }

  //COLOR

  "Color simplifica si contiene otro color dentro suyo " in {
    val colorContenido = Color(240,240,240, triangulo)
    val colorContenedor = Color(10,10,10, colorContenido)

    assert(colorContenedor.simplificar == colorContenido)
  }

  "Color simplifica si contiene otro color dentro suyo recursivo " in {
    val colorContenido2 = Color(240, 240, 240, triangulo)
    val colorContenido1 = Color(7, 7, 7, colorContenido2)
    val colorContenedor = Color(10, 10, 10, colorContenido2)

    assert(colorContenedor.simplificar == colorContenido2)
  }

  "Color simplifica su contenido " in {
    val color = Color(240, 240, 240, Rotacion(0, triangulo))

    assert(color.simplificar == Color(240, 240, 240, triangulo))
  }

  //ROTACION

  "Rotacion simplifica si contiene otra rotacion dentro suyo " in {
    val rotacionContenida = Rotacion(20, triangulo)
    val rotacionContenedora = Rotacion(40, rotacionContenida)

    assert(rotacionContenedora.simplificar == Rotacion(60, triangulo))
  }

  "Rotacion simplifica si contiene otra rotacion dentro suyo recursivo " in {
    val rotacionContenida1 = Rotacion(20, triangulo)
    val rotacionContenida2 = Rotacion(30, rotacionContenida1)
    val rotacionContenedora = Rotacion(40, rotacionContenida2)

    assert(rotacionContenedora.simplificar == Rotacion(90, triangulo))
  }

  "Rotacion se elimina cuando su angulo es 0" in {
    val rotacion = Rotacion(0, triangulo)

    assert(rotacion.simplificar == triangulo)
  }

  "Rotacion simplifica su contenido " in {
    val rotacion = Rotacion(240, Escala(1, 1, triangulo))

    assert(rotacion.simplificar == Rotacion(240, triangulo))
  }

  //TRASLACION

  "Traslacion simplifica si contiene otra traslacion dentro suyo " in {
    val traslacionContenida = Traslacion(20, 30, triangulo)
    val traslacionContenedora = Traslacion(80, 70, traslacionContenida)

    assert(traslacionContenedora.simplificar == Traslacion(100, 100, triangulo))
  }

  "Traslacion simplifica si contiene otra traslacion dentro suyo recursivo " in {
    val traslacionContenida1 = Traslacion(20, 30, triangulo)
    val traslacionContenida2 = Traslacion(80, 70, traslacionContenida1)
    val traslacionContenedora = Traslacion(20, 20, traslacionContenida2)

    assert(traslacionContenedora.simplificar == Traslacion(120, 120, triangulo))
  } //toDo: Arreglar

  "Traslacion se elimina cuando ambos valores son 0" in {
    val traslacion = Traslacion(0, 0, triangulo)

    assert(traslacion.simplificar == triangulo)
  }

  "Traslacion simplifica su contenido " in {
    val traslacion = Traslacion(2, 5, Escala(1, 1, triangulo))

    assert(traslacion.simplificar == Traslacion(2, 5, triangulo))
  }

  //ESCALA

  "Escala simplifica si contiene otra escala dentro suyo " in {
    val escalaContenida = Escala(2, 3, triangulo)
    val escalaContenedora = Escala(4, 2, escalaContenida)

    assert(escalaContenedora.simplificar == Escala(8, 6, triangulo))
  }

  "Escala simplifica si contiene otra escala dentro suyo recursivo " in {
    val escalaContenida1 = Escala(2, 2, triangulo)
    val escalaContenida2 = Escala(4, 3, escalaContenida1)
    val escalaContenedora = Escala(1, 2, escalaContenida2)

    assert(escalaContenedora.simplificar == Escala(8, 12, triangulo))
  } //toDo: Arreglar

  "Escala se elimina cuando ambos factores son 0" in {
    val escala = Escala(1, 1, triangulo)

    assert(escala.simplificar == triangulo)
  }

  "Escala simplifica su contenido " in {
    val escala = Escala(2, 5, Rotacion(0, triangulo))

    assert(escala.simplificar == Escala(2, 5, triangulo))
  }
}
