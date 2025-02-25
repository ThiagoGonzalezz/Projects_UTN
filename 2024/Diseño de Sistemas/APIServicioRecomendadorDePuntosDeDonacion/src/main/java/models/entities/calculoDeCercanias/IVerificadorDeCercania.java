package models.entities.calculoDeCercanias;

import models.entities.datosGeograficos.PuntoGeografico;

public interface IVerificadorDeCercania {
     boolean estanCerca(PuntoGeografico puntoGeografico1, PuntoGeografico puntoGeografico2, float distanciaMaximaEnKm);
}
