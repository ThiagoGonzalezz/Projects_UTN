package utils;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.datosGeograficos.PuntoGeografico;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import models.repositories.IRepositorioPuntosDonacion;
import models.repositories.imp.RepositorioPuntosDonacion;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CargaDePuntosBD implements WithSimplePersistenceUnit {

    public static void main(String[] args) {

        RepositorioPuntosDonacion repositorioPuntosDonacion = RepositorioPuntosDonacion.builder().build();

        CargaDePuntosBD mainExample = new CargaDePuntosBD();
        mainExample.guardarPuntosDonaciones(repositorioPuntosDonacion);

    }

    private void guardarPuntosDonaciones(IRepositorioPuntosDonacion repositorioPuntosDonacion) {
        List<DayOfWeek> diasAbierto1 = new ArrayList<>();
        List<DayOfWeek> diasAbierto2 = new ArrayList<>();
        List<DayOfWeek> diasAbierto3 = new ArrayList<>();

        diasAbierto1.addAll(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        diasAbierto1.addAll(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        diasAbierto3.addAll(List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));


        PuntoDeDonacion puntoDeDonacion1 = PuntoDeDonacion.builder().nombre("UTN Medrano").puntoGeografico(PuntoGeografico.builder().longitud(-58.420012658665414).latitud(-34.59824563867314).build()).diasAbierto(diasAbierto1).horaCierre(LocalTime.of(18,0)).horaApertura(LocalTime.of(10,0)).build();
        PuntoDeDonacion puntoDeDonacion2 = PuntoDeDonacion.builder().nombre("UTN Lugano").puntoGeografico(PuntoGeografico.builder().longitud(-58.46798633167874).latitud(-34.65938494882818).build()).diasAbierto(diasAbierto2).horaCierre(LocalTime.of(12,0)).horaApertura(LocalTime.of(8,0)).build();
        PuntoDeDonacion puntoDeDonacion3 = PuntoDeDonacion.builder().nombre("UTN Pacheco").puntoGeografico(PuntoGeografico.builder().longitud(-58.62424024703083).latitud(-34.455130586426726).build()).diasAbierto(diasAbierto3).horaCierre(LocalTime.of(23,59)).horaApertura(LocalTime.of(0,0)).build();


        withTransaction(() -> {
            repositorioPuntosDonacion.guardar(puntoDeDonacion1);
            repositorioPuntosDonacion.guardar(puntoDeDonacion2);
            repositorioPuntosDonacion.guardar(puntoDeDonacion3);
        });
    }



}
