package vianditasONG.dtos.inputs.colaboraciones;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DonacionDeViandaInputDTO {
   private Long idHumano;
   private List<ViandaDTO> viandas;
   private Integer idHeladeraSeleccionada;
}
