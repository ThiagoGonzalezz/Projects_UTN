package vianditasONG.dtos.inputs.colaboraciones;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class DistribucionDeViandaInputDTO {
    private Long idHumano;
    private Integer idHeladeraDestino;
    private Integer idHeladeraOrigen;
    private Integer cantViandas;
    private String motivoDistribucion;
}
