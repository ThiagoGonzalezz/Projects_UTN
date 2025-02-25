package vianditasONG.controllers.receptores;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Builder;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;
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
public class ReceptorDeMovimientos implements IMqttMessageListener, WithSimplePersistenceUnit {

    @Builder.Default
    private List<SensorDeMovimiento> sensoresDeMovimiento = new ArrayList<>();

    @Builder.Default
    private IAlertasRepositorio alertasRepositorio = ServiceLocator.getService(AlertasRepositorio.class);

    @Builder.Default
    private IHeladerasRepositorio heladerasRepositorio = ServiceLocator.getService(HeladerasRepositorio.class);

    public void agregarSensores(SensorDeMovimiento... nuevosSensores) {
        Collections.addAll(this.sensoresDeMovimiento, nuevosSensores);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        try {
            MoldeMensajeMov mensajeMov = this.castear(mqttMessage.toString());

            for (SensorDeMovimiento sensor : sensoresDeMovimiento) {
                if (sensor.getHeladera().getId().equals(mensajeMov.getIdHeladera())) {
                    Optional<Heladera> heladeraOptional = heladerasRepositorio.buscar(sensor.getHeladera().getId());
                    if (heladeraOptional.isPresent()) {
                        Heladera heladera = heladeraOptional.get();
                        if (sensor.recibirAlerta()) {
                            Alerta alerta = Alerta.of(TipoDeAlerta.INTENTO_FRAUDE, heladera);
                            alerta.setFechaYHora(LocalDateTime.now());
                            heladera.registarIncidente(alerta);
                            withTransaction(() -> alertasRepositorio.guardar(alerta));
                        }
                    } else {
                        System.err.println("Heladera no encontrada para el id: " + mensajeMov.getIdHeladera());
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


    private MoldeMensajeMov castear(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, MoldeMensajeMov.class);
    }

    public static class MoldeMensajeMov {

        @Column(name = "id_heladera")
        private Long idHeladera;

        public Long getIdHeladera() {
            return idHeladera;
        }

        public void setIdHeladera(Long idHeladera) {
            this.idHeladera = idHeladera;
        }
    }
}
