package vianditasONG.testConcretos.seguridad;

import vianditasONG.modelos.servicios.seguridad.RegistroUsuarios;
import vianditasONG.modelos.servicios.seguridad.ValidadorCreacionPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegistroUsuariosTest {
    public ValidadorCreacionPassword validadorCreacionPassword = ValidadorCreacionPassword.builder().build();
    public RegistroUsuarios registroUsuarios = new RegistroUsuarios();

    @Test
    public void elNombreDeUsuarioEstaDisponible() {
        Assertions.assertFalse(registroUsuarios.usuarioExistente("bauti"));
    }

    //@Test
    public void elNombreDeUsuarioYaExiste() {
        Assertions.assertTrue(registroUsuarios.usuarioExistente("pedrito"));
    }
}
