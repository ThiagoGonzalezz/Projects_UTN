package vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorPesosDonados;

import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import lombok.Setter;

@Builder
@Setter
public class CalculadorPesosDonados implements ICalculadorPesosDonados {
    @Builder.Default
    private double coeficiente = 0.5d;

    @Override
    public double calcularPuntos(Double monto){
        return monto*coeficiente;
    }

}
