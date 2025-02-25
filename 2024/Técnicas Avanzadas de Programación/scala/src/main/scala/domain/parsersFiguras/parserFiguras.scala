
package domain.parsersFiguras

import domain.figuras._
import domain.parsersBasicos._

import scala.util.Try

case object parserTriangulo extends Parser[Figura]{
  def apply(entrada: String): Try[(Figura,String)] ={
    sacarDeAdentro("triangulo[", ']', parserCoordenadas.map({case List(a,b,c) => Triangulo(a,b,c)})) (entrada)
  }
}
case object parserRectangulo extends Parser[Figura]{
  def apply(entrada: String): Try[(Figura,String)] ={
    sacarDeAdentro("rectangulo[", ']', parserCoordenadas.map({case List(a,b) => Rectangulo(a,b)})) (entrada)
  }
}

case object parserCirculo extends Parser[Figura] {
  def apply(entrada: String): Try[(Figura, String)] = {

    for {
      (_, resto) <- string("circulo[") (sacarEspacios(entrada))
      (centro, resto1) <- parsearPunto(resto)
      (_, resto2) <- char(',')(resto1)
      (radio, resto3) <- double(resto2)
      (_, restoFinal) <- char(']')(resto3)
    } yield {
      (Circulo(centro, radio), restoFinal)
    }
  }
}

object parserCompleto extends Parser[List[Figura]]{
  def apply(entrada: String): Try[(List[Figura], String)] ={
    parserFigura.* (entrada)
  }
}


