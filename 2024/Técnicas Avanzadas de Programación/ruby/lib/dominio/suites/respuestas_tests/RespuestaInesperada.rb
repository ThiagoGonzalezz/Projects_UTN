# frozen_string_literal: true

require_relative 'RespuestaTest'
class RespuestaInesperada < RespuestaTest
  def initialize(simbolo_test, excepcionRespuestaInesperada)
    super(simbolo_test, "FALLIDO","⚠️", "\e[33m", excepcionRespuestaInesperada.message)
  end
end
