require_relative '../lib/dominio/suites/TADsPec'
require_relative '../lib/dominio/spies/spy'
require_relative '../lib/dominio/aserciones/mixin_tests'
require_relative '../lib/dominio/aserciones/mixin_deberia'
require_relative 'clases_ejemplo/persona'

using MixinDeberia

class SpiesTest

  attr_accessor :pedro

  public

  def initialize()
    @pedro = Persona.new(22)
  end

  def testear_que_el_spi_devuelve_true_recibio_el_llamado
    pedro = espiar(@pedro)

    pedro.viejo?

    pedro.deberia haber_recibido(:viejo?)

    pedro.deberia haber_recibido(:edad)

  end

  def testear_que_anda_el_haber_recibido_veces

    pedro = espiar(@pedro)

    pedro.viejo?

    pedro.viejo?

    pedro.joven?

    pedro.deberia haber_recibido(:viejo?).veces(2)

    pedro.deberia haber_recibido(:joven?).veces(1)

    pedro.deberia haber_recibido(:edad).veces(3)

  end

  def testear_que_anda_recibio_argumentos

    pedro = espiar(@pedro)

    pedro.viejo?

    pedro.edad = 32

    pedro.deberia haber_recibido(:viejo?).con_argumentos()

    pedro.deberia haber_recibido(:edad).con_argumentos()

    pedro.deberia haber_recibido(:edad=).con_argumentos(32)


  end
  def testear_que_anda_haber_recibido_cuando_es_falso

    pedro = espiar(@pedro)

    pedro.viejo?

    pedro.edad = 32

    pedro.deberia haber_recibido(:joven?)


  end

  def testear_que_anda_haber_recibido_veces_cuando_es_falso

    pedro = espiar(@pedro)

    pedro.viejo?

    pedro.edad = 32

    pedro.deberia haber_recibido(:viejo?).veces(0)


  end

  def testear_que_anda_haber_recibido_con_argumentos_cuando_es_falso

    pedro = espiar(@pedro)

    pedro.viejo?

    pedro.edad = 32

    pedro.deberia haber_recibido(:viejo?).con_argumentos(1)


  end

  def testear_que_falla_cuando_no_fue_espiado

    pedro.viejo?

    pedro.edad = 32

    pedro.deberia haber_recibido(:viejo?).con_argumentos(1)

  end

end

TADsPec.testear(SpiesTest)

