package vianditasONG.serviciosExternos.sendGrid;

import vianditasONG.serviciosExternos.sendGrid.entidades.SolicitudMail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.io.IOException;

public interface SendGridServicio {

    @Headers({
            "Content-Type: application/json",
            "Accept: */*",
            "User-Agent: Java-SendGrid/1.0",
            "Authorization: Bearer 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"
    })
    @POST("v3/mail/send")
    Call<Void> enviarMail(@Body SolicitudMail solicitudMail) throws IOException;


}
