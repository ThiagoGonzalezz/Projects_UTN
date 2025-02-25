# frozen_string_literal: true

require_relative 'asercion'


module MixinDeberia
  refine Object do

    def deberia(asercion)
      asercion.verificar(self)
    end

  end
end
