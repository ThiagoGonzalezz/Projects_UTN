package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram;

import lombok.Builder;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import vianditasONG.config.ServiceLocator;

@Builder
public class BotInitializer {

    public void iniciarBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TecnicoBot tecnicoBot = ServiceLocator.getService(TecnicoBot.class);
            botsApi.registerBot(tecnicoBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
