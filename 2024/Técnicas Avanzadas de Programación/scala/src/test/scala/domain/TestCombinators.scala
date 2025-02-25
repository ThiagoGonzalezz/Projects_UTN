package domain

import domain.parsersBasicos.{char, integer, string}
import org.scalatest.freespec.AnyFreeSpec

import scala.util.{Failure, Success}

class TestCombinators extends AnyFreeSpec{

  "Or combinator debería devolver el resultado del primer parser si tiene éxito" in {
    val orParser = char('a') <|> char('b')
    assert(orParser("arbol") == Success(('a', "rbol")))
  }

  "Or combinator debería devolver el resultado del segundo parser si falla el primero" in {
    val orParser = char('a') <|> char('b')
    assert(orParser("bort") == Success(('b', "ort")))}


  "Or combinator (con integers" in {
    val orParser = integer <|> integer
    assert(orParser("-114214DWDFAFA") == Success((-114214,"DWDFAFA")))}


  "Or combinator debería fallar si fallan ambos parsers" in {
    val orParser = char('a') <|> char('b')
    assert(orParser("casa").isFailure)
   }

  /////////////////////////////////////////////////////////////////////////////////////////

  "Concat combinator debería parsear secuencialmente dos strings y devolver una tupla con sus resultados" in {
    val concatParser = string("hola") <> string("mundo")
    assert(concatParser("holamundo") == Success((("hola", "mundo"), "")))
  }

  "Concat combinator debería fallar porque parsea todo o no parsea nada" in {
    val concatParser = string("hola") <> string("mundo")
    assert(concatParser("holachau").isFailure)
  }

  /////////////////////////////////////////////////////////////////////////////////////////

  "Rightmost combinator debería devolver el resultado del segundo parser si ambos tienen éxito" in {
    val rightmostParser = string("hola") ~>  string("mundo")
    assert(rightmostParser("holamundo") == Success(("mundo", "")))
  }

  "Rightmost combinator debería fallar si el primer parser falla" in {
    val rightmostParser = string("chau") ~>  string("mundo")
    assert(rightmostParser("holamundo").isFailure)
  }

  "Rightmost combinator debería fallar si el segundo parser falla" in {
    val rightmostParser = string("hola") ~>  string("amigo")
    assert(rightmostParser("holamundo").isFailure)
  }

  /////////////////////////////////////////////////////////////////////////////////////////

  "Leftmost combinator debería devolver el resultado del primer parser si ambos tienen éxito" in {
    val leftmostParser = string("hola") <~ string("mundo")
    assert(leftmostParser("holamundo") == Success(("hola", "")))
  }

  "Leftmost combinator debería fallar si el primer parser falla" in {
    val leftmostParser = string("hola") <~ string("world")
    assert(leftmostParser("holamundo").isFailure)
  }

  "Leftmost combinator debería fallar si el segundo parser falla" in {
    val leftmostParser = string("hola") <~ string("amigo")
    assert(leftmostParser("holamundo").isFailure)
  }

  /////////////////////////////////////////////////////////////////////////////////////////


    "SepBy combinator debería parsear múltiples números separados por guiones correctamente" in {
      assert(integer.sepBy(char('-')) ("4356-1234-5678") == Success((List(4356, 1234, 5678), "")))
    }

    "SepBy combinator debería detenerse si no encuentra el separador después de un número" in {
      assert(integer.sepBy(char('-'))("4356-1234ABC") == Success((List(4356, 1234), "ABC")))
    }

    "SepBy combinator debería fallar si el input no comienza con un número válido" in {
      assert(integer.sepBy(char('-'))("ABC-1234").isFailure)
    }

    "SepBy combinator debería parsear un único número si no hay separadores" in {
      assert(integer.sepBy(char('-'))("1234") == Success((List(1234), "")))
    }

    "SepBy combinator debería fallar si el separador está al principio sin número previo" in {
      assert(integer.sepBy(char('*'))("*1234*5678").isFailure)
    }
}






