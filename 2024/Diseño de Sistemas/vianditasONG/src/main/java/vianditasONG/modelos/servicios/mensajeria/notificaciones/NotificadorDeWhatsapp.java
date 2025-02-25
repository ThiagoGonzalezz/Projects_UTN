package vianditasONG.modelos.servicios.mensajeria.notificaciones;

import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.EnviadorDeWPP;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.whatsapps.WhatsappNotificacion;
import lombok.Builder;

@Builder
public class NotificadorDeWhatsapp implements MedioDeAviso {
    @Builder.Default
    private EnviadorDeWPP enviadorDeWhatsapps = ServiceLocator.getService(EnviadorDeWPP.class);

    public NotificadorDeWhatsapp(EnviadorDeWPP enviadorDeWhatsapps) {
        this.enviadorDeWhatsapps = enviadorDeWhatsapps;
    }

    @Override
    public void notificar(Contacto contacto, Notificacion notificacion) {

        WhatsappNotificacion whatsappNotificacion = WhatsappNotificacion.of(notificacion, contacto.getContacto());
        enviadorDeWhatsapps.enviarWpp(whatsappNotificacion);
    }
}
