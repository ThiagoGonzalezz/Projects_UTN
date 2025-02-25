package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.inputs.formularios.OpcionEditInputDTO;
import vianditasONG.dtos.inputs.formularios.PreguntaInputDTO;
import vianditasONG.dtos.outputs.formulario.OpcionOutputDTO;
import vianditasONG.dtos.outputs.formulario.PreguntaEditableOutputDTO;
import vianditasONG.modelos.entities.formulario.Opcion;
import vianditasONG.modelos.entities.formulario.Pregunta;
import vianditasONG.modelos.entities.formulario.TipoEntradaPregunta;
import vianditasONG.modelos.entities.formulario.TipoPregunta;

import java.util.Objects;

@Builder
public class ConverterPregEditDTO {
    public PreguntaEditableOutputDTO convertToDTO(Pregunta pregunta) {
        PreguntaEditableOutputDTO preguDTO = PreguntaEditableOutputDTO.builder()
                .id(pregunta.getId())
                .enunciado(pregunta.getEnunciado())
                .modalidad(pregunta.getEsEsencial()? "Obligatoria" : "Opcional")
                .tipoDinamismo(pregunta.getEsEstatica()? "ESTATICA" : "DINAMICA")
                .tipoPregunta(pregunta.getTipo().aString())
                .build();

        if(pregunta.getTipo() == TipoPregunta.PREGUNTA_A_DESARROLLAR){
            preguDTO.setTipoRespuesta(pregunta.getTipoEntrada().toString());
        }else{
            for(Opcion opcion : pregunta.getOpciones()){
                preguDTO.agregarOpcion(this.convertirOpcionDTO(opcion));
            }
        }

        return preguDTO;
    }

    public OpcionOutputDTO convertirOpcionDTO(Opcion opcion){
        return OpcionOutputDTO.builder()
                .id(opcion.getId().toString())
                .descripcion(opcion.getDescripcion())
                .build();
    }

    public Pregunta convertToEntity(PreguntaInputDTO preguDTO){
        Pregunta pregunta = Pregunta.builder()
                .enunciado(preguDTO.getEnunciado())
                .esEsencial(Objects.equals(preguDTO.getModalidad(), "Obligatoria"))
                .tipo(TipoPregunta.aEntity(preguDTO.getTipoPregunta()))
                .esEstatica(false)
                .build();

        if(pregunta.getTipo() == TipoPregunta.PREGUNTA_A_DESARROLLAR){
            pregunta.setTipoEntrada(TipoEntradaPregunta.valueOf(preguDTO.getTipoRespuesta()));
        }else{
            for(OpcionEditInputDTO opcionDTO : preguDTO.getOpciones()){
                pregunta.agregarOpciones(this.convertToOpcionEntity(opcionDTO));
            }
        }

        pregunta.setActivo(true);

        return pregunta;
    }

    public Opcion convertToOpcionEntity(OpcionEditInputDTO opcionDTO) {
        Opcion opcion =  Opcion.builder()
                .descripcion(opcionDTO.getEnunciado())
                .build();

        opcion.setActivo(true);

        return opcion;
    }
}
