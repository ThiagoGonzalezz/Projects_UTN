package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.formulario.OpcionOutputDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaDTO;
import vianditasONG.modelos.entities.formulario.Opcion;
import vianditasONG.modelos.entities.formulario.Pregunta;
import vianditasONG.modelos.entities.formulario.TipoPregunta;

import java.util.ArrayList;
import java.util.List;

@Builder
public class ConverterPreguntasDTO {

    public PreguntaDTO convertToPreguntaDTO(Pregunta pregunta) {

        List<OpcionOutputDTO> opcionesDTO = new ArrayList<>();

        for(Opcion opcion: pregunta.getOpciones()){
            OpcionOutputDTO opcionOutputDTO = OpcionOutputDTO.builder()
                    .id(String.valueOf(opcion.getId()))
                    .descripcion(opcion.getDescripcion())
                    .build();

            opcionesDTO.add(opcionOutputDTO);
        }

        PreguntaDTO preguntaDTO = PreguntaDTO.builder()
                    .tipoPregunta(String.valueOf(pregunta.getTipo()))
                    .id(String.valueOf(pregunta.getId()))
                    .enunciado(pregunta.getEnunciado() + (pregunta.getEsEsencial()? " *" : ""))
                    .placeholder(pregunta.getPlaceholder())
                    .requiredValue(pregunta.getEsEsencial()? "REQUIRED" : null)
                    .tipoSelect(pregunta.getTipo() == TipoPregunta.MULTIPLE_CHOICE? "MULTIPLE" : null)
                    .tipoEntrada(String.valueOf(pregunta.getTipoEntrada()))
                    .opciones(opcionesDTO)
                    .build();

        return  preguntaDTO;

    }

}
