# frozen_string_literal: true
require_relative 'asercion'

class AsercionBasica < Asercion
  attr_accessor :metodo_basico

  def initialize(metodo_basico, valor_esperado=nil)
    @metodo_basico = metodo_basico
    super(valor_esperado)
  end

  def cumple_condicion(objeto)
    if valor_esperado.nil?
      objeto.send(@metodo_basico)
    else
      return objeto.send(@metodo_basico, @valor_esperado)
    end
  end

  def respuesta_esperada
    @metodo_basico.to_s + " " + @valor_esperado.to_s
  end

  def respuesta_obtenida
    "falso"
  end

end
