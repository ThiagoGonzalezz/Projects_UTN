package vianditasONG.modelos.entities.datosGenerales;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vianditasONG.config.Config;

@Getter
@Setter
@Builder
public class AreaDeBusqueda {

    private PuntoGeografico puntoGeografico;
    @Builder.Default
    private Integer radioEnMetros= Integer.valueOf(Config.getInstancia().obtenerDelConfig("radioEnMetrosDefault"));

    public AreaDeBusqueda(PuntoGeografico puntoGeografico, Integer radioEnMetros) {
        this.puntoGeografico = puntoGeografico;
        this.radioEnMetros = radioEnMetros;
    }
}
