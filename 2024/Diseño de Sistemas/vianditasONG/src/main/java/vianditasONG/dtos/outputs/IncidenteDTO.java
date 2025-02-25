package vianditasONG.dtos.outputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IncidenteDTO {
    private String descripcion;
    private String fotoResolucion;
    private String fechaResolucion;
    private String tecnicoResolutor;
    private String fechaIncidente;
}
