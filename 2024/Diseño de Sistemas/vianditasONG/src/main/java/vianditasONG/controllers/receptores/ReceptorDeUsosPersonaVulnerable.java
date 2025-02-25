package vianditasONG.controllers.receptores;

import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.RegistroUsoTarjeta;
import vianditasONG.modelos.entities.personaVulnerable.tarjetas.TarjetaDePersonaVulnerable;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.ITarjetasDePersonasVulnerablesRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.RegistroUsoTarjetaRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasDePersonasVulnerablesRepositorio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Builder
public class ReceptorDeUsosPersonaVulnerable implements IMqttMessageListener, WithSimplePersistenceUnit {

    @Builder.Default
    private IHeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);
    @Builder.Default
    private ITarjetasDePersonasVulnerablesRepositorio tarjetasDePersonasVulnerablesRepositorio = ServiceLocator.getService(TarjetasDePersonasVulnerablesRepositorio.class);
    @Builder.Default
    private RegistroUsoTarjetaRepositorio registroUsoTarjetaRepositorio = ServiceLocator.getService(RegistroUsoTarjetaRepositorio.class);

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

        ReceptorDeUsosPersonaVulnerable.MoldeMensajeRespuesta mensajeRespuesta = this.castear(mqttMessage.toString());
        Optional<Heladera> heladeraOptional = heladerasRepositorio.buscar(Long.valueOf(mensajeRespuesta.idHeladera));
        if(heladeraOptional.isEmpty())
            return;
        Heladera heladera = heladeraOptional.get();

        Optional<TarjetaDePersonaVulnerable> tarjetaDePersonaVulnerableOptional = tarjetasDePersonasVulnerablesRepositorio.buscarPorCodigo(mensajeRespuesta.nroTarjeta);
        if(tarjetaDePersonaVulnerableOptional.isEmpty())
            return;
        TarjetaDePersonaVulnerable tarjetaDePersonaVulnerable = tarjetaDePersonaVulnerableOptional.get();

        RegistroUsoTarjeta nuevoUso = new RegistroUsoTarjeta();
        LocalDate fechaActual = LocalDate.now();
        LocalDateTime fechaActualCast = fechaActual.atStartOfDay();
        nuevoUso.setFechaUso(fechaActualCast);
        nuevoUso.setHeladera(heladera);
        nuevoUso.setCantidadViandas(mensajeRespuesta.getCantidadViandas());

        tarjetaDePersonaVulnerable.registrarUso(heladera, mensajeRespuesta.cantidadViandas);

        withTransaction(() -> {
            tarjetasDePersonasVulnerablesRepositorio.actualizar(tarjetaDePersonaVulnerable);
            registroUsoTarjetaRepositorio.guardar(nuevoUso);
        });


    }

    private ReceptorDeUsosPersonaVulnerable.MoldeMensajeRespuesta castear(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ReceptorDeUsosPersonaVulnerable.MoldeMensajeRespuesta.class);
    }
    private class MoldeMensajeRespuesta{
        @Setter
        @Getter
        private Integer idHeladera;
        @Getter
        @Setter
        private String nroTarjeta;
        @Getter
        @Setter
        private Integer cantidadViandas;

    }

}
