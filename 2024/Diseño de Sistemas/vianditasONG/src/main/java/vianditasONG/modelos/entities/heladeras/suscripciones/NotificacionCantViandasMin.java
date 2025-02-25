package vianditasONG.modelos.entities.heladeras.suscripciones;

import vianditasONG.config.Config;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class NotificacionCantViandasMin implements Notificacion {
    private String titulo;
    @Setter
    private String mensaje;

    public static NotificacionCantViandasMin of(Heladera heladera, int cantidad) {
        String titulo = Config.getInstancia().obtenerDelConfig("tituloNotificacionHeladeraLlenaCantMindeViandas");

        String formatoMensaje = Config.getInstancia().obtenerDelConfig("mensajeNotificacionHeladeraLlenaCantMindeViandas");
        String mensaje = String.format(formatoMensaje, heladera.getId(), cantidad);

        return NotificacionCantViandasMin.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }
}

