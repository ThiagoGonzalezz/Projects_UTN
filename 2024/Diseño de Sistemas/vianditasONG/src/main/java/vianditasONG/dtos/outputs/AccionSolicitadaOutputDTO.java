package vianditasONG.dtos.outputs;

import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.TipoDeAccion;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AccionSolicitadaOutputDTO {

    private Long id;
    private TipoDeAccion tipoDeAccion;
    private LocalDateTime fechaYHoraDeSolicitud;
    private LocalDateTime fechaDeCaducidadDeSolicitud;
    private TarjetaColaborador tarjetaColaborador;
    private List<Heladera> heladerasSolicitadas;


}
