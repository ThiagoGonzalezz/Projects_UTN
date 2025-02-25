package vianditasONG.modelos.servicios.conversorDirecAPunto;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import lombok.Setter;

import java.io.IOException;
@Setter
@Builder
public class ConversorDirecAPunto {
    @Builder.Default
    private ConversorDirecAPuntoAdapter adapterConversorDirecAPunto = ServiceLocator.getService(ConversorDirecAPuntoAdapter.class);

    public PuntoGeografico obtenerPuntoGeografico(Direccion direccion) throws IOException {
        return this.adapterConversorDirecAPunto.obtenerPuntoGeografico(direccion);
    }

    public Direccion obtenerDireccionDesdePuntoGeografico(PuntoGeografico puntoGeografico) throws IOException {
        return this.adapterConversorDirecAPunto.obtenerDireccionDesdePuntoGeografico(puntoGeografico);
    }

}
