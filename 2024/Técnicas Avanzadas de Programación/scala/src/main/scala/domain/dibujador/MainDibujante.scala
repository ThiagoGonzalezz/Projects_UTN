package domain.dibujador


import tadp.drawing.TADPDrawingAdapter
import domain.parsersFiguras.*

import java.nio.file.{Files, Paths}

object MainDibujante extends App {
  TADPDrawingAdapter.forScreen { adapter =>

    dibujarArchivo("src/fuenteImagenes/Pepita",adapter)
  }

  def dibujarArchivo(pathDesdeDirectorio: String,adapter: TADPDrawingAdapter): Unit = {
    val pathFinal = Paths.get("").toAbsolutePath.resolve(pathDesdeDirectorio)

    val codigoFuentePreprocesado = new String(Files.readAllBytes(pathFinal)).replaceAll("[\\n\\t\\r]", "")

    parserCompleto(codigoFuentePreprocesado).get._1.foldLeft (adapter) ((adapter, figura) => figura.simplificar.dibujarse(adapter))

  }

}

