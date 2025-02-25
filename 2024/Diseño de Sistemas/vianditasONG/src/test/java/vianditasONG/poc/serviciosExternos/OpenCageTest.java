package vianditasONG.poc.serviciosExternos;

import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.datosGenerales.direccion.Localidad;
import vianditasONG.modelos.entities.datosGenerales.direccion.Provincia;
import vianditasONG.serviciosExternos.openCage.ServicioOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Geometry;
import vianditasONG.serviciosExternos.openCage.entidades.RespuestaOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Resultado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


public class OpenCageTest {
    @Test
    public void recepcionDePuntoGeografico() throws IOException {
        //direccion para la prueba
        Localidad localidad = Localidad.builder().provincia(Provincia.BUENOS_AIRES).nombre("Almagro").build();
        Direccion direccion = Direccion.builder().calle("Avenida Medrano").altura("951").localidad(localidad).build();

        ServicioOpenCage servicioOpenCage = ServicioOpenCage.getInstancia();

        RespuestaOpenCage respuestaOpenCage = servicioOpenCage.puntoGeografico(direccion);

        List<Resultado> resultados = respuestaOpenCage.getResults();

        Geometry geometry = resultados.get(0).getGeometry();

        Assertions.assertEquals("-34.5986333", geometry.getLat());
        Assertions.assertEquals("-58.4199428", geometry.getLng());
    }
}
