package vianditasONG.dtos.outputs;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemHistorialOutputDTO {

    private String imagen;
    private String tipo;
    private Integer puntos;
    private String descripcion;
    private String fechaYHora;
}
