package domain.parsersBasicos

import scala.::
import scala.util.{Failure, Success, Try}

class satisfies[A](parser1: Parser[A], cond: A => Boolean) extends Parser[A] {
  def apply(entrada: String): Try[(A, String)] = {
    for {
      (resultado, restante) <- parser1(entrada)
      if cond(resultado)
    } yield (resultado, restante)
  }
}

class opt[A](parser1: Parser[A]) extends Parser[Option[A]] {
  def apply(entrada: String): Try[(Option[A], String)] = {

    parser1(entrada)
      .map {case (resultado, restante) => (Some(resultado), restante) }
      .recover { case _ => (None, entrada)
      }
  }
}

class ClausuraKleene[A](parser: Parser[A]) extends Parser[List[A]] {

  // La función que se encarga de hacer la recursión
  def apply(entrada: String): Try[(List[A], String)] = {

    // Función recursiva que intenta aplicar el parser tantas veces como sea posible
    def parseRec(entradaActual: String, acumulado: List[A]): Try[(List[A], String)] = {

      parser(entradaActual) match {
        // Si el parser tiene éxito
        case Success((resultado, resto)) =>
          // Llamamos recursivamente, agregando el resultado a la lista acumulada
          parseRec(resto, acumulado :+ resultado)

        // Si el parser falla, devolvemos lo acumulado hasta el momento
        case Failure(_) =>
          Success((acumulado, entradaActual))
      }
    }

    // Iniciamos la recursión con una lista vacía
    parseRec(entrada, List.empty)
  }
}

class ClausuraPositiva[A](parser: Parser[A]) extends Parser[List[A]] {
  def apply(entrada: String): Try[(List[A], String)] = {
    for {
      (primerResultado, primerResto) <- parser(entrada)
      (posiblesRestoParseos, restoFinal) <- parser.* (primerResto)
    } yield (primerResultado :: posiblesRestoParseos, restoFinal)
  }
}


class Map[A, B](parser: Parser[A], transformacion: A => B) extends Parser[B]{
  def apply(entrada: String): Try[(B, String)] = {
    for{
      (parseado, resto) <- parser(entrada)
    }yield (transformacion(parseado), resto)

  }
}


class SinEspacios[A](parser: Parser[A]) extends Parser[A] {
  def apply(entrada: String): Try[(A, String)] = {
    val sacarEspacios = (char(' ') <|> char('\n') <|> char('\t')).*

    for {
      (_, sinEspaciosIniciales) <- sacarEspacios(entrada)
      (resultado, resto) <- parser(sinEspaciosIniciales)
      (_, sinEspaciosFinales) <- sacarEspacios(resto)
    } yield (resultado, sinEspaciosFinales)}
}