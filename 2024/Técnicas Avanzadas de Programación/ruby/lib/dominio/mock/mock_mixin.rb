#frozen_string_literal: true

require_relative '../suites/TADsPec'

module MockMixin

  @@diccionario_clases_simbolos_metodos_originales = Hash.new { |hash, clave| hash[clave] = {} }

  refine Module do

    public

    def mockear(simbolo_a_mockear, &comportamiento_mockeado)

      metodo_original = self.instance_method(simbolo_a_mockear)

      define_method(simbolo_a_mockear) do |*args, &block|
        comportamiento_mockeado.call(*args, &block)
      end

      TADsPec.registrar_mockeo(self)
      registrar_mockeo(self, simbolo_a_mockear, metodo_original)

    end

    def reestablecer_mockeos

      @@diccionario_clases_simbolos_metodos_originales[self].each do |simbolo, metodo_original|
        reestablecer_metodo(simbolo, metodo_original)
      end

      @@diccionario_clases_simbolos_metodos_originales[self] = Hash.new

    end

    private

    def registrar_mockeo(clase, simbolo_a_mockear, metodo_original)

      if@@diccionario_clases_simbolos_metodos_originales[clase][simbolo_a_mockear].nil?
        @@diccionario_clases_simbolos_metodos_originales[clase][simbolo_a_mockear] = metodo_original
      end

    end


    def reestablecer_metodo(simbolo, metodo_original)

      define_method(simbolo) do |*args, &block|
        metodo_original.bind(self).call(*args, &block)
      end

    end
  end

end