package vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas;

import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;

public interface ICalculadorViandasDonadas {
    public double calcularPuntos(DonacionDeVianda donacionDeVianda);
}
