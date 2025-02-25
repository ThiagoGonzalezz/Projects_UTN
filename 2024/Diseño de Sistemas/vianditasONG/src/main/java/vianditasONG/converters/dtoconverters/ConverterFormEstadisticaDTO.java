package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.formulario.FormularioEstadisticaDTO;
import vianditasONG.modelos.entities.formulario.Formulario;
import vianditasONG.modelos.entities.formulario.Pregunta;

import java.util.stream.Collectors;

@Builder
public class ConverterFormEstadisticaDTO {
    public FormularioEstadisticaDTO convertToDTO(Formulario formulario) {
        return FormularioEstadisticaDTO.builder()
                .nombre(formulario.getNombre())
                .id(formulario.getId())
                .pregTotales(formulario.getPreguntas().size())
                .pregOpcioTotales(formulario.getPreguntas().stream().filter(p -> !p.getEsEsencial()).toList().size())
                .pregObliTotales(formulario.getPreguntas().stream().filter(Pregunta::getEsEsencial).toList().size())
                .tipoForm(formulario.getEsEstablecido()? "Establecido" : "No Establecido")
                .build();
    }
}
