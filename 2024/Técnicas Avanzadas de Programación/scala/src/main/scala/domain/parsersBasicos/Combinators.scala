package domain.parsersBasicos

import scala.util.{Success, Try}

class Or[A](parser1: Parser[A], parser2: Parser[A]) extends Parser[A] {
  def apply(entrada: String): Try[(A, String)] = {
    parser1(entrada).orElse(parser2(entrada))
  }
}


class ConcatCombinator[A, B](parser1: Parser[A], parser2: Parser[B]) extends Parser[(A, B)] {
  def apply(entrada: String): Try[((A, B), String)] =
    for {
      (resultado1, restante1) <- parser1(entrada)
      (resultado2, restante2) <- parser2(restante1)
    } yield ((resultado1, resultado2), restante2)
}


class RightMostCombinator[A, B](parser1: Parser[A], parser2: Parser[B]) extends Parser[B] {
  def apply(entrada: String): Try[(B, String)] = (parser1 <> parser2).map(elem => elem._2)(entrada)
}


class LeftMostCombinator[A, B](parser1: Parser[A], parser2: Parser[B]) extends Parser[A] {
  def apply(entrada: String): Try[(A, String)] = (parser1 <> parser2).map(elem => elem._1)(entrada)
}


class SepBy[A, B](parser1: Parser[A], parser2: Parser[B]) extends Parser[List[A]] {
  def apply(entrada: String): Try[(List[A], String)] = {
    (parser1 <> (parser2 ~> parser1).*).map((cabeza, cola) => cabeza::cola) (entrada)
    }
}


