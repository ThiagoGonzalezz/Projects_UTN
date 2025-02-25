package vianditasONG.serviciosExternos.twilio;

import vianditasONG.config.Config;
import vianditasONG.serviciosExternos.twilio.entidades.SolicitudWpp;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


public class ServicioTwilio implements IServicioTwilio {
    private static ServicioTwilio instancia = null;
    private static final String username = Config.getInstancia().obtenerDelConfig("accountSid");
    private static final String password = Config.getInstancia().obtenerDelConfig("authToken");

    public static ServicioTwilio getInstancia() {
        if (instancia == null) {
            Twilio.init(username, password);
            instancia = new ServicioTwilio();
        }
        return instancia;
    }

    @Override
    public void enviarWpp(SolicitudWpp solicitudWpp){
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(solicitudWpp.to),
                        new com.twilio.type.PhoneNumber(solicitudWpp.from),
                        solicitudWpp.body)
                        .create();
    }
}

