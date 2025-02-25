package vianditasONG.testConcretos.utils.serviceLocator;

import vianditasONG.config.ServiceLocator;
import vianditasONG.serviciosExternos.openCage.ServicioOpenCage;
import vianditasONG.serviciosExternos.sendGrid.ServicioSendGrid;
import vianditasONG.serviciosExternos.twilio.ServicioTwilio;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceLocatorTest {
    @Test
    public void testWhatsAppService() {
        ServicioTwilio whatsappService = ServiceLocator.getService(ServicioTwilio.class);
        assertNotNull(whatsappService);
        assertTrue(whatsappService instanceof ServicioTwilio);
    }

    @Test
    public void testEmailService() {
        ServicioSendGrid emailService = ServiceLocator.getService(ServicioSendGrid.class);
        assertTrue(emailService instanceof ServicioSendGrid);
    }

    @Test
    public void testGeoLocalizadorService() {
        ServicioOpenCage geoService = ServiceLocator.getService(ServicioOpenCage.class);
        assertNotNull(geoService);
        assertTrue(geoService instanceof ServicioOpenCage);
    }
}
