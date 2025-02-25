package verificadorCercania;

import models.entities.calculoDeCercanias.CalculadorDeDistancias;
import models.entities.calculoDeCercanias.ICalculadorDeDistancias;
import models.entities.calculoDeCercanias.VerificadorDeCercania;
import models.entities.datosGeograficos.PuntoGeografico;
import models.entities.puntosDeDonacion.PuntoDeDonacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.EncendidoSeguro;
import utils.ServiceLocator;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class verificadorDeCercaniaTest {
    VerificadorDeCercania verificadorDeCercania;
    PuntoDeDonacion puntoDeDonacion1;
    PuntoDeDonacion puntoDeDonacion2;
    PuntoDeDonacion puntoDeDonacion3;
    PuntoGeografico puntoGeo;

    @BeforeEach
    public void inicializar(){
        CalculadorDeDistancias calculadorDeDistancias = CalculadorDeDistancias.builder().build();

        ServiceLocator.registerService(ICalculadorDeDistancias.class, calculadorDeDistancias);

         this.verificadorDeCercania = VerificadorDeCercania.builder().build();

        List<DayOfWeek> diasAbierto1 = new ArrayList<>();
        List<DayOfWeek> diasAbierto2 = new ArrayList<>();
        List<DayOfWeek> diasAbierto3 = new ArrayList<>();

        diasAbierto1.addAll(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        diasAbierto1.addAll(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        diasAbierto3.addAll(List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));


        puntoDeDonacion1 = PuntoDeDonacion.builder().nombre("UTN Medrano").puntoGeografico(PuntoGeografico.builder().longitud(-58.420012658665414).latitud(-34.59824563867314).build()).diasAbierto(diasAbierto1).horaCierre(LocalTime.of(18,0)).horaApertura(LocalTime.of(10,0)).build();
        puntoDeDonacion2 = PuntoDeDonacion.builder().nombre("UTN Lugano").puntoGeografico(PuntoGeografico.builder().longitud(-58.46798633167874).latitud(-34.65938494882818).build()).diasAbierto(diasAbierto2).horaCierre(LocalTime.of(12,0)).horaApertura(LocalTime.of(8,0)).build();
        puntoDeDonacion3 = PuntoDeDonacion.builder().nombre("UTN Pacheco").puntoGeografico(PuntoGeografico.builder().longitud(-58.62424024703083).latitud(-34.455130586426726).build()).diasAbierto(diasAbierto3).horaCierre(LocalTime.of(23,59)).horaApertura(LocalTime.of(0,0)).build();

        puntoGeo = PuntoGeografico.builder().longitud(-58.420012658665414).latitud(-34.59824563867314).build();
    }

    @Test()
    @DisplayName("retorna falso cuando la distancia supera la indicada")
    public void refutarCercania(){
        Assertions.assertNotEquals(true, verificadorDeCercania.estanCerca(puntoDeDonacion2.getPuntoGeografico(), puntoGeo, 1));
    }

    @Test()
    @DisplayName("retorna verdadero cuando la distancia supera la indicada")
    public void confirmarCercania(){
        Assertions.assertEquals(true, verificadorDeCercania.estanCerca(puntoDeDonacion2.getPuntoGeografico(), puntoGeo, 10));
    }

}
