package vianditasONG.serviciosExternos.twilio;

import vianditasONG.serviciosExternos.twilio.entidades.SolicitudWpp;

public interface IServicioTwilio {

    void enviarWpp(SolicitudWpp solicitudWpp);
}

