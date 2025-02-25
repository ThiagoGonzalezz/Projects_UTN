package vianditasONG.modelos.servicios.mensajeria.notificaciones;

import lombok.Builder;
import lombok.Getter;
import vianditasONG.config.Config;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams.NotificacionIncidente;

@Builder
@Getter
public class NotificacionAceptado implements Notificacion {
    private String titulo;
    private String mensaje;
    public static NotificacionAceptado of(PersonaJuridica pj){
        String titulo = Config.getInstancia().obtenerDelConfig("tituloNotificacionAceptado");

        String formatoMensaje = Config.getInstancia().obtenerDelConfig("formatoMensajeNotificacionAceptadoPJ");
        String mensaje = String.format(formatoMensaje, pj.getRazonSocial());

        return NotificacionAceptado.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }

    public static NotificacionAceptado of(Humano humano, String numTarjeta){
        String titulo = Config.getInstancia().obtenerDelConfig("tituloNotificacionAceptado");

        String formatoMensaje = Config.getInstancia().obtenerDelConfig("formatoMensajeNotificacionAceptadoHumano");
        String mensaje = String.format(formatoMensaje, humano.getNombre(), numTarjeta);

        return NotificacionAceptado.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }
}
