package domain.parsersFiguras

import domain.figuras._
import domain.parsersBasicos._
import scala.util.{Failure, Try}


object parserCoordenadas extends Parser[List[Punto2d]]{
  def apply(entrada: String): Try[(List[Punto2d], String)] = {
    parsearPuntos.map(x => listaDeDoubleAListaDePunto(x))(entrada)
  }


  def parsearPuntos: Parser[List[List[Double]]] = {
    double
      .sepBy(char('@').opt.sinEspacios)
      .sepBy(char(',').opt.sinEspacios)
  }

  def listaDeDoubleAListaDePunto(lista: List[List[Double]]) ={
    lista.map({case List(x,y) => Punto2d(x,y)})
  }
}

case object parsearPunto extends Parser[Punto2d] {
  def apply(cadena: String): Try[(Punto2d, String)] = {
    for {
      (valores, resto) <- double.sepBy(char('@'))(cadena)
      punto <- Try {
        valores match {
          case List(x, y) => Punto2d(x, y)
          case _ => throw new IllegalArgumentException("Formato inválido para un punto")
        }
      }
    } yield (punto, resto)
  }
}
// 23.0 @ 27.4 => (new Punto2d(23.0, 27.4) ,"")

//( (23.0, 27.4) ,"")







