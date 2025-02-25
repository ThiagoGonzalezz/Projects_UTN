package domain

import domain.parsersBasicos._
import domain.parsersFiguras._
import domain.figuras._
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Failure, Success}

class TestParsers extends AnyFreeSpec{

  "anyChar falla cuando la entrada es vacia" in{
    assert(anyChar("").isFailure);
  }

  "anyChar pasa cuando la entrada no es nula" in {
    assert(anyChar("chau") == Success(('c', "hau")))
  }

  "Char pasa cuando el caracter parseado es el esperado" in{
    assert(char('c')("chau") == Success(('c', "hau")))
  }

  "Char falla cuando el caracter parseado no es el esperado" in{
    assert(char('c')("hola").isFailure)
  }

  "Digit pasa cuando el caracter es un digito" in{
    assert(digit("12345") == Success(('1', "2345")))
  }

  "Digit falla cuando el caracter no es un digito" in{
    assert(digit("a12354").isFailure)
  }

  "String pasa cuando la cadena parseada empieza con la cadena esperada" in{
    assert(string("hola")("holamundo") == Success(("hola","mundo")))
  }

  "String falla cuando la cadena parseada no empieza con la cadena esperada" in{
    assert(string("hola")("chaumundo").isFailure)
  }

  "Integer pasa cuando la cadena de entrada comienza con un entero positivo" in{
    assert(integer("114214DWDFAFA") == Success((114214,"DWDFAFA")))
  }

  "Integer pasa cuando la cadena de entrada comienza con un entero negativo" in{
    assert(integer("-114214DWDFAFA") == Success((-114214,"DWDFAFA")))
  }

  "Integer falla cuando la cadena de entrada no comienza con un entero" in{
    assert(integer("DW-114214DFAFA").isFailure)
  }

  "Double pasa cuando la cadena de entrada es un double positivo" in{
    assert(double("15.22A") == Success((15.22,"A")))
  }

  "Double pasa cuando la cadena de entrada es un double negativo" in{
    assert(double("-15.22A") == Success((-15.22,"A")))
  }

  "Double pasa cuando la cadena de entrada es un integer negativo" in{
    assert(double("-15A") == Success((-15,"A")))
  }

  "Double pasa cuando la cadena de entrada es un integer positivo" in{
    assert(double("15A") == Success((15,"A")))
  }

  "Double falla cuando la cadena no empieza con un numero decimal" in{
    assert(double("A15.22A").isFailure)
  }
}
