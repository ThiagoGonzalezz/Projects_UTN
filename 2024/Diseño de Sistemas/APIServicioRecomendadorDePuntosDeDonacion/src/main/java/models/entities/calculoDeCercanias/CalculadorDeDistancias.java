package models.entities.calculoDeCercanias;

import lombok.Builder;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import models.entities.datosGeograficos.PuntoGeografico;

@Builder
public class CalculadorDeDistancias implements ICalculadorDeDistancias {

    @Override
    public double calcularDistanciaEnKm(PuntoGeografico puntoGeografico1, PuntoGeografico puntoGeografico2) {
        Geodesic geod = Geodesic.WGS84;
        GeodesicData g = geod.Inverse(
                puntoGeografico1.getLatitud(),
                puntoGeografico1.getLongitud(),
                puntoGeografico2.getLatitud(),
                puntoGeografico2.getLongitud()
        );
        return g.s12 / 1000.0; // Convertir a kms
    }
}
