package vianditasONG.poc.mensajeria.enviadorDeMails;

import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails.MailCredencial;
import vianditasONG.utils.creadorDeCredenciales.Credencial;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.EnviadorDeMails;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.SendGridAdapterAPI;

import java.io.IOException;

public class MailCredencialPOC {
    public static void main(String[] args) throws IOException {


        SendGridAdapterAPI enviadorDeMailsAdapter = SendGridAdapterAPI.builder()
                .build();

        EnviadorDeMails enviadorDeMails = EnviadorDeMails.builder()
                .adapterEnviadorDeMails(enviadorDeMailsAdapter)
                .build();

        String receptor = "thiago23gonzalez@gmail.com";

        Humano colaborador = Humano.builder().build();
        colaborador.setNombre("Luca Cesari");

        Credencial credencial = Credencial.of("da39a3ee5e6b4b0d3255bfef95601890afd80709", receptor, colaborador);

        enviadorDeMails.enviarMail(MailCredencial.of(credencial));
    }
}

