package vianditasONG.utils.verificadorDeCercania;

import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import lombok.Setter;
@Builder
@Setter
public class VerificadorDeCercania {
    @Builder.Default
    private float distanciaMaximaEnKms = Float.parseFloat((Config.getInstancia().obtenerDelConfig("parametroDeCercania")));
    @Builder.Default
    private CalculadorDeDistancias calculadorDeDistancias = ServiceLocator.getService(CalculadorDeDistancias.class);


    public boolean estanCerca(PuntoGeografico puntoGeografico1, PuntoGeografico puntoGeografico2) {
        double distancia = calculadorDeDistancias.calcularDistancia(puntoGeografico1, puntoGeografico2);
        return distancia <= this.distanciaMaximaEnKms;
    }

    public boolean estanCerca(PuntoGeografico puntoGeografico1, PuntoGeografico puntoGeografico2, float distanciaMaxima) {
        double distancia = calculadorDeDistancias.calcularDistancia(puntoGeografico1, puntoGeografico2);
        return distancia <= distanciaMaxima;
    }

}
