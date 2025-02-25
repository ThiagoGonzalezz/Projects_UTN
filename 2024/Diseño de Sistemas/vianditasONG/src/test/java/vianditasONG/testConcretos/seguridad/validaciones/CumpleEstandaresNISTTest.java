package vianditasONG.testConcretos.seguridad.validaciones;

import vianditasONG.modelos.servicios.seguridad.estandares.EstandarDeComplejidad;
import vianditasONG.modelos.servicios.seguridad.estandares.EstandarDeLongitud;
import vianditasONG.modelos.servicios.seguridad.estandares.EstandarDeRotacion;
import vianditasONG.modelos.servicios.seguridad.validaciones.CumpleEstandaresNIST;
import org.junit.jupiter.api.*;
import vianditasONG.modelos.servicios.seguridad.validaciones.ResultadoValidacion;

public class CumpleEstandaresNISTTest {

        public CumpleEstandaresNIST validadorCumpleEstandares = CumpleEstandaresNIST.builder().build();
        @BeforeEach
        public void inicializar(){
            validadorCumpleEstandares.agregarEstandaresNist(
                    EstandarDeLongitud.builder().build(),
                    EstandarDeComplejidad.builder().build(),
                    EstandarDeRotacion.builder().build());
        }

        @Test
        @DisplayName("LongitudCorta (Invalida)")
        public void invalidaLongitudCorta() {
            String password = "Y_!tf6";
            ResultadoValidacion resultadoValidacion = validadorCumpleEstandares.cumpleValidacion(password);
            System.out.println(resultadoValidacion.getMensajeError());
            Assertions.assertFalse(resultadoValidacion.esValido());
        }

       /* @Test
        @DisplayName("LongitudLarga (Invalida)")
        public void invalidaLongitudLarga() {
            String password = "x@F3aG7qH9jK2mP5sU8vY0bX1nZ4cQ6wE5rT7yI9oL3pM6kNdfsgsdgsdgsdsdgs8ja";
            Assertions.assertFalse(validadorCumpleEstandares.cumpleValidacion(password));
        }

        @Test
        @DisplayName("No tiene Mayusculas (Invalida)")
        public void invalidaSinMayuscula() {
            String password = "21contra1!!";
            Assertions.assertFalse(validadorCumpleEstandares.cumpleValidacion(password));
        }

        @Test
        @DisplayName("No tiene Minuscula (Invalida)")
        public void invalidaSinMinuscula() {
            String password = "21CONTRA1!!";
            Assertions.assertFalse(validadorCumpleEstandares.cumpleValidacion(password));
        }

        @Test
        @DisplayName("No tiene Caracteres Especiales (Invalida)")
        public void invalidaSinCaracteresEspeciales() {
            String password = "21CONTRA1aaa";
            Assertions.assertFalse(validadorCumpleEstandares.cumpleValidacion(password));
        }

        @Test
        @DisplayName("No tiene Digitos (Invalida)")
        public void invalidaSinDigitos() {
            String password = "!!CONTRA!aaa";
            Assertions.assertFalse(validadorCumpleEstandares.cumpleValidacion(password));
        }

        @Test
        @DisplayName("Cumple los estandares (Valida)")
        public void valida() {
            String caracteresEspeciales = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ ";
            String password = "123457689fADSD@_qaswfawfa!";
            Assertions.assertTrue(validadorCumpleEstandares.cumpleValidacion(password));
        }
        */

}
