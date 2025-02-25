package vianditasONG.dtos.inputs.colaboraciones;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OfertaInputDTO {
    private Long rubroId;
    private String nombre;
    private Double puntosNecesarios;
    private String imagen;
    private Long ofertanteId;
}
