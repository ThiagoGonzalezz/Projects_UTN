package vianditasONG.modelos.servicios.mensajeria.enviadorDeMails;

import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails.Mail;

import java.io.IOException;

public interface EnviadorDeMailsAdapter {

    public void enviarMail(Mail mail) throws IOException;

}
