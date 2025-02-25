package vianditasONG.testConcretos.calculadorDePuntos.calculadorDePuntosEspecificos;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.DonacionDeDinero;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.FrecuenciaDonacion;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorPesosDonados.CalculadorPesosDonados;

import java.time.LocalDate;

public class CalculadorPesosDonadosTests {
    public CalculadorPesosDonados calculadorPesosDonados;

    @BeforeEach
    public void inicializar() {
        calculadorPesosDonados = CalculadorPesosDonados.builder().build();
        calculadorPesosDonados.setCoeficiente(0.5);
    }

    @Test
    @DisplayName("un humano sin donaciones de dinero tiene 0 puntos por donaciones de dinero")
    public void humanoSinDonacionesDeDinero() {
        Humano humano = Humano.builder().build();
        Assertions.assertEquals(0, humano.getPuntos());
    }

    @Test
    @DisplayName("Una persona juridica sin donaciones de dinero tiene 0 puntos por donaciones de dinero")
    public void personaJuridicaSinDonacionesDeDinero() {
        PersonaJuridica personaJuridica = PersonaJuridica.builder().build();
        Assertions.assertEquals(0, personaJuridica.getPuntos());
    }

    @Test
    @DisplayName("Una persona juridica que dono 3 veces automaticamente 200 pesos tiene 300 puntos")
    public void personaJuridicaCon3DonacionesAutomaticas() {
        PersonaJuridica personaJuridica =  PersonaJuridica.builder().build();
        DonacionDeDinero donacion = DonacionDeDinero.builder().
                monto(200d).
                fecha(LocalDate.now().minusDays(21)).
                frecuenciaDonacion(FrecuenciaDonacion.UNA_CADA_DOS_SEMANAS).
                build();
        personaJuridica.agregarDonacionesDeDinero(donacion, donacion, donacion);

        Assertions.assertEquals(100, calculadorPesosDonados.calcularPuntos(donacion.getMonto()));
    }

    @Test
    @DisplayName("Un humano que dono 3 veces manuales 100 pesos tiene 150 puntos")
    public void humanoCon3DonacionesManuales() {
        Humano humano = Humano.builder().build();
        DonacionDeDinero donacion = DonacionDeDinero.builder().frecuenciaDonacion(FrecuenciaDonacion.UNICA_VEZ).build();
        donacion.setMonto(100d);
        //humano.agregarDonacionesDeDinero(donacion, donacion, donacion);

        Assertions.assertEquals(150, 3*calculadorPesosDonados.calcularPuntos(donacion.getMonto()));
    }

    //@Test
    @DisplayName("Un humano que dono 1 vez manual 100 pesos y 2 veces automatico 150 pesos tiene 200 puntos")
    public void humanoCon1DonacionManualYDosAutomaticas() {
        Humano humano = Humano.builder().build();
        DonacionDeDinero donacionManual = DonacionDeDinero.builder().frecuenciaDonacion(FrecuenciaDonacion.UNICA_VEZ).build();
        donacionManual.setMonto(100d);
        DonacionDeDinero donacionAutomatica = DonacionDeDinero.builder().frecuenciaDonacion(FrecuenciaDonacion.UNA_CADA_DOS_SEMANAS).build();
        donacionAutomatica.setFecha(LocalDate.now().minusDays(11));
       // humano.agregarDonacionesDeDinero(donacionManual, donacionAutomatica, donacionAutomatica);
        donacionAutomatica.setMonto(150d);
        Assertions.assertEquals(200,  calculadorPesosDonados.calcularPuntos(donacionManual.getMonto())
                +2*calculadorPesosDonados.calcularPuntos(donacionAutomatica.getMonto()));
    }
}
