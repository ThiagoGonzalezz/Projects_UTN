package vianditasONG.serviciosExternos.twilio.entidades;

import lombok.Builder;

import java.util.List;

@Builder
public class SolicitudWpp {
    public String to;
    public String from;
    public String body;
    public String mediaUrl;
}
