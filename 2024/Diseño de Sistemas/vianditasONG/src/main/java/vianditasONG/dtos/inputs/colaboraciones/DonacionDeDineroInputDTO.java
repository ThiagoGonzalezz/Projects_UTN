package vianditasONG.dtos.inputs.colaboraciones;

import lombok.Builder;
import vianditasONG.modelos.entities.colaboraciones.donacionDeDinero.FrecuenciaDonacion;
import lombok.Getter;


@Builder
@Getter
public class DonacionDeDineroInputDTO {
    private Long idUsuario;
    private Double monto;
    private FrecuenciaDonacion frecuenciaDonacion;
}
