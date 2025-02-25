package vianditasONG.poc.mqtt.sensores;

import vianditasONG.config.Config;
import vianditasONG.modelos.servicios.mensajeria.Contacto;
import vianditasONG.modelos.servicios.mensajeria.TipoDeContacto;
import vianditasONG.modelos.entities.heladeras.*;
import vianditasONG.modelos.servicios.mensajeria.notificaciones.NotificadorDeTelegram;
import vianditasONG.modelos.servicios.mensajeria.enviadorDeTelegram.telegrams.NotificacionIncidente;
import vianditasONG.modelos.entities.tecnicos.CalculadorDeTecnicoCercano;
import vianditasONG.modelos.entities.tecnicos.Tecnico;
import vianditasONG.modelos.repositorios.alertas.imp.AlertasRepositorio;
import vianditasONG.utils.mqtt.MQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecibirAlertasPOC {

    public static void main(String[] args) throws MqttException {

        MQTTHandler mqttHandler = MQTTHandler.builder().build();
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


        Heladera heladera1 = Heladera.builder().
                modeloHeladera(modelo).
                build();


        Heladera heladera2 = Heladera.builder().
                modeloHeladera(modelo).
                build();

        AlertasRepositorio alertasRepositorio = new AlertasRepositorio();

        SensorDeMovimiento sensorDeMovimiento1 = SensorDeMovimiento.builder().heladera(heladera1).build();
        SensorDeMovimiento sensorDeMovimiento2 = SensorDeMovimiento.builder().heladera(heladera2).build();

        /*ReceptorDeMovimientos receptorDeMovimientos = new ReceptorDeMovimientos();
        receptorDeMovimientos.agregarSensores(sensorDeMovimiento1, sensorDeMovimiento2);
        receptorDeMovimientos.setAlertasRepositorio(new AlertasRepositorio());

        mqttHandler.subscribe(Config.getInstancia().obtenerDelConfig("topicoAlertasMovimimientos"), receptorDeMovimientos);
*/
    }

    /*
        Topico = vianditasONG/heladeras/movimientos
        Se recibe la alerta de un sensor de movimiento con el siguiente formato JSON:
        {
            "idHeladera": 1
        }
    */

}
