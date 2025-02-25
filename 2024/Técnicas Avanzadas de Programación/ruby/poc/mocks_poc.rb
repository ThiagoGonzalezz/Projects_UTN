# frozen_string_literal: true
require_relative 'clases_ejemplo/perro'
require_relative 'clases_ejemplo/persona'
require_relative '../lib/dominio/suites/TADsPec'
require_relative '../lib/dominio/mock/mock_mixin'
require_relative '../lib/dominio/aserciones/mixin_deberia'
require_relative '../lib/dominio/aserciones/mixin_tests'

using MixinDeberia
using MockMixin

class TestPersonaMocks

  attr_accessor :pedro

  public

  def initialize()
    @pedro = Persona.new(22)
    @perro = Perro.new(7)
  end

  def testear_que_anda_el_mock
    Persona.mockear(:viejo?) do
      65
    end

    @pedro.viejo?.deberia ser 65

    @pedro.viejo?.deberia ser 65
  end

  def testear_que_se_fue_el_mock

    @pedro.viejo?.deberia ser false

  end

  def testear_que_anda_el_segundo_mock
    Persona.mockear(:viejo?) do
      "anda"
    end

    @pedro.viejo?.deberia ser "anda"

  end

  def testear_que_anda_doble_mock_mismo_metodo

    Persona.mockear(:viejo?) do
      "anda"
    end

    @pedro.viejo?.deberia ser "anda"

    Persona.mockear(:viejo?) do
      "andaX2"
    end

    @pedro.viejo?.deberia ser "andaX2"

  end

  def testear_que_anda_varios_mocks_de_distintos_metodos_misma_clase
    Persona.mockear(:viejo?) do
      "anda"
    end

    Persona.mockear(:joven?) do
      "tambien anda"
    end

    @pedro.viejo?.deberia ser "anda"
    @pedro.joven?.deberia ser "tambien anda"

  end

  def testear_que_anda_varios_mocks_mismo_simbolo_distinta_clase
    Persona.mockear(:viejo?) do
      "anda"
    end

    Perro.mockear(:viejo?) do
      "anda tambien"
    end

    @pedro.viejo?.deberia ser "anda"

    @perro.viejo?.deberia ser "anda tambien"

  end

  def testear_que_se_fueron_todos_los_mocks

    @pedro.viejo?.deberia ser false

    @perro.viejo?.deberia ser false

    @pedro.joven?.deberia ser true

  end

end

TADsPec.testear