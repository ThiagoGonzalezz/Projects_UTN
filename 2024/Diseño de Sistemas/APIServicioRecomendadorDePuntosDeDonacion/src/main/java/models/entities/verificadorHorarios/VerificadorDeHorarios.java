package models.entities.verificadorHorarios;

import lombok.Builder;
import models.entities.puntosDeDonacion.PuntoDeDonacion;

import java.time.LocalTime;

@Builder
public class VerificadorDeHorarios implements IVerificadorDeHorarios {

    @Override
    public boolean coincideHorario(PuntoDeDonacion puntoDonacion, LocalTime horarioBuscado){
        return horarioBuscado.isBefore(puntoDonacion.getHoraCierre())
                && horarioBuscado.isAfter(puntoDonacion.getHoraApertura());
    }

}
