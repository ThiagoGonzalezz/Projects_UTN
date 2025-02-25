package vianditasONG.converters.dbconverters;

import vianditasONG.modelos.servicios.mensajeria.notificaciones.MedioDeAviso;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeEmail;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeWhatsapp;
import vianditasONG.config.ServiceLocator;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)

public class MedioDeAvisoConverter implements AttributeConverter<MedioDeAviso, String> {
    @Override
    public String convertToDatabaseColumn(MedioDeAviso imedioDeAviso) {
        if(imedioDeAviso == null){
            return null;
        }

        String descripcionMedioDeAviso = null;

        if(imedioDeAviso instanceof NotificadorDeEmail){
            descripcionMedioDeAviso = "EMAIL";
        }
        else if(imedioDeAviso instanceof NotificadorDeTelegram){
            descripcionMedioDeAviso = "TELEGRAM";
        }
        else if(imedioDeAviso instanceof NotificadorDeWhatsapp){
            descripcionMedioDeAviso = "WPP";
        }

        return descripcionMedioDeAviso;
    }

    @Override
    public MedioDeAviso convertToEntityAttribute(String descripcionMedioDeAviso) {
        if(descripcionMedioDeAviso == null){
            return null;
        }

        MedioDeAviso medioDeAviso = null;

        switch (descripcionMedioDeAviso){
            case "EMAIL": medioDeAviso = ServiceLocator.getService(NotificadorDeEmail.class);
            break;
            case "WPP": medioDeAviso = ServiceLocator.getService(NotificadorDeWhatsapp.class);
            break;
            case "TELEGRAM": medioDeAviso = ServiceLocator.getService(NotificadorDeTelegram.class);
            break;
        }

        return medioDeAviso;
    }
}
