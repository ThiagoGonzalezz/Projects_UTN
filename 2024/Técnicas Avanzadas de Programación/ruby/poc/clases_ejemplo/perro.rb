# frozen_string_literal: true

class Perro

  attr_accessor :edad

  def initialize(edad)
    @edad = edad
  end

  def viejo?
    @edad > 10
  end

  def joven?
    @edad < 10
  end

end
