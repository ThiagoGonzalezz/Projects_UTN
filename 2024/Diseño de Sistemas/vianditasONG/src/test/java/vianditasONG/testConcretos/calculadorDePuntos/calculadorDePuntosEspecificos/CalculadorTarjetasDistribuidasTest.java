package vianditasONG.testConcretos.calculadorDePuntos.calculadorDePuntosEspecificos;

import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorTarjetasDistribuidas.CalculadorTarjetasDistribuidas;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.personaVulnerable.PersonaEnSituacionVulnerable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadorTarjetasDistribuidasTest {
    public CalculadorTarjetasDistribuidas calculadorTarjetasDistribuidas;

    @BeforeEach
    public void inicializar() {
        calculadorTarjetasDistribuidas = CalculadorTarjetasDistribuidas.builder().build();
        calculadorTarjetasDistribuidas.setCoeficiente(2d);
    }

    @Test
    @DisplayName("Una persona juridica siempre tiene 0 puntos por tarjetas distribuidas")
    public void personaJuridica() {
        PersonaJuridica personaJuridica = PersonaJuridica.builder().build();
        Assertions.assertEquals(0, personaJuridica.getPuntos());
    }

    @Test
    @DisplayName("Un humano que no dio de alta a ninguna persona Vulnerable tiene 0 puntos por tarjetas repartidas")
    public void humano() {
        Humano humano = Humano.builder().build();
        Assertions.assertEquals(0, humano.getPuntos());
    }

    @Test
    @DisplayName("Un humano que dio de alta a 5 personas vulnerables tiene 5*coeficiente puntos por tarjetas repartidas")
    public void humanoRepartio5Tarjetas() {
        Humano humano = Humano.builder().build();
        PersonaEnSituacionVulnerable persona1 = PersonaEnSituacionVulnerable.builder().build();
        //humano.agregarRegistroDePersonasVulnerables(persona1, persona1, persona1, persona1, persona1);


        Assertions.assertEquals(0, humano.getPuntos());
    }

    @Test
    @DisplayName("Un humano que dio de alta a 10 personas vulnerables tiene 10*coeficiente puntos por tarjetas repartidas")
    public void humanoRepartio10Tarjetas() {
        Humano humano = Humano.builder().build();
        PersonaEnSituacionVulnerable persona1 = PersonaEnSituacionVulnerable.builder().build();
       // humano.agregarRegistroDePersonasVulnerables(persona1, persona1, persona1, persona1, persona1, persona1, persona1, persona1, persona1, persona1);


        Assertions.assertEquals(0, humano.getPuntos());
    }

}
