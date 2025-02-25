package vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails;

import vianditasONG.config.Config;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MailNotificacion implements Mail{
    private String asunto;
    private String emisor;
    private String receptor;
    private String mensaje;

    public static MailNotificacion of(Notificacion notificacion, String mailReceptor){
        String formatoMensaje = Config.getInstancia().obtenerDelConfig("mensajeBaseMailNotificacion");
        String mensajeEnHtml = notificacion.getMensaje().replace("\n", "<br>");
        String mensajeMail = String.format(formatoMensaje, notificacion.getTitulo(), mensajeEnHtml);

        return MailNotificacion.builder()
                .asunto(notificacion.getTitulo())
                .emisor(Config.getInstancia().obtenerDelConfig("mailEmisor"))
                .receptor(mailReceptor)
                .mensaje(mensajeMail)
                .build();
    }
}
