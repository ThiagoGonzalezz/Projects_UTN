package services;

import lombok.Builder;
import lombok.Setter;
import models.entities.datosGeograficos.PuntoGeografico;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import models.entities.recomendador.RecomendadorDePuntos;
import utils.ServiceLocator;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface IRecomendadorDePuntosDonacionesService {

    List<PuntoDeDonacion> recomendarPuntos(double latitud, double longitud, float radio);

    List<PuntoDeDonacion> recomendarPuntos(double latitud, double longitud, float radio, LocalTime horaBuscada);

    List<PuntoDeDonacion> recomendarPuntos(double latitud, double longitud, float radio, List<DayOfWeek> diasBuscados);

    List<PuntoDeDonacion> recomendarPuntos(double latitud, double longitud, float radio, LocalTime horaBuscada, List<DayOfWeek> diasBuscados);
}
