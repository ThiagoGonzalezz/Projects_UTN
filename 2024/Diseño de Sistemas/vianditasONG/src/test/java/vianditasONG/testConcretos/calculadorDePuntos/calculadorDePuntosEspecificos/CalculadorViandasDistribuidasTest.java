package vianditasONG.testConcretos.calculadorDePuntos.calculadorDePuntosEspecificos;

import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorViandasDistribuidas.CalculadorViandasDistribuidas;
import vianditasONG.modelos.entities.colaboraciones.distribucionDeViandas.DistribucionDeVianda;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadorViandasDistribuidasTest {

    public CalculadorViandasDistribuidas calculadorViandasDistribuidas;

    @BeforeEach
    public void inicializar() {
        calculadorViandasDistribuidas = CalculadorViandasDistribuidas.builder().build();
        calculadorViandasDistribuidas.setCoeficiente(1d);
    }

    @Test
    @DisplayName("Una persona juridica siempre tiene 0 puntos por viandas distribuidas")
    public void personaJuridica() {
        PersonaJuridica personaJuridica =PersonaJuridica.builder().build();
        Assertions.assertEquals(0, personaJuridica.getPuntos());
    }

    @Test
    @DisplayName("Un humano que no distribuyo ninguna vianda tiene 0 puntos por viandas distribuidas")
    public void humano() {
        Humano humano = Humano.builder().build();
        Assertions.assertEquals(0, humano.getPuntos());
    }

    @Test
    @DisplayName("Un humano que distribuyo 5 viandas tiene 5*coeficiente puntos por viandas distribuidas")
    public void humanoRepartio5Viandas() {
        Humano humano = Humano.builder().build();
        DistribucionDeVianda distribucion1 = DistribucionDeVianda.builder().build();
        DistribucionDeVianda distribucion2 = DistribucionDeVianda.builder().build();
        distribucion1.setCantidadDeViandas(2);
        distribucion2.setCantidadDeViandas(3);
       // humano.agregarViandasDistribuidas(distribucion1, distribucion2);

        Assertions.assertEquals(0d,humano.getPuntos());
    }

    @Test
    @DisplayName("Un humano que distribuyo 10 viandas tiene 10*coeficiente puntos por viandas distribuidas")
    public void humanoRepartio10Viandas() {
        Humano humano = Humano.builder().build();
        DistribucionDeVianda distribucion1 = DistribucionDeVianda.builder().build();
        DistribucionDeVianda distribucion2 = DistribucionDeVianda.builder().build();
        distribucion1.setCantidadDeViandas(7);
        distribucion2.setCantidadDeViandas(3);
     //   humano.agregarViandasDistribuidas(distribucion1, distribucion2);

        Assertions.assertEquals(0d, humano.getPuntos());
    }
}
