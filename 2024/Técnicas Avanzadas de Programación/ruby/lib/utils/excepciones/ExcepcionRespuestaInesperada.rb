# frozen_string_literal: true

class ExcepcionRespuestaInesperada < RuntimeError
  def initialize(esperado, obtenido)
    mensaje = "Test ejecutado sin éxito: Respuesta esperada: " + esperado.to_s + " - Respuesta obtenida: " +  obtenido.to_s
    super(mensaje)
  end
end
