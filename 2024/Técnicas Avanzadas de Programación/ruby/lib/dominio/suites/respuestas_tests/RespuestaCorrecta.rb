# frozen_string_literal: true

require_relative 'RespuestaTest'

class RespuestaCorrecta < RespuestaTest
  def initialize(simbolo_test)
    super(simbolo_test, "EXITO", "✅","\e[32m")
  end
end
