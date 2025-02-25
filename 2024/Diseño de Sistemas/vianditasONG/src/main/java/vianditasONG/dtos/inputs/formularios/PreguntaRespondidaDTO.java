package vianditasONG.dtos.inputs.formularios;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PreguntaRespondidaDTO {
    private Long PreguntaAsociadaId;
    private String respuestaLibre;
    private List<OpcionInputDTO> opciones;
}
