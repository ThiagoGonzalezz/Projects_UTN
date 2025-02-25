package domain.parsersFiguras
import domain.parsersBasicos._
import scala.util.Try
object sacarEspacios {
  def apply(cadena: String): String = {
    cadena.filter(c => c != ' ' && c != '\n' && c != '\r')
  }
}

case class sacarDeAdentro[A](paredInicial:String, paredFinal:Char, parser: Parser [A]) extends  Parser[A] {
  def apply (cadena: String) : Try[(A, String)] = {
    (string(paredInicial) ~> parser <~ char(paredFinal)) (cadena)
  }
}
