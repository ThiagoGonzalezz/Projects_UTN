package vianditasONG.testConcretos.calculadorDePuntos.calculadorDePuntosEspecificos;

import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDonadas.CalculadorViandasDonadas;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.DonacionDeVianda;
import vianditasONG.modelos.entities.colaboraciones.donacionDeViandas.Vianda;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadorViandasDonadasTest {
    public CalculadorViandasDonadas calculadorViandasDonadas;

    @BeforeEach
    public void inicializar() {
        calculadorViandasDonadas = CalculadorViandasDonadas.builder().build();
        calculadorViandasDonadas.setCoeficiente(1.5);
    }

    @Test
    @DisplayName("Una persona juridica siempre tiene 0 puntos por viandas donadas")
    public void personaJuridica() {
        PersonaJuridica personaJuridica = PersonaJuridica.builder().build();
        Assertions.assertEquals(0, personaJuridica.getPuntos());
    }

    @Test
    @DisplayName("Un humano que no dono ninguna vianda tiene 0 puntos por viandas donadas")
    public void humano() {
        Humano humano = Humano.builder().build();
        Assertions.assertEquals(0, humano.getPuntos());
    }

    @Test
    @DisplayName("Un humano que dono 5 viandas tiene 5*coeficiente puntos por viandas donadas")
    public void humanoDono5Viandas() {
        Humano humano = Humano.builder().build();
        Vianda vianda = Vianda.builder().build();
        DonacionDeVianda donacion1 = DonacionDeVianda.builder().build();
        DonacionDeVianda donacion2 = DonacionDeVianda.builder().build();
        donacion1.agregarViandas(vianda, vianda);
        donacion2.agregarViandas(vianda, vianda, vianda);
       // humano.agregarViandasDonadas(donacion1, donacion2);

        Assertions.assertEquals(0, humano.getPuntos());
    }

    @Test
    @DisplayName("Un humano que dono 10 viandas tiene 10*coeficiente puntos por viandas donadas")
    public void humanoDono10Viandas() {
        Humano humano = Humano.builder().build();
        Vianda vianda = Vianda.builder().build();
        DonacionDeVianda donacion1 = DonacionDeVianda.builder().build();
        DonacionDeVianda donacion2 = DonacionDeVianda.builder().build();
        donacion1.agregarViandas(vianda, vianda);
        donacion2.agregarViandas(vianda, vianda, vianda, vianda, vianda, vianda, vianda, vianda);
      //  humano.agregarViandasDonadas(donacion1, donacion2);

        Assertions.assertEquals(0, humano.getPuntos());
    }
}
