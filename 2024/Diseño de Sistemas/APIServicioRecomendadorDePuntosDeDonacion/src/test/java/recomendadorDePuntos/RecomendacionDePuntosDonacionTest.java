package recomendadorDePuntos;

import models.entities.calculoDeCercanias.CalculadorDeDistancias;
import models.entities.calculoDeCercanias.ICalculadorDeDistancias;
import models.entities.calculoDeCercanias.IVerificadorDeCercania;
import models.entities.calculoDeCercanias.VerificadorDeCercania;
import models.entities.datosGeograficos.PuntoGeografico;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import models.entities.recomendador.RecomendadorDePuntos;
import models.entities.verificadorDias.IVerificadorDiasAbierto;
import models.entities.verificadorDias.VerificadorDiasAbierto;
import models.entities.verificadorHorarios.IVerificadorDeHorarios;
import models.entities.verificadorHorarios.VerificadorDeHorarios;
import models.repositories.imp.RepositorioPuntosDonacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ServiceLocator;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RecomendacionDePuntosDonacionTest {

    private RecomendadorDePuntos recomendadorDePuntos;
    private PuntoGeografico puntoGeo;
    private List<DayOfWeek> diasBuscados = new ArrayList<>();
    private LocalTime horarioBuscado;

    @BeforeEach
    public void inicializar(){
        RepositorioPuntosDonacion repositorioPuntosDonacion = RepositorioPuntosDonacion.builder().build();

        CalculadorDeDistancias calculadorDeDistancias = CalculadorDeDistancias.builder().build();

        ServiceLocator.registerService(ICalculadorDeDistancias.class, calculadorDeDistancias);

        VerificadorDeCercania verificadorDeCercania = VerificadorDeCercania.builder().build();

        VerificadorDeHorarios verificadorDeHorarios = VerificadorDeHorarios.builder().build();

        VerificadorDiasAbierto verificadorDiasAbierto = VerificadorDiasAbierto.builder().build();

        ServiceLocator.registerService(IVerificadorDeHorarios.class, verificadorDeHorarios);
        ServiceLocator.registerService(IVerificadorDiasAbierto.class, verificadorDiasAbierto);
        ServiceLocator.registerService(IVerificadorDeCercania.class, verificadorDeCercania);

        recomendadorDePuntos = RecomendadorDePuntos.builder().build();

        List<DayOfWeek> diasAbierto1 = new ArrayList<>();
        List<DayOfWeek> diasAbierto2 = new ArrayList<>();
        List<DayOfWeek> diasAbierto3 = new ArrayList<>();

        diasAbierto1.addAll(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        diasAbierto1.addAll(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        diasAbierto3.addAll(List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));


        PuntoDeDonacion puntoDeDonacion1 = PuntoDeDonacion.builder().nombre("UTN Medrano").puntoGeografico(PuntoGeografico.builder().longitud(-58.420012658665).latitud(-34.598245638673).build()).diasAbierto(diasAbierto1).horaCierre(LocalTime.of(18,0)).horaApertura(LocalTime.of(10,0)).build();
        PuntoDeDonacion puntoDeDonacion2 = PuntoDeDonacion.builder().nombre("UTN Lugano").puntoGeografico(PuntoGeografico.builder().longitud(-58.468040149725326).latitud(-34.65960688494879).build()).diasAbierto(diasAbierto2).horaCierre(LocalTime.of(12,0)).horaApertura(LocalTime.of(8,0)).build();
        PuntoDeDonacion puntoDeDonacion3 = PuntoDeDonacion.builder().nombre("UTN Pacheco").puntoGeografico(PuntoGeografico.builder().longitud(-58.62426796660136).latitud(-34.455219136675986).build()).diasAbierto(diasAbierto3).horaCierre(LocalTime.of(23,59)).horaApertura(LocalTime.of(0,0)).build();

        recomendadorDePuntos.agregarPuntosDeDonacion(puntoDeDonacion1, puntoDeDonacion2, puntoDeDonacion3);

        puntoGeo = PuntoGeografico.builder().latitud(-34.598245638673).longitud(-58.420012658665414).build();
        diasBuscados.add(DayOfWeek.SATURDAY);
        horarioBuscado = LocalTime.of(23,00,00);

    }

    @Test
    @DisplayName("Recomienda los puntos cercanos en el radio indicado")
    public void recomendarPorRadio(){

        Assertions.assertEquals(2, recomendadorDePuntos.recomendarPuntosDeDonacion(PuntoGeografico.builder().longitud(-58.420012658665).latitud(-34.598245638673).build(), 10).size());
    }

    @Test
    @DisplayName("Recomienda los puntos cercanos y en el horario indicado")
    public void recomendarPorRadioYHorario(){

        Assertions.assertEquals(2, recomendadorDePuntos.recomendarPuntosDeDonacion(PuntoGeografico.builder().longitud(-58.46732181710551).latitud(-34.66291992043316).build(), 1000, LocalTime.of(9,00)).size());    }

    @Test
    @DisplayName("Recomienda los puntos cercanos en los dias indicados")
    public void recomendarPorRadioYDia(){

        Assertions.assertEquals(2, recomendadorDePuntos.recomendarPuntosDeDonacion(PuntoGeografico.builder().longitud(-58.46732181710551).latitud(-34.66291992043316).build(), 1000000000).size(), List.of(DayOfWeek.SATURDAY, DayOfWeek.MONDAY).size());    }

    @Test
    @DisplayName("Recomienda los puntos cercanos en el horario y dias indicados")
    public void recomendarPorRadioHorarioYDia(){

        Assertions.assertEquals(1, recomendadorDePuntos.recomendarPuntosDeDonacion(PuntoGeografico.builder().longitud(-58.46732181710551).latitud(-34.66291992043316).build(), 1000000, LocalTime.of(23,00), List.of(DayOfWeek.SATURDAY)).size());    }
}
