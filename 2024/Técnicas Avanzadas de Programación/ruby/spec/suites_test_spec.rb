

require_relative 'spec_helper'

  describe 'detección de tests válidos' do

  it "detecta un método que comienza con 'testear_que_' como test" do
    suite = Class.new do
      def testear_que_algo_pasa; end
    end

    expect(TADsPec.verificar_metodo_test(:testear_que_algo_pasa, suite)).to be true
  end

  it "no detecta métodos que no comienzan con 'testear_que_' como test" do
    suite = Class.new do
      def otro_metodo; end
    end
    expect(TADsPec.verificar_metodo_test(:otro_metodo, suite)).to be false
  end
  end

  describe "Correr una suite de tests en particular " do
    using MixinDeberia
    it "ejecuta todos los tests de una suite y devuelve los resultados" do
      suite = Class.new do
        def testear_que_funciona_algo
          7.deberia ser 7
        end

        def testear_que_funciona_otra_cosa
          7.deberia ser uno_de_estos [2,4,7]
        end
        def testear_que_falla_algo
          7.deberia ser 23
        end

        def esto_no_es_un_test
          self
        end
      end
      #spies de ruby
      allow(TADsPec).to receive(:generar_respuesta_correcta).and_call_original
      allow(TADsPec).to receive(:generar_respuesta_explosion).and_call_original
      allow(TADsPec).to receive(:generar_respuesta_inesperada).and_call_original

      TADsPec.testear(suite)

      expect(TADsPec).to have_received(:generar_respuesta_correcta).twice
      expect(TADsPec).to have_received(:generar_respuesta_inesperada).once
      expect(TADsPec).not_to have_received(:generar_respuesta_explosion)
    end
  end


  describe "Correr un test específico de una suite" do
    it "ejecuta un test en particular de una suite" do
      suite = Class.new do
        def testear_que_explota_algo
          7 / 0
        end
      end

      allow(TADsPec).to receive(:generar_respuesta_correcta).and_call_original
      allow(TADsPec).to receive(:generar_respuesta_explosion).and_call_original
      allow(TADsPec).to receive(:generar_respuesta_inesperada).and_call_original

      TADsPec.testear(suite, :testear_que_explota_algo)

      expect(TADsPec).to have_received(:generar_respuesta_explosion).once
      expect(TADsPec).not_to have_received(:generar_respuesta_inesperada)
      expect(TADsPec).not_to have_received(:generar_respuesta_correcta)
    end

  end

  describe "Correr todas las suites que se hayan importado al contexto" do
    using MixinDeberia
    it "ejecuta todas las suites" do
      suite1 = Class.new do
        def testear_que_funciona
          2.deberia ser uno_de_estos [35,2]
        end
      end

      suite2 = Class.new do
        def testear_que_falla
          2.deberia ser uno_de_estos [35,6]
        end
      end

      Object.const_set('Suite1', suite1)
      Object.const_set('Suite2', suite2)

      allow(TADsPec).to receive(:generar_respuesta_correcta).and_call_original
      allow(TADsPec).to receive(:generar_respuesta_inesperada).and_call_original
      allow(TADsPec).to receive(:generar_respuesta_explosion).and_call_original

      TADsPec.testear

      expect(TADsPec).to have_received(:generar_respuesta_correcta).once
      expect(TADsPec).to have_received(:generar_respuesta_inesperada).once
      expect(TADsPec).not_to have_received(:generar_respuesta_explosion)
    end

  end

describe "suite sin métodos testear_que" do
  it "intentar ejecutar una suite sin tests válidos" do
    suite = Class.new do
      def metodo_no_es_test; end
      def este_metodo_tampoco_es_un_test; end
    end

    allow(TADsPec).to receive(:generar_respuesta_correcta).and_call_original
    allow(TADsPec).to receive(:generar_respuesta_inesperada).and_call_original
    allow(TADsPec).to receive(:generar_respuesta_explosion).and_call_original

    TADsPec.testear(suite)

    expect(TADsPec).not_to have_received(:generar_respuesta_correcta)
    expect(TADsPec).not_to have_received(:generar_respuesta_inesperada)
    expect(TADsPec).not_to have_received(:generar_respuesta_explosion)
  end
end







