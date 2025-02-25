package vianditasONG.poc.mensajeria.enviadorDeWpp;

import vianditasONG.modelos.servicios.mensajeria.notificaciones.Notificacion;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.whatsapps.WhatsappNotificacion;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.EnviadorDeWPP;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.EnviadorDeWppAdapter;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeWpps.TwilioAdapterAPI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WppSuscripcionPOC {

    //ACLARACION: Se debe iniciar el sandbox antes de hacer la prueba de concepto.
    public static void main(String[] args){

        EnviadorDeWppAdapter adapterTwilio = TwilioAdapterAPI.builder().build();

        EnviadorDeWPP enviadorDeWPP = EnviadorDeWPP.builder()
                .enviadorDeWppAdapter(adapterTwilio)
                .build();


        Notificacion notificacion = mock(Notificacion.class);
        when(notificacion.getTitulo()).thenReturn("Notificación de Desperfecto en la Heladera");
        when(notificacion.getMensaje()).thenReturn("La heladera sufrió un desperfecto y las viandas deben ser llevadas a otras heladeras a la brevedad para que no se echen a perder. Sugerencias de heladeras:\n  -Heladera A\n  -Heladera B\n  En caso de acudir a realizar alguna de estas tareas, debes registrar una distribución de viandas como una forma de contribución");


        WhatsappNotificacion wppTexto = WhatsappNotificacion.of(notificacion, "+5491138085884" );

        enviadorDeWPP.enviarWpp(wppTexto);
    }
}
