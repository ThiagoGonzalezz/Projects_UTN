

class ExceptionFueraDeTest < StandardError
  def initialize(msg="La ejecución del metodo debería tiene que estar dentro de un Test")
    super(msg)
  end


end