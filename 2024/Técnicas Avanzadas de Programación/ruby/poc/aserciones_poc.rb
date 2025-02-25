require_relative 'clases_ejemplo/persona'
require_relative '../lib/dominio/aserciones/mixin_deberia'
require_relative '../lib/dominio/suites/TADsPec'
require_relative '../lib/dominio/aserciones/mixin_tests'

using MixinDeberia

class AsercionesTest

  attr_accessor :pedro

  public

  def initialize()
    @pedro = Persona.new(22)
  end

  def testear_que_pedro_edad_es_22
    @pedro.edad.deberia ser 22
  end

  def testear_que_pedro_edad_es_27
    @pedro.edad.deberia ser 27
  end

  def testear_que_pedro_edad_es_una_de_la_lista_18_22_35
    @pedro.edad.deberia ser uno_de_estos [18, 22, 35]
  end
  def testear_que_pedro_edad_es_una_de_vargars_18_22_35
    @pedro.edad.deberia ser uno_de_estos 18, 22, 35
  end
  def testear_que_pedro_edad_es_una_de_la_lista_18_27_35
    @pedro.edad.deberia ser uno_de_estos [18, 27, 35]
  end
  def testear_que_pedro_edad_es_una_de_vargars_18_27_25
    @pedro.edad.deberia ser uno_de_estos 18, 27, 35
  end
  def testear_que_pedro_edad_es_mayor_a_10
    @pedro.edad.deberia ser mayor_a 10
  end
  def testear_que_pedro_edad_es_mayor_a_40
    @pedro.edad.deberia ser mayor_a 40
  end
  def testear_que_pedro_edad_es_menor_a_40
    @pedro.edad.deberia ser menor_a 40
  end
  def testear_que_pedro_edad_es_menor_a_10
    @pedro.edad.deberia ser menor_a 10
  end

  def testear_que_pedro_es_joven
    @pedro.deberia ser_joven
  end
  def testear_que_pedro_es_viejo
    @pedro.deberia ser_viejo
  end
  #  def testear_que_pedro_es_pato_explota
  #    @pedro.edad.deberia ser_pato
  #  end
  def testear_que_explota
    en { 7 / 0 }.deberia explotar_con ZeroDivisionError
  end

  def testear_que_explota_inesperadamente
    (7/0).deberia ser 7
  end

  def testear_que_pedro_entiende_edad
    @pedro.deberia entender :edad
  end

  def testear_que_pedro_no_entiende_altura
    @pedro.deberia entender :altura
  end

  def testear_que_tiene_edad_22
    @pedro.deberia tener_edad 22
  end

  def testear_que_tiene_edad_menor_a_60
    @pedro.deberia tener_edad menor_a 60
  end

  def testear_que_tiene_edad_mayor_a_10
    @pedro.deberia tener_edad mayor_a 10
  end

  def testear_que_tiene_edad_una_de_33_22_27
    @pedro.deberia tener_edad uno_de_estos 33, 22, 27
  end

  def testear_que_tiene_edad_una_de_33_22_27_lista
    @pedro.deberia tener_edad uno_de_estos [33, 22, 27]
  end

  def testear_que_no_tiene_edad_99
    @pedro.deberia tener_edad 99
  end

  def testear_que_tiene_altura_nil_inexistente
    @pedro.deberia tener_altura nil
  end

  def testear_que_no_tiene_altura_20_inexistente
    @pedro.deberia tener_altura 20
  end

  def testear_que_tiene_no_edad_menor_a_10
    @pedro.deberia tener_edad menor_a 10
  end

  def testear_que_tiene_edad_no_mayor_a_80
    @pedro.deberia tener_edad mayor_a 80
  end

  def testear_que_no_tiene_edad_una_de_33_57_27
    @pedro.deberia tener_edad uno_de_estos 33, 57, 27
  end

  def testear_que_no_tiene_edad_una_de_33_57_27_lista
    @pedro.deberia tener_edad uno_de_estos [33, 57, 27]
  end

  def testear_que_explota_el_ser_prop_booleana_inexistente
    @pedro.deberia ser_argentino
  end

  def testear_que_no_tira_error_cuando_se_esperaba_uno
    en {@pedro.viejo?}.deberia explotar_con NoMethodError
  end

end

TADsPec.testear(AsercionesTest)
