package vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps;

import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.whatsapps.Whatsapp;
import vianditasONG.serviciosExternos.twilio.ServicioTwilio;
import vianditasONG.serviciosExternos.twilio.entidades.SolicitudWpp;
import lombok.Builder;

@Builder
public class TwilioAdapterAPI implements EnviadorDeWppAdapter {
    @Override
    public void enviarWpp(Whatsapp wpp){
        SolicitudWpp solicitudWpp = SolicitudWpp.
                builder()
                .body(wpp.getMensaje())
                .from("whatsapp:" + wpp.getNumEmisor())
                .to("whatsapp:" + wpp.getNumReceptor())
                .build();

        ServicioTwilio.getInstancia().enviarWpp(solicitudWpp);
    }
}
