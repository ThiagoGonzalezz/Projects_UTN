package vianditasONG.dtos.inputs.formularios;

import lombok.Builder;
import lombok.Getter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class PreguntaInputDTO {
    private String id;
    private String enunciado;
    private String modalidad;
    private String tipoPregunta;
    private String tipoRespuesta;
    @Builder.Default
    private List<OpcionEditInputDTO> opciones = new ArrayList<>();
}
