package vianditasONG.modelos.servicios.mensajeria.notificaciones;

import lombok.Builder;
import lombok.Getter;
import vianditasONG.config.Config;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;

@Getter
@Builder
public class NotificacionRechazo implements Notificacion {
    private String titulo;
    private String mensaje;
    public static NotificacionRechazo of(PersonaJuridica pj, String motivo){
        String titulo = Config.getInstancia().obtenerDelConfig("tituloNotificacionRechazada");

        String formatoMensaje = Config.getInstancia().obtenerDelConfig("formatoNotificacionRechazada");
        String mensaje = String.format(formatoMensaje, pj.getRazonSocial(), motivo);

        return NotificacionRechazo.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }

    public static NotificacionRechazo of(Humano humano, String motivo){
        String titulo = Config.getInstancia().obtenerDelConfig("tituloNotificacionRechazada");

        String formatoMensaje = Config.getInstancia().obtenerDelConfig("formatoNotificacionRechazada");
        String mensaje = String.format(formatoMensaje, humano.getNombre(), motivo);

        return NotificacionRechazo.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }
}
