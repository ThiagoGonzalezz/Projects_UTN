package vianditasONG.modelos.servicios.seguridad.validaciones;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ResultadoValidacion {
    @Setter
    private boolean valido;
    @Getter @Setter
    private String mensajeError;

    public ResultadoValidacion(boolean valido) {
        this.valido = valido;
    }

    public boolean esValido() {
        return valido;
    }
}