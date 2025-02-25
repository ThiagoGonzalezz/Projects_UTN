package vianditasONG.modelos.servicios.mensajeria;

import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.MedioDeAviso;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeEmail;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeWhatsapp;

public enum TipoDeContacto {
    CORREO,
    WHATSAPP,
    TELEGRAM;

    public MedioDeAviso getMedioDeAviso() {
        return switch (this) {
            case CORREO -> ServiceLocator.getService(NotificadorDeEmail.class);
            case WHATSAPP -> ServiceLocator.getService(NotificadorDeWhatsapp.class);
            case TELEGRAM -> ServiceLocator.getService(NotificadorDeTelegram.class);
            default -> throw new IllegalArgumentException("Tipo de contacto no soportado: " + this);
        };
    }


}
