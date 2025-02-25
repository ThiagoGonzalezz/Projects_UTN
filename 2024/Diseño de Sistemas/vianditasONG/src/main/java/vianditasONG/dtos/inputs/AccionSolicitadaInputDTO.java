package vianditasONG.dtos.inputs;

import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.TipoDeAccion;
import lombok.Data;

import java.util.List;

@Data
public class AccionSolicitadaInputDTO {

    private TipoDeAccion tipoDeAccion;
    //private LocalDateTime fechaYHoraDeSolicitud; se deduce en el controller
    //private LocalDateTime fechaDeCaducidadDeSolicitud; se deduce en el controller
    private String codigoTarjetaColaborador;
    private List<Integer> idHeladerasSolicitadas;

}
