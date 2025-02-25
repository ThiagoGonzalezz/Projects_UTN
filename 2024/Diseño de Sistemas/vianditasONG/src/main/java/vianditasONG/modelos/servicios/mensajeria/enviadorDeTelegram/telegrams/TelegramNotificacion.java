package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams;

import vianditasONG.config.Config;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TelegramNotificacion implements Telegram {
    private String chat_id;
    private String mensaje;

    public static TelegramNotificacion of(Notificacion notificacion, String chatId) {
        String formatoMensaje = Config.getInstancia().obtenerDelConfig("formatoTelegramNotificacion");
        String mensajeTelegram = String.format(formatoMensaje, notificacion.getTitulo().toUpperCase(), notificacion.getMensaje());

        return TelegramNotificacion.builder()
                .chat_id(chatId)
                .mensaje(mensajeTelegram)
                .build();
    }
}
