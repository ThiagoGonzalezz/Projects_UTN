package vianditasONG.converters.dtoconverters;


import lombok.Builder;
import vianditasONG.dtos.outputs.formulario.FormularioRespondidoDTO;
import vianditasONG.dtos.outputs.formulario.RespuestaDTO;
import vianditasONG.modelos.entities.formulario.FormularioRespondido;
import vianditasONG.modelos.entities.formulario.Opcion;
import vianditasONG.modelos.entities.formulario.Respuesta;

import java.util.stream.Collectors;

@Builder
public class ConverterFormularioRespondidoDTO {

    public FormularioRespondidoDTO convertToDto(FormularioRespondido formRespondido, String nombreColab) {
        FormularioRespondidoDTO formularioRespondidoDTO = FormularioRespondidoDTO.builder()
                .nombreColaborador(nombreColab)
                .build();

        for (Respuesta respuesta : formRespondido.getListaRespuestas()) {
            formularioRespondidoDTO.agregarRespuesta(this.respuestaToDTO(respuesta));
        }

        return formularioRespondidoDTO;
    }

    private RespuestaDTO respuestaToDTO(Respuesta respuesta){
        return RespuestaDTO.builder()
                .enunciado(respuesta.getPreguntaAsociada().getEnunciado())
                .respuesta(this.generarRespuestaSegunTipo(respuesta))
                .build();
    }

    private String generarRespuestaSegunTipo(Respuesta respuesta){
        return switch (respuesta.getPreguntaAsociada().getTipo()){
            case PREGUNTA_A_DESARROLLAR -> respuesta.getRespuestaLibre();
            case SINGLE_CHOICE -> respuesta.getOpcionesSeleccionadas().get(0).getDescripcion();
            case MULTIPLE_CHOICE -> respuesta.getOpcionesSeleccionadas().stream().map(Opcion::getDescripcion).collect(Collectors.joining(", "));
        };

    }
}
