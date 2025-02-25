

class ExcepcionSinEspia < RuntimeError
  def initialize(objeto)
    mensaje = "El objeto #{objeto} no fue espiado"
    super(mensaje)
  end
end