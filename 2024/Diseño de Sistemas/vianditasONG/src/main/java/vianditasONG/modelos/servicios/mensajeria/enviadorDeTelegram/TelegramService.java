package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram;

import retrofit2.Call;
import retrofit2.http.*;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.Map;

public interface TelegramService {
    @POST("/bot{bot_token}/sendMessage")
    Call<Void> enviarTelegram(@Path("bot_token") String botToken, @Body Map<String, String> body);

    @POST("/bot{botToken}/setWebhook")
    Call<Void> configurarWebhook(@Path("botToken") String botToken, @Body Map<String, String> body);

    @GET("/bot{botToken}/getUpdates")
    Call<Map<String, Object>> obtenerActualizaciones(@Path("botToken") String botToken, @Query("offset") int offset);


}




