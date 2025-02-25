package vianditasONG.modelos.servicios.mensajeria.enviadorDeMails;

import vianditasONG.config.Config;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails.Mail;
import vianditasONG.serviciosExternos.sendGrid.ServicioSendGrid;
import vianditasONG.serviciosExternos.sendGrid.entidades.*;
import lombok.Builder;

import java.io.IOException;
@Builder
public class SendGridAdapterAPI implements EnviadorDeMailsAdapter {

    public void enviarMail(Mail mail) throws IOException {
        //Se arma el molde de solicitud de mail
        From from = new From(mail.getEmisor());
        To tos = new To(mail.getReceptor());
        Personalization personalization = new Personalization();
        personalization.agregarTos(tos);
        Content content = new Content(
                         Config.getInstancia().obtenerDelConfig("tipoContenidoMail"),
                         mail.getMensaje());


        SolicitudMail solicitudDeMail = new SolicitudMail(from, mail.getAsunto());
        solicitudDeMail.agregarPersonalization(personalization);
        solicitudDeMail.agregarContent(content);

        //Se envia el mail
        ServicioSendGrid.getInstancia().enviarMail(solicitudDeMail);
    }
}
