package vianditasONG.testConcretos.calculadorDePuntos.calculadorDePuntosEspecificos;

import vianditasONG.modelos.servicios.calculadoresDePuntos.calculadorHeladeras.CalculadorHeladeras;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadorHeladerasTest {
    public CalculadorHeladeras calculadorHeladeras;

    @BeforeEach
    public void inicializar(){
        calculadorHeladeras = CalculadorHeladeras.builder().build();
        calculadorHeladeras.setCoeficiente(5.0);
    }

    //@Test
    /*@DisplayName("un humano siempre tiene 0 puntos por heladeras")
    public void humano() {
        Humano humano = Humano.builder().build();
        Assertions.assertEquals(0, calculadorHeladeras.calcularPuntos(humano));
    }*/

    //@Test
    /*@DisplayName("Una persona juridica que no tiene heladeras a cargo tiene 0 puntos por heladeras")
    public void personaJuridicaSinHeladeras() {
        PersonaJuridica personaJuridica = PersonaJuridica.builder().build();
        Assertions.assertEquals(0, calculadorHeladeras.calcularPuntos(personaJuridica));
    }*/

    /*@Test
    @DisplayName("Una persona juridica que tiene heladeras a cargo, pero estan inactivas, tiene 0 puntos por heladeras")
    public void personaJuridicaConHeladerasInactivas() {
        PersonaJuridica personaJuridica = PersonaJuridica.builder().build();
        Heladera heladera = Heladera.builder().build();
        heladera.desactivarHeladera(EstadoHeladera.DESCONECTADA);
        heladera.setFechaDeRegistro(LocalDateTime.of(2024,10,23, 8, 25));
        HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera();
        hacerseCargoDeHeladera.setHeladera(heladera);
        personaJuridica.agregarHeladerasACargo(hacerseCargoDeHeladera, hacerseCargoDeHeladera);
        Assertions.assertEquals(0, Math.abs(calculadorHeladeras.calcularPuntos(personaJuridica)));
    }*///TODO: Arreglar

    /*@Test
    @DisplayName("Una persona juridica que tiene 2 heladeras activas a cargo hace 1 mes cada una, tiene 20 puntos por heladeras")
    public void personaJuridicaConHeladerasActivas() {

        PersonaJuridica personaJuridica = PersonaJuridica.builder().build();
        Heladera heladera1 = Heladera.builder().build();
        heladera1.activarHeladera();
        heladera1.setFechaUltimoCalculo(LocalDateTime.now().minusDays(30));
        Heladera heladera2 = Heladera.builder().build();
        heladera2.activarHeladera();
        heladera2.setFechaUltimoCalculo(LocalDateTime.now().minusDays(30));
        HacerseCargoDeHeladera cargo1 = new HacerseCargoDeHeladera();
        cargo1.setHeladera(heladera1);
        HacerseCargoDeHeladera cargo2 = new HacerseCargoDeHeladera();
        cargo2.setHeladera(heladera2);
        personaJuridica.agregarHeladerasACargo(cargo1, cargo2);
        
        Assertions.assertEquals(20, calculadorHeladeras.calcularPuntos(personaJuridica));
    }*///TODO: Arreglar
}
