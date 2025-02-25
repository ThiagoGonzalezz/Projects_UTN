package vianditasONG.testConcretos.conversorDirecAPunto;

import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPunto;
import vianditasONG.modelos.servicios.conversorDirecAPunto.ConversorDirecAPuntoAdapter;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConversorDirecAPuntoTest {

    Localidad localidad = Localidad.builder().nombre("Villa Lugano").provincia(Provincia.BUENOS_AIRES).build();
    Direccion direccion = Direccion.builder().altura("2300").calle("Mozart").localidad(localidad).build();
    Humano humano = Humano.builder().direccion(direccion).build();
    PuntoGeografico puntoGeografico = PuntoGeografico.builder().latitud(-34.659546).longitud(-58.46799).build();

    ConversorDirecAPunto conversorDirecAPunto = ConversorDirecAPunto.builder().build();
    @Test
            public void SolicitudPuntoGeografico() throws IOException {

            ConversorDirecAPuntoAdapter conversorDirecAPuntoAdapterMockeado = mock(ConversorDirecAPuntoAdapter.class);

            when(conversorDirecAPuntoAdapterMockeado.obtenerPuntoGeografico(direccion)).thenReturn(puntoGeografico);
            conversorDirecAPunto.setAdapterConversorDirecAPunto(conversorDirecAPuntoAdapterMockeado);

            humano.setConversorDirecAPunto(conversorDirecAPunto);
            humano.setearPuntoGeografico();
            //PuntoGeografico puntoGeograficoResultado = conversorDirecAPunto.obtenerPuntoGeografico(direccion);

        Assertions.assertEquals(-34.659546, humano.getPuntoGeografico().getLatitud());
        Assertions.assertEquals(-58.46799,  humano.getPuntoGeografico().getLongitud());
    }
}
