package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TelegramCantMin implements Telegram {
    @Getter
    private String chat_id;
    @Getter
    private String mensaje;

    public static TelegramCantMin of(String chat_id, String mensaje){
        return TelegramCantMin.builder()
                .chat_id(chat_id)
                .mensaje(mensaje)
                .build();
    }
}
