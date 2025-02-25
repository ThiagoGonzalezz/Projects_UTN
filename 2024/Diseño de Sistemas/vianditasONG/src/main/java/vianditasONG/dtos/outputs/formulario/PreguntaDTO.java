package vianditasONG.dtos.outputs.formulario;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PreguntaDTO {

    private String id;
    private String enunciado;
    private String requiredValue;
    private String placeholder;
    private String tipoPregunta;
    private String tipoSelect;
    private String tipoEntrada;
    private List<OpcionOutputDTO> opciones;
}
