package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TelegramDesperfecto implements Telegram {
    private String chat_id;
    private String mensaje;

    public static TelegramDesperfecto of(String chat_id, String mensaje){
        return TelegramDesperfecto.builder()
                .chat_id(chat_id)
                .mensaje(mensaje)
                .build();
    }
}
