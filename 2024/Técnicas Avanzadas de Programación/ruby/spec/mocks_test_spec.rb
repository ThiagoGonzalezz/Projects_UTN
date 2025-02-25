# frozen_string_literal: true

require_relative 'spec_helper'
require_relative '../poc/spies_poc'

describe 'mock' do
  using MixinDeberia
  using MockMixin

  it 'No mockeados 1' do
    expect{
      viejos = PersonaHome.new.personas_viejas

      viejos.deberia ser []
    }.not_to raise_error
  end

  it 'Mockeando todas_las_personas' do
    expect{
      nico = Persona.new(65)
      axel = Persona.new(65)
      lean = Persona.new(22)

      PersonaHome.mockear(:todas_las_personas) do
        [nico, axel, lean]
      end

      viejos = PersonaHome.new.personas_viejas

      viejos.deberia ser [nico, axel]
    }.not_to raise_error
  end

  it 'No mockeados 2' do
    TADsPec.testear(SpiesTest)
    expect{
      viejos = PersonaHome.new.personas_viejas

      viejos.deberia ser []
    }.not_to raise_error
  end


end
