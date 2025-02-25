package vianditasONG.dtos.outputs.formulario;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
public class PreguntaEditableOutputDTO {
    private long id;
    private String enunciado;
    private String modalidad;
    private String tipoPregunta;
    @Setter
    private String tipoRespuesta;
    @Builder.Default
    private List<OpcionOutputDTO> opciones = new ArrayList<>();
    private String tipoDinamismo;

    public void agregarOpcion(OpcionOutputDTO ... opciones){
        Collections.addAll(this.opciones, opciones);
    }
}
