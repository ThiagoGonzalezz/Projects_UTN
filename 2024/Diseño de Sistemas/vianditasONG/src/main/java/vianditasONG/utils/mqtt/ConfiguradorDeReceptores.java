package vianditasONG.utils.mqtt;

import lombok.Builder;
import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.controllers.receptores.ReceptorDeRespuestasSolicitudes;
import vianditasONG.controllers.receptores.ReceptorDeMovimientos;
import vianditasONG.controllers.receptores.ReceptorDeTemperaturas;
import vianditasONG.controllers.receptores.ReceptorDeUsosPersonaVulnerable;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeMovimiento;
import vianditasONG.modelos.entities.heladeras.sensores.SensorDeTemperatura;
import org.eclipse.paho.client.mqttv3.MqttException;
import vianditasONG.modelos.repositorios.sensores.ISensoresDeMovimientoRepositorio;
import vianditasONG.modelos.repositorios.sensores.ISensoresDeTemperaturaRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeMovimientoRepositorio;
import vianditasONG.modelos.repositorios.sensores.imp.SensoresDeTemperaturaRepositorio;

import java.util.List;

@Builder
public class ConfiguradorDeReceptores {
    @Builder.Default
    private static MQTTHandler mqttHandler = ServiceLocator.getService(MQTTHandler.class);
    @Builder.Default
    private ISensoresDeMovimientoRepositorio sensoresDeMovimientoRepositorio = ServiceLocator.getService(SensoresDeMovimientoRepositorio.class);
    @Builder.Default
    private ISensoresDeTemperaturaRepositorio sensoresDeTemperaturaRepositorio = ServiceLocator.getService(SensoresDeTemperaturaRepositorio.class);

    public static void initReceptores(ISensoresDeMovimientoRepositorio movimientoRepo,
                                      ISensoresDeTemperaturaRepositorio temperaturaRepo){
        try {
            mqttHandler.connect(Config.getInstancia().obtenerDelConfig("conexionBrokerMQTT"));
            configurarReceptorDeMovimiento(movimientoRepo);
            configurarReceptorDeTemperaturas(temperaturaRepo);
            configurarReceptorDeSolicitudes();
            configurarReceptorDeUsos();
        } catch (MqttException e) {
            e.printStackTrace();
            System.err.println("Error al conectar o configurar los receptores: " + e.getMessage());
        }

    }

    private static void configurarReceptorDeMovimiento(ISensoresDeMovimientoRepositorio movimientoRepo) throws MqttException {

        List<SensorDeMovimiento> sensoresDeMovimiento = movimientoRepo.buscarTodos();
        ReceptorDeMovimientos receptorDeMovimientos = ServiceLocator.getService(ReceptorDeMovimientos.class);
        sensoresDeMovimiento.forEach(receptorDeMovimientos::agregarSensores);
        mqttHandler.subscribe(Config.getInstancia().obtenerDelConfig("topicoAlertasMovimimientos"), receptorDeMovimientos);
    }

    private static void configurarReceptorDeTemperaturas(ISensoresDeTemperaturaRepositorio temperaturaRepo) throws MqttException {
        List<SensorDeTemperatura> sensoresDeTemperatura = temperaturaRepo.buscarTodos();
        ReceptorDeTemperaturas receptorDeTemperaturas = ServiceLocator.getService(ReceptorDeTemperaturas.class);
        sensoresDeTemperatura.forEach(receptorDeTemperaturas::agregarSensores);
        mqttHandler.subscribe(Config.getInstancia().obtenerDelConfig("topicoTemperaturas"), receptorDeTemperaturas);
    }

    private static void configurarReceptorDeSolicitudes() throws MqttException {
        ReceptorDeRespuestasSolicitudes receptorDeRespuestasSolicitudes = ReceptorDeRespuestasSolicitudes.builder().build();
        mqttHandler.subscribe(Config.getInstancia().obtenerDelConfig("topicoRespuestasAperturas"), receptorDeRespuestasSolicitudes);
    }
    private static void configurarReceptorDeUsos() throws MqttException {
        ReceptorDeUsosPersonaVulnerable receptorDeUsosPersonaVulnerable = ReceptorDeUsosPersonaVulnerable.builder().build();
        mqttHandler.subscribe(Config.getInstancia().obtenerDelConfig("topicoUsosTarjetaPersonaVulnerable"), receptorDeUsosPersonaVulnerable);
    }
}

