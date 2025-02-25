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
@Builder
public class RecomendadorDePuntosDonacionesDonacionesService implements IRecomendadorDePuntosDonacionesService {


    @Builder.Default
    @Setter
    private RecomendadorDePuntos recomendadorDePuntos = ServiceLocator.getService(RecomendadorDePuntos.class);

    @Override
    public List<PuntoDeDonacion> recomendarPuntos(double latitud, double longitud, float radio) {
        PuntoGeografico puntoInicial = PuntoGeografico.builder().latitud(latitud).longitud(longitud).build();
        return  recomendadorDePuntos.recomendarPuntosDeDonacion(puntoInicial, radio);
    }

    @Override
    public List<PuntoDeDonacion> recomendarPuntos(double latitud, double longitud, float radio, LocalTime horaBuscada) {
        PuntoGeografico puntoInicial = PuntoGeografico.builder().latitud(latitud).longitud(longitud).build();
        return  recomendadorDePuntos.recomendarPuntosDeDonacion(puntoInicial, radio, horaBuscada);
    }

    @Override
    public List<PuntoDeDonacion> recomendarPuntos(double latitud, double longitud, float radio, List<DayOfWeek> diasBuscados) {
        PuntoGeografico puntoInicial = PuntoGeografico.builder().latitud(latitud).longitud(longitud).build();
        return  recomendadorDePuntos.recomendarPuntosDeDonacion(puntoInicial, radio, diasBuscados);
    }

    @Override
    public List<PuntoDeDonacion> recomendarPuntos(double latitud, double longitud, float radio, LocalTime horaBuscada, List<DayOfWeek> diasBuscados) {
        PuntoGeografico puntoInicial = PuntoGeografico.builder().latitud(latitud).longitud(longitud).build();
        return  recomendadorDePuntos.recomendarPuntosDeDonacion(puntoInicial, radio, horaBuscada, diasBuscados);
    }
}