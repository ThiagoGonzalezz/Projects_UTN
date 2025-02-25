package vianditasONG.poc.mensajeria.enviadorDeMails;

import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.EnviadorDeMails;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.SendGridAdapterAPI;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails.MailNotificacion;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MailSuscripcionPOC {
    public static void main(String[] args) throws IOException {


        SendGridAdapterAPI enviadorDeMailsAdapter = SendGridAdapterAPI.builder()
                .build();

        EnviadorDeMails enviadorDeMails = EnviadorDeMails.builder()
                .adapterEnviadorDeMails(enviadorDeMailsAdapter)
                .build();

        String receptor = "thiago23gonzalez@gmail.com";

        Notificacion notificacion = mock(Notificacion.class);
        when(notificacion.getTitulo()).thenReturn("Notificación de Desperfecto en la Heladera");
        when(notificacion.getMensaje()).thenReturn("La heladera sufrió un desperfecto y las viandas deben ser llevadas a otras heladeras a la brevedad para que no se echen a perder. Sugerencias de heladeras:\n  -Heladera A\n  -Heladera B\n  En caso de acudir a realizar alguna de estas tareas, debes registrar una distribución de viandas como una forma de contribución");


        enviadorDeMails.enviarMail(MailNotificacion.of(notificacion, receptor));
    }
}
