# frozen_string_literal: true

require_relative 'asercion_basica'

class AsercionSpy < AsercionBasica

  attr_accessor :simbolo, :respuesta_buscada, :espia
  public
  def initialize(simbolo)
    @metodo_basico = :recibio
    @simbolo = simbolo
    @respuesta_buscada = ":haber_recibido " + @simbolo.to_s
  end

  def veces(cantidad)
    @valor_esperado = cantidad
    @metodo_basico = :recibio_n_llamados
    @respuesta_buscada = :haber_recibido.to_s + " " + @simbolo.to_s + " :veces " + @valor_esperado.to_s
    return self
  end

  def con_argumentos(*argumentos)
    @valor_esperado = argumentos
    @metodo_basico = :recibio_conjunto_argumentos
    @respuesta_buscada = :haber_recibido.to_s + " " + @simbolo.to_s + " :con_argumentos " + argumentos.to_s
    return self
  end

  def cumple_condicion(objeto)
    @espia = objeto.instance_variable_get(:@espia)
    if !@espia.nil?
      if @valor_esperado.nil?
        @espia.send(@metodo_basico, @simbolo)
      else
        @espia.send(@metodo_basico, @simbolo, @valor_esperado)
      end
    else
      false
    end
  end

  def respuesta_esperada
    @respuesta_buscada.to_s
  end

  def respuesta_obtenida
    if @espia.nil?
      "El objeto no fue espiado"
    else
      super
    end
  end

end