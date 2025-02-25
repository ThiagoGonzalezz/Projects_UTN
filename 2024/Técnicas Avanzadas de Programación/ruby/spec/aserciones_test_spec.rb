# frozen_string_literal: true

require_relative 'spec_helper'

describe 'ser' do
  using MixinDeberia

  let(:leandro) {Persona.new(22)}

  it 'ser pasa cuando los valores coinciden' do
    expect{7.deberia ser 7}.not_to raise_error
  end

  it 'ser falla cuando los valores no coinciden' do
    expect{true.deberia ser false}.to raise_error ExcepcionRespuestaInesperada
    expect{leandro.edad.deberia ser 25}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'ser mayor_a pasa cuando el valor es mayor a su parametro' do
    expect{leandro.edad.deberia ser mayor_a 20}.not_to raise_error
  end

  it 'ser mayor_a falla cuando el valor no es mayor a su parametro' do
    expect{leandro.edad.deberia ser mayor_a 50}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'ser menor_a pasa cuando el valor es menor a su parametro' do
    expect{leandro.edad.deberia ser menor_a 50}.not_to raise_error
  end

  it 'ser menor_a tfalla cuando el valor no es menor a su parametro' do
    expect{leandro.edad.deberia ser menor_a 20}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'ser uno_de_estos pasa cuando el valor es uno de los de la lista' do
    expect{leandro.edad.deberia ser uno_de_estos [7, 22, "hola"]}.not_to raise_error
  end

  it 'ser uno_de_estos falla cuando el valor no es uno de los de la lista' do
    expect{leandro.edad.deberia ser uno_de_estos [7, 29, "hola"]}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'ser uno_de_estos pasa cuando el valor es uno de los vargargs' do
    expect{leandro.edad.deberia ser uno_de_estos 7, 22, "hola"}.not_to raise_error
  end

  it 'ser uno_de_estos falla cuando el valor no es uno de los vargargs' do
    expect{leandro.edad.deberia ser uno_de_estos 7, 29, "hola"}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'ser_propiedad_booleana pasa cuando la propiedad booleana del valor es verdadera' do
    expect{leandro.deberia ser_joven}.not_to raise_error
  end

  it 'ser_propiedad_booleana falla cuando la propiedad booleana del valor es falso' do
    expect{leandro.deberia ser_viejo}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'ser_propiedad_booleana explota cuando el valor posee la propiedad booleana' do
    expect{leandro.deberia ser_arquitecto}.to raise_error NoMethodError
  end

end

describe 'tener' do
  using MixinDeberia

  let(:leandro) {Persona.new(22)}

  it 'tener_atributo pasa cuando los valores coinciden' do
    expect{leandro.deberia tener_edad 22}.not_to raise_error
  end

  it 'tener_atributo falla cuando los valores no coinciden' do
    expect{leandro.deberia tener_edad 50}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'tener_atributo pasa cuando no tiene el atributo y el valor esperado es nil' do
    expect{leandro.deberia tener_edad 22}.not_to raise_error
  end

  it 'tener_atributo falla cuando no existe el atributo y el valor esperado no es nil' do
    expect{leandro.deberia tener_nombre "leandro"}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'tener_atributo mayor_a pasa cuando el valor es mayor a su parametro' do
    expect{leandro.deberia tener_edad mayor_a 20}.not_to raise_error
  end

  it 'tener_atributo mayor_a falla cuando el valor no es mayor a su parametro' do
    expect{leandro.deberia tener_edad mayor_a 50}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'tener_atributo menor_a pasa cuando el valor es menor a su parametro' do
    expect{leandro.deberia tener_edad menor_a 50}.not_to raise_error
  end

  it 'tener_atributo menor_a falla cuando el valor no es menor a su parametro' do
    expect{leandro.deberia tener_edad menor_a 20}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'tener_atributo uno_de_estos pasa cuando el valor es uno de los de la lista' do
    expect{leandro.edad.deberia ser uno_de_estos [7, 22, "hola"]}.not_to raise_error
  end

  it 'tener_atributo uno_de_estos falla cuando el valor no es uno de los de la lista' do
    expect{leandro.deberia tener_edad uno_de_estos [7, 29, "hola"]}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'tener_atributo uno_de_estos pasa cuando el valor es uno de los vargargs' do
    expect{leandro.deberia tener_edad uno_de_estos 7, 22, "hola"}.not_to raise_error
  end

  it 'tener_atributo uno_de_estos falla cuando el valor no es uno de los vargargs' do
    expect{leandro.deberia tener_edad uno_de_estos 7, 29, "hola"}.to raise_error ExcepcionRespuestaInesperada
  end

end

describe 'entender' do
  using MixinDeberia

  let(:leandro) {Persona.new(22)}

  it 'entender pasa cuando es un metodo de su clase' do
    expect{leandro.deberia entender :viejo?}.not_to raise_error
  end

  it 'entender pasa cuando es un metodo de heredado' do
    expect{leandro.deberia entender :class}.not_to raise_error
  end

  it 'entender falla cuando no entiende el mensaje' do
    expect{leandro.deberia entender :nombre}.to raise_error ExcepcionRespuestaInesperada
  end

end


describe 'explotar' do
  using MixinDeberia

  let(:leandro) {Persona.new(22)}

  it 'explotar pasa cuando explota con la misma excepcion esperada' do
    expect{en { 7 / 0 }.deberia explotar_con ZeroDivisionError}.not_to raise_error
    expect{en { leandro.nombre }.deberia explotar_con NoMethodError}.not_to raise_error
  end

  it 'explotar pasa cuando explota con una excepcion que hereda de la esperada' do
    expect{en { leandro.nombre }.deberia explotar_con Exception}.not_to raise_error
  end

  it 'explotar falla cuando explota con una excepcion distinta de la esperada' do
    expect{en { 7 / 0 }.deberia explotar_con NoMethodError}.to raise_error ExcepcionRespuestaInesperada
  end

  it 'explotar falla cuando no explota' do
    expect{en { leandro.viejo?}.deberia explotar_con NoMethodError}.to raise_error ExcepcionRespuestaInesperada
  end

end




