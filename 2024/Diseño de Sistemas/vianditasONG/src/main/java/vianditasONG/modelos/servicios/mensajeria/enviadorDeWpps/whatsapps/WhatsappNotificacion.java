package vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.whatsapps;

import vianditasONG.config.Config;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WhatsappNotificacion implements Whatsapp{
    private String numEmisor;
    private String numReceptor;
    private String mensaje;


    public static WhatsappNotificacion of(Notificacion notificacion, String numReceptor){
        String formatoMensaje = Config.getInstancia().obtenerDelConfig("formatoWppNotificacion");
        String mensajeWpp = String.format(formatoMensaje, notificacion.getTitulo().toUpperCase(), notificacion.getMensaje());


        String numEmisor = Config.getInstancia().obtenerDelConfig("numEmisorWpp");

        return WhatsappNotificacion.builder()
                .numEmisor(numEmisor)
                .numReceptor(numReceptor)
                .mensaje(mensajeWpp)
                .build();
    }
}
