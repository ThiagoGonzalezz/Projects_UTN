package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.colaboradores.personasJuridicas.RubroPersonaJuridicaDTO;
import vianditasONG.dtos.outputs.heladeras.HeladeraEnMapaDTO;
import vianditasONG.modelos.entities.colaboradores.infoColaboradores.RubroPersonaJuridica;
import vianditasONG.modelos.entities.heladeras.Heladera;

@Builder
public class ConverterRubrosPersonaJuridicaDTO {
    public RubroPersonaJuridicaDTO convertToDTO(RubroPersonaJuridica rubro){
        return RubroPersonaJuridicaDTO.builder()
                .id(rubro.getId())
                .descripcion(rubro.getDescripcion())
                .build();
    }
}
