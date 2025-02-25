
class RespuestaTest
  attr_accessor :simbolo_test, :resultado, :mensajeAsociado, :icono, :color

  public def visualizar
    puts "\n#{color} #{icono} Test: #{simbolo_test} - Resultado: #{resultado} \e[0m"

    unless mensajeAsociado.nil?
      puts "#{color} Detalles: #{mensajeAsociado}  \e[0m "
    end
  end

  public def initialize(simbolo_test, resultado, icono, color, mensajeAsociado = nil )
    @simbolo_test = simbolo_test
    @resultado = resultado
    @mensajeAsociado = mensajeAsociado
    @icono = icono
    @color = color
  end
end