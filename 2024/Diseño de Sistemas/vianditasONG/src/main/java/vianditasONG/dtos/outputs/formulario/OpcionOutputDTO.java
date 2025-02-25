package vianditasONG.dtos.outputs.formulario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OpcionOutputDTO {
    private String id;
    private String descripcion;
}
