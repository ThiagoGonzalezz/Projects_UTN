package vianditasONG.dtos.inputs.colaboradores;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContactoDTO {
    private String tipoContacto;
    private String detalleContacto;
}
