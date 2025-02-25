package vianditasONG.modelos.servicios.mensajeria.notificaciones;

import lombok.Builder;
import lombok.Getter;
import vianditasONG.config.Config;
import vianditasONG.modelos.entities.colaboraciones.ofrecerProductosOServicios.Oferta;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.PersonaJuridica;
import vianditasONG.modelos.entities.colaboradores.Puntuable;

@Builder
@Getter
public class NotificacionCanjeado implements Notificacion{

    private String titulo;
    private String mensaje;
    public static NotificacionCanjeado of(Puntuable canjeador, Oferta oferta) {
        String titulo = Config.getInstancia().obtenerDelConfig("tituloNotificacionCanjeado");

        String formatoMensaje = Config.getInstancia().obtenerDelConfig("formatoMensajeNotificacionCanjeado");
        String mensaje = String.format(formatoMensaje, oferta.getNombre(), canjeador.obtenerNombreCompleto());

        return NotificacionCanjeado.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
    }

}
