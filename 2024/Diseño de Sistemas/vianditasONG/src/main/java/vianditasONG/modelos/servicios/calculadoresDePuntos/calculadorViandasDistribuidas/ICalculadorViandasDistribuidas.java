package vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas;

import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;

public interface ICalculadorViandasDistribuidas {
    public double calcularPuntos(DistribucionDeVianda distribucionDeVianda);
}
