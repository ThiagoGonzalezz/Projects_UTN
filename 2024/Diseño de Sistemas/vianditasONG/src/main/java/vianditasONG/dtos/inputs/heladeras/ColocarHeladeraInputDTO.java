package vianditasONG.dtos.inputs.heladeras;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ColocarHeladeraInputDTO {
    private Long personaJuridicaId;
    private Long modeloHeladeraId;
    private String nombre;
    private String calle;
    private String localidadId;
    private String altura;
}

