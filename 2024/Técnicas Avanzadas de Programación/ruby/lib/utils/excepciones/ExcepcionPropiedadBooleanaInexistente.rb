class ExceptionPropiedadBooleanaInexistente < StandardError
  def initialize(msg="La propiedad booleana indicada es inexistente")
    super(msg)
  end
end