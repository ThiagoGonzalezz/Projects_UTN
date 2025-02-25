package domain.figuras

import scalafx.scene.paint.Color as ColorTADP
import tadp.drawing.TADPDrawingAdapter


case class Punto2d(x: Double, y: Double){
  def toTupla : (Double, Double) = (x, y)
}

sealed trait Figura {
  def simplificar : Figura

  def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter

  def contenido: Figura = this
}


// Figuras básicas
case class Triangulo(p1: Punto2d, p2: Punto2d, p3: Punto2d) extends Figura {
  override def simplificar: Figura = this

  override def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    adapter.triangle(p1.toTupla, p2.toTupla, p3.toTupla)
  }
}

case class Rectangulo(v1: Punto2d, v2: Punto2d) extends Figura {
  override def simplificar: Figura = this

  override def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    adapter.rectangle(v1.toTupla, v2.toTupla)
  }

}
case class Circulo(centro: Punto2d, radio: Double) extends Figura {
  override def simplificar: Figura = this

  override def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    adapter.circle(centro.toTupla, radio)}
}


// Grupos de elementos
case class Grupo(elementos: List[Figura]) extends Figura {
  override def simplificar: Figura =
    elementos.head match {
      case transformacion: Transformacion if elementos.forall(f => transformacion.esElMismoTransformador(f)) //toDO: SON DEL MISMO TIPO
                    => transformacion.cambiarContenido(this.grupoContenidoTransf).simplificar
      case _ => this.copy(elementos = elementos.map(e => e.simplificar))
    }

  def grupoContenidoTransf : Grupo = {
    Grupo(this.elementos.map(e => e.contenido))
  }

  override def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    this.elementos.foldLeft (adapter) ((adapter, figura) => figura.dibujarse(adapter))
  }

}




// Transformaciones que contienen a otro Elemento
sealed trait Transformacion extends Figura{
  def cambiarContenido(nuevoContenido: Figura) : Transformacion

  def esElMismoTransformador(t2: Figura): Boolean = (this, t2) match {
    case (Color(rojo1, verde1, azul1, _), Color(rojo2, verde2, azul2, _))
      if rojo1 == rojo2 && verde1 == verde2 && azul1 == azul2 => true

    case (Escala(factX1, factY1, _), Escala(factX2, factY2, _))
      if factX1 == factX2 && factY1 == factY2 => true

    case (Rotacion(angulo1, _), Rotacion(angulo2, _))
      if angulo1 == angulo2 => true

    case (Traslacion(desplX1, desplY1, _), Traslacion(desplX2, desplY2, _))
      if desplX1 == desplX2 && desplY1 == desplY2 => true

    case _ => false
  }

  override def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {adapter.circle((10, 10), 6)}
}

case class Color(rojo: Int, verde: Int, azul: Int, elemento: Figura) extends Transformacion{
  require(rojo <= 255 && rojo >= 0)
  require(verde <= 255 && verde >= 0)
  require(azul <= 255 && azul >= 0)

  override def simplificar: Figura = {
    this match {
      case Color(_, _, _, subColor:Color) => subColor.simplificar
      case Color(rojo, azul, verde, elemento) => Color(rojo, azul, verde, elemento.simplificar)
    }
  }

  override def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    elemento.dibujarse(adapter.beginColor(ColorTADP.rgb(rojo,verde,azul))).end()
  }
  override def contenido : Figura = {
    this.elemento
  }

  override def cambiarContenido(nuevoContenido: Figura): Transformacion =
    this.copy(elemento = nuevoContenido)
}
case class Escala(factorX: Double, factorY: Double, elemento: Figura) extends Transformacion {
  override def simplificar: Figura = {
    this match{
      case Escala(1, 1, figura) => figura.simplificar
      case Escala(factorX, factorY, Escala(subFactorX, subFactorY, figura))
                  => Escala(factorX * subFactorX, factorY * subFactorY, figura.simplificar).simplificar
      case Escala(factX, factY, figura) => Escala(factX, factY, figura.simplificar)
    }
  }

  override def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    elemento.dibujarse(adapter.beginScale(factorX, factorY)).end()
  }

  override def contenido: Figura = {
    this.elemento
  }

  override def cambiarContenido(nuevoContenido: Figura): Transformacion
                  = this.copy(elemento =  nuevoContenido)
}
case class Rotacion(angulo: Int, elemento: Figura) extends Transformacion {
  require(angulo >=0 && angulo <=359)
  override def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    elemento.dibujarse(adapter.beginRotate(angulo)).end()
  }
  override def simplificar: Figura =
    this match{
      case Rotacion(0, figura: Figura) => figura.simplificar
      case Rotacion(angulo1, Rotacion(subAngulo, subFigura))
                  => Rotacion(angulo1+subAngulo, subFigura.simplificar).simplificar
      case Rotacion(angulo, figura) => Rotacion(angulo, figura.simplificar)
    }

  override def cambiarContenido(nuevoContenido: Figura): Transformacion
                            = this.copy(elemento =  nuevoContenido)

  override def contenido: Figura = {
    this.elemento
  }
}
case class Traslacion(desplazamientoX: Int, desplazamientoY: Int, elemento: Figura) extends Transformacion {
  override def simplificar: Figura = {
    this match {
      case Traslacion(0, 0, figura) => figura.simplificar
      case Traslacion(desplX, desplY, Traslacion(subDesplX, subDesplY, figura))
                      => Traslacion(desplX+subDesplX, desplY+subDesplY, figura.simplificar).simplificar
      case Traslacion(desplX, desplY, figura) => Traslacion(desplX, desplY, figura.simplificar)

    }
  }

  override def dibujarse(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    elemento.dibujarse(adapter.beginTranslate(desplazamientoX,desplazamientoY)).end()
  }

  override def cambiarContenido(nuevoContenido: Figura): Transformacion
                          = this.copy(elemento =  nuevoContenido)

  override def contenido: Figura = {
    this.elemento
  }
}

