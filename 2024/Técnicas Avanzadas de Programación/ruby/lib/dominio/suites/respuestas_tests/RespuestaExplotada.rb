# frozen_string_literal: true

 require_relative 'RespuestaTest'

class RespuestaExplotada < RespuestaTest
  def initialize(simbolo_test, excepcion)
    super(simbolo_test, "EXPLOTADO", "❌", "\e[31m", mensaje(excepcion))
  end

  private
  def mensaje(excepcion)
    "#{excepcion.message}\n" \
    " Excepción lanzada: #{excepcion.class}\n" \
    " Stack trace:\n #{excepcion.backtrace.join("\n ")}"
  end
end
