package domain.parsersFiguras

import domain.figuras._
import domain.parsersBasicos.{string, *}

import scala.util.Try
case object parserGrupo extends Parser[Figura] {
  def apply(cadena: String): Try[(Figura, String)] = {
    val funcion: List[Figura] => Figura = { laLista => Grupo(laLista) }

    sacarDeAdentro("grupo(", ')', parserFigura.sepBy(char(',').sinEspacios).map(lista => Grupo(lista))) (cadena)
  }
}

case object parserFigura extends Parser[Figura] {
  def apply(cadena: String): Try[(Figura, String)] = {
    (parsearFiguras <|> parserGrupo <|> parsearTransformacion)(cadena)
  }

  private def parsearFiguras = {
    parserCirculo <|> parserRectangulo <|> parserTriangulo
  }

  private def parsearTransformacion = {
    parserColor <|> parserEscala <|> parserRotacion <|> parserTraslacion
  }

}




case class parserTransformacion(nombre:String) extends Parser[(List[Int], Figura)] {
  override def apply(entrada:String) : Try[((List[Int], Figura), String)] ={
    (sacarDeAdentro(nombre + "[", ']', parserAtributos) <> sacarDeAdentro("(", ')', parserFigura)) (entrada)
  }

}


object  parserAtributos extends Parser[List[Int]]{
  override def apply(entrada: String): Try[(List[Int], String)] =
    integer.sepBy(char(',')).sinEspacios (entrada)
}

case object parserColor extends Parser[Figura]{
  override def apply(entrada: String): Try[(Figura, String)] =
    parserTransformacion("color").map((listaAtrributos, figura) =>
                Color(listaAtrributos(0), listaAtrributos(1), listaAtrributos(2), figura)) (entrada)
}

case object parserEscala extends Parser[Figura]{
  override def apply(entrada: String): Try[(Figura, String)] =
    parserTransformacion("escala").map((listaAtrributos, figura) =>
    Escala(listaAtrributos(0).toDouble, listaAtrributos(1).toDouble, figura)) (entrada)
}

case object parserRotacion extends Parser[Figura]{
  override def apply(entrada: String): Try[(Figura, String)] =
    parserTransformacion("rotacion").map((listaAtrributos, figura) =>
      Rotacion(listaAtrributos(0), figura)) (entrada)
}

case object parserTraslacion extends Parser[Figura]{
  override def apply(entrada: String): Try[(Figura, String)] =
    parserTransformacion("traslacion").map((listaAtrributos, figura) =>
      Traslacion(listaAtrributos(0), listaAtrributos(1), figura)) (entrada)
}

/*
case object nuevaTrans extends Parser[Figura]{
  override def apply(entrada: String): Try[(Figura, String)] =
    parserTransformacion("tituloTrans").map((listaAtributos, figura)) =>
      NuevaTrans(listaAtributos[)
}*/
