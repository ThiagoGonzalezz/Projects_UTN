package vianditasONG.poc.serviciosExternos;

import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.serviciosExternos.recomendadorDePuntos.ServicioRecomendadorDePuntos;
import vianditasONG.serviciosExternos.recomendadorDePuntos.entidades.ListadoDePuntosEstrategicos;
import vianditasONG.serviciosExternos.recomendadorDePuntos.entidades.PuntoMolde;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class RecomendadorDePuntosTest {

    @Test
    public void recepcionListadoDePuntosEstrategicos() throws IOException {
        //punto y radio para la prueba
        PuntoGeografico puntoGeografico = PuntoGeografico.builder()
                .longitud(-58.4207)
                .latitud(-34.5978)
                .build();
        int radio = 3500;

        ServicioRecomendadorDePuntos servicio = ServicioRecomendadorDePuntos.getInstancia();

        ListadoDePuntosEstrategicos resultado = servicio.listadoDePuntosEstrategicos(puntoGeografico, radio);

        List<PuntoMolde> puntosEstrategicos = resultado.getPuntosEstrategicos();
        PuntoMolde primerPunto = puntosEstrategicos.get(0);
        PuntoMolde segundoPunto = puntosEstrategicos.get(1);

        Assertions.assertEquals("-33.6037", String.valueOf(primerPunto.getLatitud()));
        Assertions.assertEquals("-57.3816", String.valueOf(primerPunto.getLongitud()));

        Assertions.assertEquals("-35.2457", String.valueOf(segundoPunto.getLatitud()));
        Assertions.assertEquals("-59.1463", String.valueOf(segundoPunto.getLongitud()));
    }

}