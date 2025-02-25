package vianditasONG.dtos.inputs.colaboraciones;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuscripcionDTO {
    private Long idSuscripcion;
    private String tipoSuscripcion;
    private String heladeraId;
    private Integer cantViandasMax;
    private Integer cantViandasMin;
    private String contacto;

}
