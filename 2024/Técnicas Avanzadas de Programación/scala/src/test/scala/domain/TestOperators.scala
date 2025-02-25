package domain

import org.scalatest.freespec.AnyFreeSpec
import domain.parsersBasicos._
import domain.parsersFiguras._
import domain.figuras._
import scala.util.{Failure, Success}

import scala.util.Success

class TestOperators extends AnyFreeSpec {

  //Satisfies
  "Satisfies pasa cuando se parsea correctamente y lo parseado cumple la condicion" in {
    assert(anyChar.satisfies(c => c == 'x') ("xas") == Success(('x', "as")))
  }

  "Satisfies falla cuando se parsea correctamente y lo parseado no cumple la condicion" in {
    assert(anyChar.satisfies(c => c == 'z') ("xas").isFailure)
  }

  "Satisfies falla cuando no se parsea correctamente" in {
    assert(integer.satisfies(n => n == 12) ("25").isFailure)
  }

  //Opt
  "Opt parser debería devolver un Some si encuentra coincidencia" in {
    // Se define un parser que reconoce la cadena "in"
    val talVezIn = string("in").opt

    // Se define un parser que espera "in" y luego "fija"
    val precedencia = talVezIn <> string("fija")

    // Caso donde "in" es encontrado, luego "fija" se encuentra correctamente
    val resultado = precedencia("infija")

    // Se espera que talVezIn sea Some("in") y "fija" esté intacta como el resto
    assert(resultado == Success(((Some("in"), "fija") , "")))
  }

  "Opt parser debería devolver None si no encuentra coincidencia" in {
    // Se define el mismo parser talVezIn
    val talVezIn = string("in").opt

    // Se define el mismo parser precedencia
    val precedencia = talVezIn <> string("fija")

    // Caso donde no se encuentra "in", pero "fija" sigue siendo reconocido
    val resultado = precedencia("fija")

    // Se espera que talVezIn sea None y "fija" esté intacta como el resto
    assert(resultado == Success((None, "fija"), ""))
  }

  "Opt parser debería devolver None si no su parser interno falla" in {

    assert(string("boca").opt ("boquita") == Success(None, "boquita"))
  }

  "Opt parser debería devolver Some(resultadoParser) si su parser interno pasa" in {
    assert(string("boquita").opt ("boquita") == Success(Some("boquita"), ""))
  }

  //Kleene

  "ClausulaDeKleene pasa cuando se ejecuta 0 veces" in {
    val resultado = char('x').*("cccfc")

    assert(resultado == Success((Nil, "cccfc")))
  }

  "ClausulaDeKleene pasa cuando se ejecuta 1 vez" in {
    val resultado = char('x').*("xcccfc")

    assert(resultado == Success((List('x'), "cccfc")))
  }

  "ClausulaDeKleene pasa cuando se ejecuta muchas veces" in {
    val resultado = char('c').*("cccfc")

    assert(resultado == Success((List('c', 'c', 'c'), "fc")))
  }

  //Positiva

  "Clausura positiva pasa cuando se ejecuta una sola vez" in {
    val parserNumero = integer
    val resultado = parserNumero.+("123456")

    assert(resultado == Success((List(123456), "")))
  }

  "Clausura positiva pasa cuando se ejecuta mas de una vez" in {
    val resultado = char('c').+("cccfc")

    assert(resultado == Success((List('c', 'c', 'c'), "fc")))
  }


  "Clausura positiva lanza error si no hay elementos para parsear" in {
    val parserNumero = integer
    val resultado = parserNumero.+("")

    assert(resultado.isFailure)
  }

  //Map
  "Map falla si su parser falla" in {

    assert(integer.map(n => n + 5)("sadas24").isFailure)
  }

  "Map pasa si su parser pasa, y ejecuta correctamente la funcion" in {

    assert(integer.map(n => n + 5)("24sad") == Success((29, "sad")))
  }
}