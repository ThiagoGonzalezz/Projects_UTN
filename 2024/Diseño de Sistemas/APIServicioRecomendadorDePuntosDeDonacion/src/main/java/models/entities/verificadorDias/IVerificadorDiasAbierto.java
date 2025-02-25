package models.entities.verificadorDias;

import models.entities.puntosDeDonacion.PuntoDeDonacion;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface IVerificadorDiasAbierto {
    boolean coincideAlgunDia(PuntoDeDonacion puntoDonacion, List<DayOfWeek> diasBuscados);
}
