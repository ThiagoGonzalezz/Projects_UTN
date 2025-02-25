package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram;

import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams.Telegram;

public interface EnviadorDeTelegramAdapter {
    void enviarTelegram(Telegram telegram);
}