# frozen_string_literal: true

require_relative 'asercion'
require_relative 'asercion_basica'
require_relative 'asercion_ser'
require_relative 'asercion_tener'
require_relative 'asercion_spy'
require_relative 'mixin_deberia'
require_relative '../../utils/ExtensionIsIncluded'
require_relative '../suites/TADsPec'
require_relative '../spies/spy'

module MixinTests

  public
  def espiar(objeto)
      if objeto.instance_variable_get(:@espia).nil?
        espia = Spy.new(objeto)
        objeto.instance_variable_set(:@espia, espia)
        TADsPec.registrarEspia(espia)
        objeto
      else
        objeto
      end
  end

  def haber_recibido(simbolo)
    AsercionSpy.new(simbolo)
  end

  def ser(valor_o_rango)
    AsercionSer.new(valor_o_rango)
  end

  def mayor_a(valor)
    AsercionBasica.new(:>, valor)
  end

  def menor_a(valor)
    AsercionBasica.new(:<, valor)
  end

  def uno_de_estos(*valores)
    lista = valores.flatten
    AsercionBasica.new(:is_included?, lista)
  end

  def entender(simbolo)
    AsercionBasica.new(:respond_to?, simbolo)
  end

  def en(&block)
    begin
      block.yield
    rescue => error
      error
    end
  end

  def explotar_con(error)
    AsercionBasica.new(:is_a?, error)
  end

  private
  def method_missing (symbol,*args)
    case symbol.to_s

    when /ser_/
      prop_a_buscar = symbol.to_s.sub("ser_","") + "?"
      AsercionBasica.new(prop_a_buscar.to_sym)

    when /tener_/
      atributo_a_buscar = "@" + symbol.to_s.sub("tener_","")
      AsercionTener.new(atributo_a_buscar, args[0])

    else
      super(symbol, *args)
    end
  end


  def respond_to_missing?(symbol, *args)
      case symbol.to_s

      when /ser_/
        true

      when /tener_/
        true

      else
        super(symbol, *args)
      end
  end
end
