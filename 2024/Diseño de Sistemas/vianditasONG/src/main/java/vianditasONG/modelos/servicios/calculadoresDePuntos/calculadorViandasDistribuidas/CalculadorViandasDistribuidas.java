package vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import lombok.Setter;

@Builder
@Setter
public class CalculadorViandasDistribuidas implements ICalculadorViandasDistribuidas {

    @Builder.Default
    private double coeficiente = 1d;

    public double calcularPuntos(DistribucionDeVianda distribucionDeVianda){
        return distribucionDeVianda.getCantidadDeViandas()*coeficiente;
    }
}

