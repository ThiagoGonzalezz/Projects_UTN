package vianditasONG.modelos.servicios.conversorDirecAPunto;

import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.serviciosExternos.openCage.OpenCageServicio;
import vianditasONG.serviciosExternos.openCage.ServicioOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Geometry;
import vianditasONG.serviciosExternos.openCage.entidades.RespuestaOpenCage;
import vianditasONG.serviciosExternos.openCage.entidades.Resultado;

import java.io.IOException;
import java.util.List;

@Builder
public class OpenCageAdapterAPI implements ConversorDirecAPuntoAdapter{

    @Override
    public PuntoGeografico obtenerPuntoGeografico(Direccion direccion) throws IOException {
        ServicioOpenCage servicioOpenCage = ServicioOpenCage.getInstancia();
        servicioOpenCage.puntoGeografico(direccion);
        RespuestaOpenCage respuestaOpenCage = servicioOpenCage.puntoGeografico(direccion);

        List<Resultado> resultados = respuestaOpenCage.getResults();

        Geometry geometry = resultados.get(0).getGeometry();

        return PuntoGeografico.builder().
                latitud(Double.parseDouble(geometry.getLat())).
                longitud(Double.parseDouble(geometry.getLng())).
                build();
    }

    public Direccion obtenerDireccionDesdePuntoGeografico(PuntoGeografico puntoGeografico) throws IOException {
        ServicioOpenCage servicioOpenCage = ServicioOpenCage.getInstancia();
        RespuestaOpenCage respuesta = servicioOpenCage.obtenerDireccionDesdePunto(puntoGeografico);

        if (respuesta != null && !respuesta.getResults().isEmpty()) {
            Resultado resultado = respuesta.getResults().get(0);

            String direccionCompleta = resultado.getFormatted();


            return Direccion.builder()
                    .calle(extractCalle(direccionCompleta))
                    .altura(extractAltura(direccionCompleta))
                    .build();
        }

        return null;
    }


    private String extractCalle(String direccionCompleta) {
        // Aquí puedes implementar la lógica para extraer la calle de la dirección completa
        String[] partes = direccionCompleta.split(", ");
        return partes.length > 1 ? partes[1] : ""; // Retorna la parte que correspondería a la calle
    }

    private String extractAltura(String direccionCompleta) {
        // Aquí puedes implementar la lógica para extraer la altura de la dirección completa
        String[] partes = direccionCompleta.split(" ");
        return partes.length > 0 ? partes[0] : ""; // Retorna la primera parte que correspondería a la altura
    }


}
