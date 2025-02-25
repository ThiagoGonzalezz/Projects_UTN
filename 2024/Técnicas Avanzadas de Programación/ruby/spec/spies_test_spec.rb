# frozen_string_literal: true

require_relative 'spec_helper'

describe 'spies' do
  using MixinDeberia

  let(:lean) { Persona.new(22) }
  let(:pato) { espiar(Persona.new(23)) }


  it 'haber_recibido pasa cuando el metodo fue llamado' do
      expect {
        pato.viejo?
        pato.deberia haber_recibido(:edad)
      }.not_to raise_error
  end

  it 'haber_recibido falla cuando el metodo no fue llamado' do
    expect {
      pato.viejo?
      pato.deberia haber_recibido(:joven?)
    }.to raise_error(ExcepcionRespuestaInesperada)
  end

  it 'haber_recibido veces pasa cuando se recibió la misma cantidad de llamados al metodo' do
    expect{
      pato.viejo?
      pato.deberia haber_recibido(:edad).veces(1)
    }.not_to raise_error
  end

  it 'haber_recibido veces falla cuando no se recibió la misma cantidad de llamados al metodo' do
    expect{
      pato.viejo?
      pato.deberia haber_recibido(:edad).veces(5)
    }.to raise_error(ExcepcionRespuestaInesperada)
  end

  it 'haber_recibido con_argumentos pasa cuando recibio un llamado al metodo con los mismos argumentos.' do
    expect{
      pato.viejo?
      pato.deberia haber_recibido(:viejo?).con_argumentos()
    }.not_to raise_error
  end

  it 'haber_recibido con_argumentos falla cuando no recibio un llamado al metodo con los mismos argumentos.' do
    expect{
      pato.viejo?
      pato.deberia haber_recibido(:viejo?).con_argumentos(19, "hola")
    }.to raise_error(ExcepcionRespuestaInesperada)
  end

  it 'haber_recibido falla cuando el objeto no fue espiado' do
    expect{lean.deberia haber_recibido(:edad)}.to raise_error(ExcepcionRespuestaInesperada)
  end

  it 'haber_recibido veces falla cuando el objeto no fue espiado' do
    expect{lean.deberia haber_recibido(:edad)}.to raise_error(ExcepcionRespuestaInesperada)
  end

  it 'haber_recibido con_argumentos falla cuando el objeto no fue espiado' do
    expect{lean.deberia haber_recibido(:edad)}.to raise_error(ExcepcionRespuestaInesperada)
  end

end
