package vianditasONG.poc.incidentes;

import vianditasONG.config.Config;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.PuntoEstrategico;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.entities.incidentes.Alerta;
import vianditasONG.modelos.entities.incidentes.Incidente;
import vianditasONG.modelos.entities.incidentes.TipoDeAlerta;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.BotTelegramAPI;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.EnviadorDeTelegrams;
import vianditasONG.modelos.entities.tecnicos.CalculadorDeTecnicoCercano;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.config.ServiceLocator;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class reporteIncidenteATecnicoPOC {
    public static void main(String[] args) throws IOException {

        Contacto contacto = Contacto.builder()
                .tipoDeContacto(TipoDeContacto.TELEGRAM)
                .contacto("6190209796")
                .build();

        BotTelegramAPI botTelegram = new BotTelegramAPI(Config.getInstancia().obtenerDelConfig("tokenBot"));

        EnviadorDeTelegrams enviadorDeTelegrams = new EnviadorDeTelegrams(botTelegram);

        NotificadorDeTelegram notificadorDeTelegram = new NotificadorDeTelegram(enviadorDeTelegrams);

        Tecnico tecnico = Tecnico.builder()
                .contacto(contacto)
                .medioDeAviso(notificadorDeTelegram)
                .build();

        CalculadorDeTecnicoCercano calculadorDeTecnico = mock(CalculadorDeTecnicoCercano.class);
        when(calculadorDeTecnico.obtenerTecnicoMasCercano(any(Heladera.class))).thenReturn(tecnico);

        Direccion direccion = mock(Direccion.class);
        when(direccion.direccionCompleta()).thenReturn("Av. Medrano 951, Ciudad Autónoma de Buenos Aires");

        PuntoEstrategico punto = mock(PuntoEstrategico.class);
        when(punto.getDireccion()).thenReturn(direccion);
        when(punto.getNombre()).thenReturn("HELADERA MEDRANO UTN");

        Heladera heladera = Heladera.builder().build();
        heladera.setPuntoEstrategico(punto);

        Incidente incidente = Alerta.of(TipoDeAlerta.DESCONEXION, heladera);

        heladera.registarIncidente(incidente);

    }
}
