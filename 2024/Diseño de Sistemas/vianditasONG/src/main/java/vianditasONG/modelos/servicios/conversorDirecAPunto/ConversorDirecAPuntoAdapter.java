package vianditasONG.modelos.servicios.conversorDirecAPunto;

import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;

import java.io.IOException;

public interface ConversorDirecAPuntoAdapter {

    public PuntoGeografico obtenerPuntoGeografico(Direccion direccion) throws IOException;

    Direccion obtenerDireccionDesdePuntoGeografico(PuntoGeografico puntoGeografico) throws IOException;
}
