package vianditasONG.dtos.inputs;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FallaTecnicaInputDTO {
    private Long heladeraId;
    private String descripcionFalla;
    private Long colaboradorId;
}
