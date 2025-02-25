package vianditasONG.dtos.outputs;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FallaTecnicaOutputDTO {
    private Long id;
    private String descripcion;
    private String fechaReporte;
    private String estado;
    private String foto;
}
