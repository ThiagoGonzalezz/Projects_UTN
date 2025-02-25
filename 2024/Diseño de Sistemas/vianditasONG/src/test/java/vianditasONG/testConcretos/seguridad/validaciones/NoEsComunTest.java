package vianditasONG.testConcretos.seguridad.validaciones;

import vianditasONG.modelos.servicios.seguridad.validaciones.NoEsComun;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NoEsComunTest {

    public NoEsComun validadorNoEsComun = NoEsComun.builder().build();
    /*
    @Test
    @DisplayName("es Comun (Invalida)")
    public void invalida() {
        String password = "12345678";
        Assertions.assertFalse(validadorNoEsComun.cumpleValidacion(password));
    }

    @Test
    @DisplayName("no es Comun (Valida)")
    public void valida() {
        String password = "cT!a1492rz!";
        Assertions.assertTrue(validadorNoEsComun.cumpleValidacion(password));
    }*/
}
