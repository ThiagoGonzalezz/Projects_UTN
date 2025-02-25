package vianditasONG.modelos.servicios.mensajeria.notificaciones;

import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.EnviadorDeMails;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails.MailNotificacion;
import lombok.Builder;

import java.io.IOException;

@Builder
public class NotificadorDeEmail implements MedioDeAviso {
    @Builder.Default
    private EnviadorDeMails enviadorDeMails = ServiceLocator.getService(EnviadorDeMails.class);

    public NotificadorDeEmail(EnviadorDeMails enviadorDeMails) {
        this.enviadorDeMails = enviadorDeMails;
    }

    @Override
    public void notificar(Contacto contacto, Notificacion notificacion) throws IOException {
        MailNotificacion mailNotificacion = MailNotificacion.of(notificacion, contacto.getContacto());
        enviadorDeMails.enviarMail(mailNotificacion);
    }
}


