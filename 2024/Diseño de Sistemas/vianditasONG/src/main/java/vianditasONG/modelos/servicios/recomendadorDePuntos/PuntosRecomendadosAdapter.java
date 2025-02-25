package vianditasONG.modelos.servicios.recomendadorDePuntos;

import vianditasONG.modelos.entities.datosGenerales.AreaDeBusqueda;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;

import java.io.IOException;
import java.util.List;

public interface PuntosRecomendadosAdapter {

    List<PuntoEstrategico> recomendarPuntos(AreaDeBusqueda areaDeBusqueda) throws IOException;

}
