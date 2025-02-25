package vianditasONG.modelos.entities.heladeras.suscripciones;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vianditasONG.config.Config;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;

@Getter
@Builder
public class NotificacionSuscripcionExitosa implements Notificacion {

    private String titulo;
    @Setter
    private String mensaje;

    public static NotificacionSuscripcionExitosa of(Heladera heladera, TipoSuscripcion tipoSuscripcion, String valor) {
        String titulo = Config.getInstancia().obtenerDelConfig("tituloNotificacionSuscripcionExitosa");

        String formatoMensaje = Config.getInstancia().obtenerDelConfig("mensajeNotificacionSuscripcionExitosa"); //TODO: @Agus mejorar mensaje
        String mensaje = String.format(formatoMensaje, obtenerDescripcionSuscripcion(tipoSuscripcion, heladera, valor));

        return NotificacionSuscripcionExitosa.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }

    public static String obtenerDescripcionSuscripcion(TipoSuscripcion tipoSuscripcion, Heladera heladera, String valor) {
        return switch (tipoSuscripcion) {
            case CANTVIANDASMIN -> String.format("queden %s viandas disponibles en la %s",
                    valor, heladera.getNombre());
            case CANTVIANDASMAX -> String.format("falten %s viandas para que la %s esté llena",
                    valor, heladera.getNombre());
            case DESPERFECTOS -> String.format("la %s sufra un desperfecto", heladera.getNombre());
            default -> "ocurra un evento desconocido.";
        };
    }
}
