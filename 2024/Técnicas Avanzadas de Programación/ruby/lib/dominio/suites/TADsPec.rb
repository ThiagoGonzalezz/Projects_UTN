require_relative 'respuestas_tests/RespuestaTest'
require_relative 'respuestas_tests/RespuestaCorrecta'
require_relative 'respuestas_tests/RespuestaInesperada'
require_relative 'respuestas_tests/RespuestaExplotada'
require_relative '../../utils/excepciones/ExcepcionRespuestaInesperada'
require_relative '../aserciones/mixin_deberia'
require_relative '../mock/mock_mixin'


using MockMixin
using MixinDeberia
class TADsPec

  @@lista_respuestas_inesperadas = []
  @@lista_respuestas_exitos = []
  @@lista_respuestas_explotados = []
  @@lista_mockeos = []
  @@lista_espias = []

  public
  def self.testear(clase_test = nil, *simbolos_tests)
    if clase_test.nil?
      testear_suites_en_contexto
    elsif verificar_suite(clase_test)
      if simbolos_tests.empty?
        ejecutar_todos_los_tests(clase_test)
      else
        imprimir_titulo_suite(clase_test)
        simbolos_tests.each do |test|
          correr_test(clase_test, test)
        end
      end
      mostrar_resultados
    else
      raise ExceptionNoSuite
    end
  end

  def self.testear_suites_en_contexto
    suites_en_contexto = obtener_suites_en_contexto
    suites_en_contexto.each do |suite|
      self.testear(suite)
    end
  end

  def self.registrar_mockeo(clase)
    @@lista_mockeos.append(clase)
  end

  def self.registrarEspia(espia)
    @@lista_espias.append(espia)
  end

  private

  def self.obtener_simbolos_tests(clase_test)
    clase_test.instance_methods.select{|s| verificar_metodo_test(s, clase_test)}

  end

  def self.obtener_suites_en_contexto
    ObjectSpace.each_object(Class).select{|clase| verificar_suite(clase)}
  end

  def self.ejecutar_todos_los_tests(clase_test)
    imprimir_titulo_suite(clase_test)

    tests_a_ejecutar = obtener_simbolos_tests(clase_test)
    tests_a_ejecutar.each do |test|
      correr_test(clase_test, test)
    end
  end

  def self.correr_test(clase_test, simbolo_test)

    begin
      instancia_test = clase_test.new()

      instancia_test.extend MixinTests
      instancia_test.send(simbolo_test)
      generar_respuesta_correcta(simbolo_test)

    rescue ExcepcionRespuestaInesperada => e
      generar_respuesta_inesperada(simbolo_test, e)
    rescue Exception => e
      generar_respuesta_explosion(simbolo_test, e)
    end

    reestablecer_contextos
  end

  def self.reestablecer_contextos
    reestablecer_spies
    reestablecer_mocks
  end

  def self.reestablecer_mocks
    @@lista_mockeos.each do |clase_mockeada|
      clase_mockeada.reestablecer_mockeos
    end

    @@lista_mockeos.clear
  end

  def self.reestablecer_spies
    @@lista_espias.each do |espia|
      espia.dejar_de_espiar
    end

    @@lista_espias.clear
  end

  def self.imprimir_titulo_suite(clase_test)
    puts "\n \n \n ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
    puts         " TEST SUITE:  #{clase_test.name}"
    puts         " ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"

  end
    def self.mostrar_resultados
    puts "\n ════════RESULTADOS═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"

    mostrar_estadisticas_resultados

    mostrar_resultados_exitosos

    mostrar_resultados_inesperados

    mostrar_resultados_explotados

    vaciar_listas
  end

  def self.mostrar_resultados_exitosos
    puts "\n ════EXITOS═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
    @@lista_respuestas_exitos.each do |respuesta|
      respuesta.visualizar
    end
  end

  def self.mostrar_resultados_inesperados
    puts "\n ════FALLAS═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
    @@lista_respuestas_inesperadas.each do |respuesta|
      respuesta.visualizar
    end
  end

  def self.mostrar_resultados_explotados
    puts "\n ════EXPLOSIONES══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
    @@lista_respuestas_explotados.each do |respuesta|
      respuesta.visualizar
    end
  end

  def self.mostrar_estadisticas_resultados
    cantidad_exitos = @@lista_respuestas_exitos.size
    cantidad_fallas = @@lista_respuestas_inesperadas.size
    cantidad_explotados = @@lista_respuestas_explotados.size
    cantidad_total = cantidad_exitos + cantidad_fallas + cantidad_explotados

    puts " Cantidad total de test corridos: " +  cantidad_total.to_s
    puts " Cantidad total de test exitosos: " +  cantidad_exitos.to_s
    puts " Cantidad total de test fallidos: " + cantidad_fallas.to_s
    puts " Cantidad total de test explotados: " + cantidad_explotados.to_s

  end

  def self.generar_respuesta_explosion(simbolo_test, excepcion)
    respuesta = RespuestaExplotada.new(simbolo_test, excepcion)
    @@lista_respuestas_explotados.append(respuesta)
  end

  def self.generar_respuesta_correcta(simbolo_test)
    respuesta = RespuestaCorrecta.new(simbolo_test)
    @@lista_respuestas_exitos.append(respuesta)
  end

  def self.generar_respuesta_inesperada(simbolo_test, excepcionRespuestaInesperada)
    respuesta = RespuestaInesperada.new(simbolo_test, excepcionRespuestaInesperada)
    @@lista_respuestas_inesperadas.append(respuesta)
  end

  def self.vaciar_listas
    @@lista_respuestas_exitos = []
    @@lista_respuestas_inesperadas = []
    @@lista_respuestas_explotados = []
  end

  def self.verificar_suite(clase)
    clase.instance_methods.any? { |s| verificar_metodo_test(s, clase) }
  end

  def self.verificar_metodo_test(symb, clase_test)
    symb.to_s.start_with?("testear_que_")  && clase_test.instance_method(symb).arity == 0
  end

end