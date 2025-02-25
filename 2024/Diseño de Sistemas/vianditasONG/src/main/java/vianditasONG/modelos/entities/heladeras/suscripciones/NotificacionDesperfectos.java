package vianditasONG.modelos.entities.heladeras.suscripciones;

import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.heladeras.CalculadorDeHeladerasCercanas;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class NotificacionDesperfectos implements Notificacion {
    private String titulo;
    @Setter
    private String mensaje;

    public static NotificacionDesperfectos of(Heladera heladera) {
        String titulo = Config.getInstancia().obtenerDelConfig("tituloNotificacionDesperfecto");


        List<Heladera> heladerasCercanas = ServiceLocator.getService(CalculadorDeHeladerasCercanas.class).obtenerHeladerasCercanas(heladera);
        String idsHeladerasCercanas = heladerasCercanas.stream()
                .map(h -> h.getId().toString())
                .collect(Collectors.joining(", "));

        String formatoMensaje = Config.getInstancia().obtenerDelConfig("mensajeNotificacionDesperfecto");
        String mensaje = String.format(formatoMensaje, heladera.getId(), idsHeladerasCercanas);

        return NotificacionDesperfectos.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }
}

