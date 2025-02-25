package vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps;

import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.whatsapps.Whatsapp;
import lombok.Builder;

@Builder
public class EnviadorDeWPP{
    @Builder.Default
    private EnviadorDeWppAdapter enviadorDeWppAdapter = ServiceLocator.getService(TwilioAdapterAPI.class);

    public void enviarWpp(Whatsapp whatsapp){
        this.enviadorDeWppAdapter.enviarWpp(whatsapp);
    }
}
