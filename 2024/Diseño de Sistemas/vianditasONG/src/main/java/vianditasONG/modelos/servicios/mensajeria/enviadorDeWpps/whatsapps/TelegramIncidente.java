package vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.whatsapps;

import vianditasONG.modelos.entities.incidentes.Incidente;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TelegramIncidente {
    private String emisor;
    private String receptor;
    private String mensaje;

    public static TelegramIncidente of(Incidente incidente){


        return TelegramIncidente.builder().build();
    }
}
