require_relative '../aserciones/mixin_deberia'

using MixinDeberia
class Spy

  attr_accessor :espiado, :diccionario_estadisticas_simbolos, :diccionario_metodos_originales, :test_contexto

  public

  def initialize(espiado)
    @espiado = espiado
    @diccionario_estadisticas_simbolos = Hash.new { |hash, key| hash[key] = [] }
    @diccionario_metodos_originales = Hash.new
    espiar
  end

  def registrarLlamado(simbolo, *argumentos)
      @diccionario_estadisticas_simbolos[simbolo] << argumentos
  end

  def dejar_de_espiar
    @espiado.instance_variable_set(:@espia, nil)

    @espiado.methods.each do |simbolo|
      desespiar_metodo(simbolo)
    end
  end

  private

  def espiar
    @espiado.methods.each do |simbolo|
        espiar_metodo(simbolo)
      end
    @diccionario_estadisticas_simbolos = Hash.new { |hash, key| hash[key] = [] }
  end

  def espiar_metodo(simbolo)
    spy = self
    metodo_original = @espiado.method(simbolo)

    @espiado.define_singleton_method(simbolo) do |*args, &block|
      spy.registrarLlamado(simbolo, *args, &block)
      metodo_original.call(*args, &block)
    end

    @diccionario_metodos_originales[simbolo] = metodo_original
  end

  def desespiar_metodo(simbolo)
    metodo_original = @diccionario_metodos_originales[simbolo]

    @espiado.define_singleton_method(simbolo) do |*args, &block|
      metodo_original.call(*args, &block)
    end

  end

  public

  def recibio(simbolo)
    !@diccionario_estadisticas_simbolos[simbolo].empty?
  end

  def recibio_conjunto_argumentos(simbolo, lista_args)
    @diccionario_estadisticas_simbolos[simbolo].any? do |args_invocados|
      args_invocados == lista_args
    end
  end

  def recibio_n_llamados(simbolo, cantidad)
    @diccionario_estadisticas_simbolos[simbolo].size == cantidad
  end

end