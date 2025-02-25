package vianditasONG.converters.dtoconverters;

import lombok.Builder;
import vianditasONG.dtos.inputs.colaboradores.ContactoDTO;
import vianditasONG.modelos.servicios.mensajeria.Contacto;

@Builder
public class ConverterContactosDTO {

    public ContactoDTO convertToDTO(Contacto contacto){
        return ContactoDTO.builder()
                .detalleContacto(contacto.getContacto())
                .tipoContacto(contacto.getTipoDeContacto().toString())
                .build();
    }
}
