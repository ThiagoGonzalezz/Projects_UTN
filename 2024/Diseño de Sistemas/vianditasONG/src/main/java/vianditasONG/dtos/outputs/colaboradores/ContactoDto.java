package vianditasONG.dtos.outputs.colaboradores;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContactoDto {
    private String tipoContacto;
    private String detalleContacto;
}
