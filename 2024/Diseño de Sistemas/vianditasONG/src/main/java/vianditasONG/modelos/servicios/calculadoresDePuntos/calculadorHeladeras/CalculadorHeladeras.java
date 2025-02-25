package vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorHeladeras;

import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.modelos.entities.colaboraciones.HacerseCargoDeHeladera;
import vianditasONG.modelos.entities.colaboradores.Puntuable;
import lombok.Setter;

import java.util.List;
@Builder
@Setter
public class CalculadorHeladeras implements ICalculadorHeladeras {
    @Builder.Default
    private double coeficiente = 5d;

    public Double calcularPuntos(Long cantHeladerasActivas, Long totalMesesActiva) {
       return (double) (cantHeladerasActivas * totalMesesActiva);
    }

}
