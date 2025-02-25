package vianditasONG.dtos.outputs.formulario;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RespuestaDTO {
    private String enunciado;
    private String respuesta;
}
