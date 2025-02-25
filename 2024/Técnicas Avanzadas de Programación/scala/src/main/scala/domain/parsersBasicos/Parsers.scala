package domain.parsersBasicos

import domain.parsersBasicos

import scala.util.{Failure, Try}

trait Parser[A] {
  def apply(entrada: String): Try[(A, String)]

  def <|>(parser2: Parser[A]): Or[A] = new Or[A](this, parser2)

  def <>[B](parser2: Parser[B]): ConcatCombinator[A, B] = new ConcatCombinator[A, B](this, parser2)

  def ~>[B](parser2: Parser[B]): RightMostCombinator[A, B] = new RightMostCombinator[A, B](this, parser2)

  def <~[B](parser2: Parser[B]): LeftMostCombinator[A, B] = new LeftMostCombinator[A, B](this, parser2)

  def sepBy[B](separador: Parser[B]): SepBy[A, B] = new SepBy(this, separador)

  def satisfies(condicion: A => Boolean) : satisfies[A] = new satisfies[A](this, condicion)
  def opt : opt[A] = new opt[A](this)

  def * : ClausuraKleene[A] = new ClausuraKleene[A](this)

  def + : ClausuraPositiva[A] = new ClausuraPositiva[A](this)

  def map[B](transformacion: A => B) = new parsersBasicos.Map[A, B](this, transformacion)

  def sinEspacios: Parser[A] = new SinEspacios(this)
}

  object anyChar extends Parser[Char] {
    def apply(input: String): Try[(Char, String)] =
      Try((input.head, input.tail))
  }

  case class char(esperado: Char) extends Parser[Char] {
    def apply(input: String): Try[(Char, String)] =
      anyChar.satisfies(c => c == esperado) (input)
  }

  object digit extends Parser[Char] {
    def apply(input: String): Try[(Char, String)] =
      anyChar.satisfies(a => a.isDigit) (input)
  }

  case class string(esperado: String) extends Parser[String] {
    def apply(input: String): Try[(String, String)] =
      Try(input)
        .filter(_.startsWith(esperado))
        .map(_ => (esperado, input.drop(esperado.length)))
        .orElse(Failure(new Exception(s"Se esperaba '$esperado', pero no coincide")))
  }


  object integer extends Parser[Int] {
    def apply(cadena: String): Try[(Int, String)] = {
      val IntegerRegex = """^-?[0-9]+""".r

      for {
        cadenaEntera <- Try(IntegerRegex.findPrefixOf(cadena).getOrElse(
          throw new Exception(s"No se encontró un entero válido en la cadena: $cadena")
        ))
        entero <- Try(cadenaEntera.toInt)
      } yield (entero, cadena.drop(cadenaEntera.length))
    }
  }

  object double extends Parser[Double] {
    def apply(cadena: String): Try[(Double, String)] = {
      val DoubleRegex = """^-?[0-9]+(\.[0-9]+)?""".r

      for {
        cadenaEntera <- Try(DoubleRegex.findPrefixOf(cadena).getOrElse(
          throw new Exception(s"No se encontró un número en formato double en la cadena: $cadena")
        ))
        decimal <- Try(cadenaEntera.toDouble)
      } yield (decimal, cadena.drop(cadenaEntera.length))
    }
  }
