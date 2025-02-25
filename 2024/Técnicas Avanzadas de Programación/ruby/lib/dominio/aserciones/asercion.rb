# frozen_string_literal: true

require_relative '../../utils/ExtensionIsIncluded'
require_relative '../../utils/excepciones/ExcepcionRespuestaInesperada'

class Asercion
  attr_accessor :valor_esperado

  def initialize(valor)
    @valor_esperado = valor
  end

  def verificar(objeto)
    unless cumple_condicion(objeto)
      raise ExcepcionRespuestaInesperada.new(respuesta_esperada, respuesta_obtenida)
    end
  end

  def respuesta_esperada
    @valor_esperado.to_s
  end
end
