package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram;

import lombok.Builder;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams.Telegram;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;
//@Builder TODO: @Agus VER
public class BotTelegramAPI implements EnviadorDeTelegramAdapter {
    private static final String BASE_URL = "https://api.telegram.org";
    private Retrofit retrofit;
    private TelegramService service;
    private String botToken;

    public BotTelegramAPI(String botToken) {
        this.botToken = botToken;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(TelegramService.class);
    }

    @Override
    public void enviarTelegram(Telegram telegram) {
        Map<String, String> body = new HashMap<>();
        body.put("chat_id", telegram.getChat_id());
        body.put("text", telegram.getMensaje());

        Call<Void> call = service.enviarTelegram(botToken, body);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                System.out.println("Mensaje enviado con éxito a Telegram");
            } else {
                System.out.println("Error al enviar mensaje a Telegram: " + response.errorBody().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al enviar mensaje a Telegram: " + e.getMessage());
        }
    }
}