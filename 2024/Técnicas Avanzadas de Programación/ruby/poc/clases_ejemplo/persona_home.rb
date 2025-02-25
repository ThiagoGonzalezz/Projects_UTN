# frozen_string_literal: true

class PersonaHome
  def todas_las_personas
    nico = Persona.new(23)
    axel = Persona.new(23)
    lean = Persona.new(22)

    [nico, axel, lean]
  end

  def personas_viejas
    self.todas_las_personas.select{|p| p.viejo?}
  end

end
