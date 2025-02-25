package vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;
import lombok.Setter;


@Builder
@Setter
public class CalculadorTarjetasDistribuidas implements ICalculadorTarjetasDistribuidas{
    @Builder.Default
    private double coeficiente = 2d;

    public double calcularPuntos(RegistroPersonasVulnerables registroPersonasVulnerables){
        return coeficiente;
    }
}