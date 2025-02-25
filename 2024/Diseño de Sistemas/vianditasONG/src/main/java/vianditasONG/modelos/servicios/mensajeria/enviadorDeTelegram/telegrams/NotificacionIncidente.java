package vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams;

import vianditasONG.config.Config;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import vianditasONG.modelos.entities.incidentes.Incidente;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Builder
public class NotificacionIncidente implements Notificacion {
    private String titulo;
    @Setter
    private String mensaje;

    public static NotificacionIncidente of(Incidente incidente) {
        String formatoTitulo = Config.getInstancia().obtenerDelConfig("formatoTituloNotificacionIncidente");
        String titulo = String.format(formatoTitulo, incidente.nombreHeladeraAsociada());

        String formatoMensaje = Config.getInstancia().obtenerDelConfig("formatoMensajeNotificacionIncidente");
        String mensaje = String.format(formatoMensaje, incidente.direccionAsociada(), incidente.getDescripcion());

        return NotificacionIncidente.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }
}
