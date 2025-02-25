package models.entities.verificadorDias;

import lombok.Builder;
import models.entities.puntosDeDonacion.PuntoDeDonacion;

import java.time.DayOfWeek;
import java.util.List;

@Builder
public class VerificadorDiasAbierto implements IVerificadorDiasAbierto{
    @Override
    public boolean coincideAlgunDia(PuntoDeDonacion puntoDonacion, List<DayOfWeek> diasBuscados){
        return diasBuscados.stream().anyMatch(d -> puntoDonacion.getDiasAbierto().contains(d));
    }
}
