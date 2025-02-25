package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vianditasONG.modelos.entities.heladeras.sensores.VisitaHeladera;
import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.modelos.entities.tecnicos.Tecnico;

@Builder
@Getter
@Setter
public class ProcesoRegistro {
    private String estado;
    private Incidente incidente;
    private VisitaHeladera visita;
    private Tecnico tecnico;
}
