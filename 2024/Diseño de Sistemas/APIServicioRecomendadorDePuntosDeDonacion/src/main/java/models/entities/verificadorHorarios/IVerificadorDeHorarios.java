package models.entities.verificadorHorarios;

import models.entities.puntosDeDonacion.PuntoDeDonacion;

import java.time.LocalTime;

public interface IVerificadorDeHorarios {
    boolean coincideHorario(PuntoDeDonacion puntoDonacion, LocalTime horarioBuscado);
}
