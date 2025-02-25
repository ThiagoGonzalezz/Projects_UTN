package vianditasONG.modelos.servicios.mensajeria.enviadorDeMails;

import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails.Mail;
import lombok.Builder;

import java.io.IOException;

@Builder
public class EnviadorDeMails {
    @Builder.Default
    private EnviadorDeMailsAdapter adapterEnviadorDeMails = ServiceLocator.getService(SendGridAdapterAPI.class);

    public void enviarMail(Mail mail) throws IOException {
        this.adapterEnviadorDeMails.enviarMail(mail);
    }

}
