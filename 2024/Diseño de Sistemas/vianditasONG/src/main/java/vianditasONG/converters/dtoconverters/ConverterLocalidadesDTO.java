package vianditasONG.converters.dtoconverters;


import lombok.Builder;
import vianditasONG.dtos.outputs.LocalidadOutputDTO;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;

@Builder
public class ConverterLocalidadesDTO {
    public LocalidadOutputDTO convertToDTO(Localidad localidad){
        return LocalidadOutputDTO.builder()
                .id(localidad.getId())
                .nombre(localidad.getNombre())
                .build();
    }

}
