// NotificacionCantViandasMax.java
package vianditasONG.modelos.entities.heladeras.suscripciones;

import vianditasONG.config.Config;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class NotificacionCantViandasMax implements Notificacion {
    private String titulo;
    @Setter
    private String mensaje;

    public static NotificacionCantViandasMax of(Heladera heladera, int cantidad) {
        String titulo = Config.getInstancia().obtenerDelConfig("tituloNotificacionHeladeraLlenaCantMaxdeViandas");


        String formatoMensaje = Config.getInstancia().obtenerDelConfig("mensajeNotificacionHeladeraLlenaCantMaxdeViandas");
        String mensaje = String.format(formatoMensaje, heladera.getId(), cantidad);

        return NotificacionCantViandasMax.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }
}

