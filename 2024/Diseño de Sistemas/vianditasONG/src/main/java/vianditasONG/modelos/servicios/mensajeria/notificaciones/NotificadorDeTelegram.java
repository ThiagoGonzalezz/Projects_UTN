package vianditasONG.modelos.servicios.mensajeria.notificaciones;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.EnviadorDeTelegrams;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams.TelegramNotificacion;

@Builder
public class NotificadorDeTelegram implements MedioDeAviso {
    @Builder.Default
    private EnviadorDeTelegrams enviadorDeTelegrams = ServiceLocator.getService(EnviadorDeTelegrams.class);

    public NotificadorDeTelegram(EnviadorDeTelegrams enviadorDeTelegrams) {
        this.enviadorDeTelegrams = enviadorDeTelegrams;
    }

    @Override
    public void notificar(Contacto contacto, Notificacion notificacion) {
        TelegramNotificacion telegramNotificacion = TelegramNotificacion.of(notificacion, contacto.getContacto());
        enviadorDeTelegrams.enviar(telegramNotificacion);
    }
}
