package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram;

import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams.Telegram;

@Builder
public class EnviadorDeTelegrams {
    @Builder.Default
    private EnviadorDeTelegramAdapter adapter = ServiceLocator.getService(BotTelegramAPI.class);

    public EnviadorDeTelegrams(EnviadorDeTelegramAdapter adapter) {
        this.adapter = adapter;
    }

    public void enviar(Telegram telegram) {
        adapter.enviarTelegram(telegram);
    }
}

