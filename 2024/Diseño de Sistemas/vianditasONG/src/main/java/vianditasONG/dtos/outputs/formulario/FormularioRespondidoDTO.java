package vianditasONG.dtos.outputs.formulario;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
public class FormularioRespondidoDTO {
    private String nombreColaborador;
    @Builder.Default
    private List<RespuestaDTO> respuestas = new ArrayList<>();

    public void agregarRespuesta (RespuestaDTO ... respuestas) {
        Collections.addAll(this.respuestas, respuestas);
    }
}
