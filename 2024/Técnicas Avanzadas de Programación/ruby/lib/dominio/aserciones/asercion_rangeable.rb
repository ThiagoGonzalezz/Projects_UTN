# frozen_string_literal: true

class AsercionRangeable < Asercion
  attr_accessor :resultado

  def cumple_condicion(objeto)

    objeto_preparado = objeto_preparado(objeto)

    if valor_esperado_es_una_asercion
      @valor_esperado.cumple_condicion(objeto_preparado)
    else
      @resultado = objeto_preparado
      objeto_preparado == @valor_esperado
    end
  end

  def respuesta_esperada
    if valor_esperado_es_una_asercion
      @valor_esperado.respuesta_esperada
    else
      super
    end
  end

  def respuesta_obtenida
    if valor_esperado_es_una_asercion
      @valor_esperado.respuesta_obtenida
    else
      @resultado.to_s
    end
  end

  private

  def valor_esperado_es_una_asercion
    @valor_esperado.is_a?(Asercion)
  end

end
