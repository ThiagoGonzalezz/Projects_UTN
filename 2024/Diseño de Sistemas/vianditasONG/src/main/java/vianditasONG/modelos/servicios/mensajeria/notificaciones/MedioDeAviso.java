package vianditasONG.modelos.servicios.mensajeria.notificaciones;

import vianditasONG.modelos.servicios.mensajeria.Contacto;

import java.io.IOException;

public interface MedioDeAviso {
    void notificar(Contacto contacto, Notificacion notificacion) throws IOException;
}
