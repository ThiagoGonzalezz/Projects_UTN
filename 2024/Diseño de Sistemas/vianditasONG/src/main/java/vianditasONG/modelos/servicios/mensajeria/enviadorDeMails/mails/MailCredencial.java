package vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails;

import vianditasONG.config.Config;
import vianditasONG.utils.creadorDeCredenciales.Credencial;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MailCredencial implements Mail{
    private String asunto;
    private String emisor;
    private String receptor;
    private String mensaje;

    public static MailCredencial of(Credencial credencial){
        String mensajeBase = Config.getInstancia().obtenerDelConfig("mensajeBaseCredencial");
        String mensaje = String.format(mensajeBase, credencial.getNombre(), credencial.getId());

        return MailCredencial.builder()
                .asunto(Config.getInstancia().obtenerDelConfig("asuntoMailCredencial"))
                .emisor(Config.getInstancia().obtenerDelConfig("mailEmisor"))
                .receptor(credencial.getMail())
                .mensaje(mensaje)
                .build();
    }
}
