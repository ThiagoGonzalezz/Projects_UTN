package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.outputs.colaboraciones.OfertaOutputDto;
import vianditasONG.dtos.outputs.colaboraciones.RubroOfertaOutputDTO;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.RubroOferta;

@Builder
public class ConverterRubrosOfertasDto {
    public RubroOfertaOutputDTO convertToDTO(RubroOferta rubroOferta){
        return RubroOfertaOutputDTO.builder()
                .id(Math.toIntExact(rubroOferta.getId()))
                .descripcion(rubroOferta.getDescripcion())
                .build();
    }

}
