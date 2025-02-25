package vianditasONG.utils.verificadorDeCercania;

import lombok.Builder;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
@Builder
public class CalculadorDeDistancias {

    public double calcularDistancia(PuntoGeografico puntoGeografico1, PuntoGeografico puntoGeografico2){
        Geodesic geod = Geodesic.WGS84;
        GeodesicData g = geod.Inverse(puntoGeografico1.getLatitud(),
                puntoGeografico1.getLongitud(),
                puntoGeografico2.getLatitud(),
                puntoGeografico2.getLongitud());
        return g.s12 / 1000.0; // Convertir a kms
    }
}
