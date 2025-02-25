package vianditasONG.testConcretos.seguridad;

public class ValidadorTest {
    /*
    private void mostrarErrores(ResultadoValidacion resultadoValidacion){

        for (String mensaje : resultadoValidacion.getMensajesError()) {
            System.out.println("- " + mensaje);
        }
    }
    public ValidadorCreacionPassword validadorCreacionPassword = ValidadorCreacionPassword.builder().build();
    public CumpleEstandaresNIST validadorCumpleEstandares = CumpleEstandaresNIST.builder().build();
    public NoEsComun validadorNoEsComun = NoEsComun.builder().build();

    @BeforeEach
    public void inicializar(){
        validadorCumpleEstandares.agregarEstandaresNist(
                EstandarDeLongitud.builder().build(),
                EstandarDeComplejidad.builder().build(),
                EstandarDeRotacion.builder().build());

        validadorCreacionPassword.agregarValidaciones(validadorNoEsComun, validadorCumpleEstandares);
    }
    @Test
    @DisplayName("LongitudCorta (Invalida)")
    public void invalidaLongitudCorta() {
        String contrasenia = "Y_!tf6";
        ResultadoValidacion resultadoValidacion = validadorCreacionPassword.validarCreacionContrasenia(contrasenia);
        mostrarErrores(resultadoValidacion);
        Assertions.assertFalse(resultadoValidacion.esValido());
    }

    @Test
    @DisplayName("LongitudLarga (Invalida)")
    public void invalidaLongitudLarga() {
        String contrasenia = "x@F3aG7qH9jK2mP5sU8vY0bX1nZ4cQ6wE5rT7yI9oL3pM6kNdfsgsdgsdgsdsdgs8ja";
        ResultadoValidacion resultadoValidacion = validadorCreacionPassword.validarCreacionContrasenia(contrasenia);
        mostrarErrores(resultadoValidacion);
        Assertions.assertFalse(resultadoValidacion.esValido());
    }

    @Test
    @DisplayName("Comun y no respeta estandares (Invalida)")
    public void invalidaComun() {
        String contrasenia = "experienced";
        ResultadoValidacion resultadoValidacion = validadorCreacionPassword.validarCreacionContrasenia(contrasenia);
        mostrarErrores(resultadoValidacion);
        Assertions.assertFalse(resultadoValidacion.esValido());
    }

    @Test
    @DisplayName("No tiene mayuscula (Invalida)")
    public void invalidaSinMayuscula() {
        String contrasenia = "se_aprueba_la_primer_entrega_10!";
        ResultadoValidacion resultadoValidacion = validadorCreacionPassword.validarCreacionContrasenia(contrasenia);
        mostrarErrores(resultadoValidacion);
        Assertions.assertFalse(resultadoValidacion.esValido());
    }

    @Test
    @DisplayName("No tiene minuscula (Invalida)")
    public void invalidaSinMinuscula() {
        String contrasenia = "SEAPRUEBALAPRIMERENTREGA10!";
        ResultadoValidacion resultadoValidacion = validadorCreacionPassword.validarCreacionContrasenia(contrasenia);
        mostrarErrores(resultadoValidacion);
        Assertions.assertFalse(resultadoValidacion.esValido());
    }

    @Test
    @DisplayName("No tiene digitos (Invalida)")
    public void innvalidaSinDigitos() {
        String contrasenia = "seApruebaLaPrimerEntrega!";
        ResultadoValidacion resultadoValidacion = validadorCreacionPassword.validarCreacionContrasenia(contrasenia);
        mostrarErrores(resultadoValidacion);
        Assertions.assertFalse(resultadoValidacion.esValido());
    }

    @Test
    @DisplayName("No tiene caracter especial (Invalida)")
    public void invalidaSinCaracterEspecial() {
        String contrasenia = "seApruebaLaPrimerEntrega10";
        ResultadoValidacion resultadoValidacion = validadorCreacionPassword.validarCreacionContrasenia(contrasenia);
        mostrarErrores(resultadoValidacion);
        Assertions.assertFalse(resultadoValidacion.esValido());
    }
    @Test
    @DisplayName("No comun, longitud correcta, cumple los estandares (Valida)")
    public void valida() {
        String contrasenia = "EstaDeberiaFuncionar!1234567";
        ResultadoValidacion resultadoValidacion = validadorCreacionPassword.validarCreacionContrasenia(contrasenia);
        mostrarErrores(resultadoValidacion);
        Assertions.assertTrue(resultadoValidacion.esValido());
    }
    */
}



