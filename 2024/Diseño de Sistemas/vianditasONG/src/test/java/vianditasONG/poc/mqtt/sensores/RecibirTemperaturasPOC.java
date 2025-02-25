package vianditasONG.poc.mqtt.sensores;

import vianditasONG.config.Config;

import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.Modelo;
import vianditasONG.controllers.receptores.ReceptorDeTemperaturas;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams.NotificacionIncidente;
import vianditasONG.modelos.entities.tecnicos.CalculadorDeTecnicoCercano;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.utils.mqtt.MQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecibirTemperaturasPOC {

    public static void main(String[] args) throws MqttException {

        MQTTHandler mqttHandler =  MQTTHandler.builder().build();
        mqttHandler.connect(Config.getInstancia().obtenerDelConfig("conexionBrokerMQTT"));

        Modelo modelo = new Modelo();
        modelo.setTempMaximaEnGradosCelsius(20f);
        modelo.setTempMinimaEnGradosCelsius(0f);


        Contacto contacto = Contacto.builder()
                .tipoDeContacto(TipoDeContacto.TELEGRAM)
                .contacto("6190209796")
                .build();

        NotificadorDeTelegram notificadorDeTelegram = mock(NotificadorDeTelegram.class);
        doNothing().when(notificadorDeTelegram).notificar(contacto, any(NotificacionIncidente.class));

        Tecnico tecnico = Tecnico.builder()
                .contacto(contacto)
                .medioDeAviso(notificadorDeTelegram)
                .build();

        CalculadorDeTecnicoCercano calculadorDeTecnico = mock(CalculadorDeTecnicoCercano.class);
        when(calculadorDeTecnico.obtenerTecnicoMasCercano(any(Heladera.class))).thenReturn(tecnico);

        Heladera heladera1 = Heladera.builder()
                .modeloHeladera(modelo).
                build();


        Heladera heladera2 = Heladera.builder()
                .modeloHeladera(modelo).
                build();


        SensorDeTemperatura sensorDeTemperatura1 = SensorDeTemperatura.builder().heladera(heladera1).build();
        SensorDeTemperatura sensorDeTemperatura2 = SensorDeTemperatura.builder().heladera(heladera2).build();
        //SensorDeTemperatura sensorDeTemperatura1 = new SensorDeTemperatura(EvaluadorDeTemperatura.builder().heladera(heladera1).alertasRepositorio(new AlertasRepositorio()).build());
        //SensorDeTemperatura sensorDeTemperatura2 = new SensorDeTemperatura(EvaluadorDeTemperatura.builder().heladera(heladera2).alertasRepositorio(new AlertasRepositorio()).build());

        ReceptorDeTemperaturas receptorDeTemperaturas = ReceptorDeTemperaturas.builder().build();
        receptorDeTemperaturas.agregarSensores(sensorDeTemperatura1, sensorDeTemperatura2);
        //receptorDeTemperaturas.setAlertasRepositorio(new AlertasRepositorio());

        mqttHandler.subscribe(Config.getInstancia().obtenerDelConfig("topicoTemperaturas"), receptorDeTemperaturas);

    }

    /*
    Topico = vianditasONG/heladeras/temperaturas
        Se recibe la temperatura de una heladera con el siguiente formato JSON:
        {
          "idHeladera": 1,
          "temperatura": 13.5
        }
    */

}