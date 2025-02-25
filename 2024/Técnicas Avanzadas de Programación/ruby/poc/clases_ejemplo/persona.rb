
class Persona
  attr_accessor :edad

  def initialize(edad)
    @edad = edad
  end

  def viejo?
    self.edad > 60
  end

  def joven?
    self.edad < 60
  end
end
