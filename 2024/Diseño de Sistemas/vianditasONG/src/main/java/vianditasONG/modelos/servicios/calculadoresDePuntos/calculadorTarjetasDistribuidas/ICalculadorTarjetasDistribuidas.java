package vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas;

import vianditasONG.modelos.entities.colaboraciones.RegistroPersonasVulnerables;

public interface ICalculadorTarjetasDistribuidas {
    public double calcularPuntos(RegistroPersonasVulnerables registroPersonasVulnerables);
}
