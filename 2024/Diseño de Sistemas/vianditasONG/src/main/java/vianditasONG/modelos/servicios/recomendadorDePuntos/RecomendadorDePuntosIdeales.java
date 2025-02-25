package vianditasONG.modelos.servicios.recomendadorDePuntos;

import vianditasONG.modelos.entities.datosGenerales.AreaDeBusqueda;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import lombok.Setter;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;

import java.io.IOException;
import java.util.List;

@Setter
public class RecomendadorDePuntosIdeales {
    private PuntosRecomendadosAdapter adapterPuntosRecomendados;

    public List<PuntoEstrategico> recomendarPuntos(AreaDeBusqueda areaDeBusqueda) throws IOException {
        return this.adapterPuntosRecomendados.recomendarPuntos(areaDeBusqueda);
    }

}
