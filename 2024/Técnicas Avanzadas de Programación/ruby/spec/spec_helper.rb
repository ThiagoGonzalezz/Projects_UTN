require 'rspec'
require_relative '../lib/dominio/aserciones/mixin_deberia'
require_relative '../lib/dominio/aserciones/mixin_tests'
require_relative '../lib/utils/excepciones/ExcepcionRespuestaInesperada'
require_relative '../poc/clases_ejemplo/persona'
require_relative '../poc/clases_ejemplo/perro'
require_relative '../poc/clases_ejemplo/persona_home'


RSpec.configure do |config|

  config.include MixinTests

end