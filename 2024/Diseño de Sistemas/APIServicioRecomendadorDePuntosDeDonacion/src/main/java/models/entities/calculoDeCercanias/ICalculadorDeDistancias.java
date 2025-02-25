package models.entities.calculoDeCercanias;

import models.entities.datosGeograficos.PuntoGeografico;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

public interface ICalculadorDeDistancias {
    double calcularDistanciaEnKm(PuntoGeografico puntoGeografico1, PuntoGeografico puntoGeografico2);
}
