package vianditasONG.controllers.receptores;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;
import vianditasONG.modelos.entities.incidentes.TipoDeAlerta;
import vianditasONG.modelos.repositorios.alertas.imp.AlertasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.IHeladerasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.EnviadorDeMails;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeMails.mails.MailNotificacion;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams.NotificacionIncidente;
import vianditasONG.utils.Persistente;
import vianditasONG.modelos.entities.incidentes.Alerta;
import vianditasONG.modelos.repositorios.alertas.IAlertasRepositorio;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;

@Builder
@Getter
public class ReceptorDeTemperaturas implements IMqttMessageListener, WithSimplePersistenceUnit {


    @Builder.Default
    private List<SensorDeTemperatura> sensoresDeTemperatura = new ArrayList<>();

    @Builder.Default
    private IAlertasRepositorio alertasRepositorio = ServiceLocator.getService(AlertasRepositorio.class);
    @Builder.Default
    private IHeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);

    public void agregarSensores(SensorDeTemperatura... nuevosSensores) {
        Collections.addAll(this.sensoresDeTemperatura, nuevosSensores);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        try {
            MoldeMensajeTemp mensajeTemp = this.castear(mqttMessage.toString());

            // Derivar al sensor de temperatura correspondiente la temperatura
            for (SensorDeTemperatura sensor : sensoresDeTemperatura) {
                if (sensor.getHeladera().getId().equals(mensajeTemp.getIdHeladera())) {
                    if (!sensor.tieneTemperaturaAdecuada(mensajeTemp.getTemperatura())) {
                        Optional<Heladera> heladeraOptional = heladerasRepositorio.buscar(sensor.getHeladera().getId());

                        if (heladeraOptional.isPresent()) {
                            Heladera heladera = heladeraOptional.get();
                            Alerta alerta = Alerta.of(TipoDeAlerta.TEMP_FUERA_DE_RANGO, heladera);
                            alerta.setFechaYHora(LocalDateTime.now());
                            heladera.registarIncidente(alerta);
                            withTransaction(() -> alertasRepositorio.guardar(alerta));
                        } else {
                            System.err.println("Heladera no encontrada para el id: " + mensajeTemp.getIdHeladera());
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el mensaje MQTT: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    private MoldeMensajeTemp castear(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, MoldeMensajeTemp.class);
    }


    public static class MoldeMensajeTemp {

        @Column(name = "id_heladera")
        private Long idHeladera;

        @Column(name = "temperatura")
        private Float temperatura;

        public Long getIdHeladera() {
            return idHeladera;
        }

        public void setIdHeladera(Long idHeladera) {
            this.idHeladera = idHeladera;
        }

        public Float getTemperatura() {
            return temperatura;
        }

        public void setTemperatura(Float temperatura) {
            this.temperatura = temperatura;
        }
    }
}


