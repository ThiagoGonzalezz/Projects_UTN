# frozen_string_literal: true

require_relative 'asercion_rangeable'
class AsercionTener < AsercionRangeable
  attr_accessor :atributo
  def initialize(atributo, valor_esperado)
    @atributo = atributo
    super(valor_esperado)
  end

  private

  def objeto_preparado(objeto)
    objeto.instance_variable_get(@atributo.to_sym)  #devuelve el valor del atributo
  end

end
