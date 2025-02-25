package vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorHeladeras;

import vianditasONG.modelos.entities.colaboradores.Puntuable;

public interface ICalculadorHeladeras {
    public Double calcularPuntos(Long cantHeladerasActivas, Long totalMesesActiva);
}
