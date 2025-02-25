package models.entities.calculoDeCercanias;

import lombok.Builder;
import models.entities.datosGeograficos.PuntoGeografico;
import utils.ServiceLocator;

@Builder
public class VerificadorDeCercania implements IVerificadorDeCercania {
    @Builder.Default
    private ICalculadorDeDistancias calculadorDeDistancias = ServiceLocator.getService(ICalculadorDeDistancias.class);

    @Override
    public boolean estanCerca(PuntoGeografico puntoGeografico1, PuntoGeografico puntoGeografico2, float distanciaMaximaEnKm) {
        double distancia = calculadorDeDistancias.calcularDistanciaEnKm(puntoGeografico1, puntoGeografico2);

        System.out.println("Distancia calculada: " + distancia + " km");

        return distancia <= distanciaMaximaEnKm;
    }

}
