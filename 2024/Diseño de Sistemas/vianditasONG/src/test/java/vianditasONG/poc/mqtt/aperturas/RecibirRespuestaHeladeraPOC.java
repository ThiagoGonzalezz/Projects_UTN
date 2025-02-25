package vianditasONG.poc.mqtt.aperturas;

import vianditasONG.config.Config;
import vianditasONG.controllers.receptores.ReceptorDeRespuestasSolicitudes;
import vianditasONG.modelos.repositorios.aperturas.accionesSolicitadas.imp.AccionesSolicitadasRepositorio;
import vianditasONG.modelos.repositorios.heladeras.imp.HeladerasRepositorio;
import vianditasONG.modelos.repositorios.tarjetas.imp.TarjetasColaboradorRepositorio;
import vianditasONG.utils.mqtt.MQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;

public class RecibirRespuestaHeladeraPOC {

    public static void main(String[] args) throws MqttException {

        MQTTHandler mqttHandler = MQTTHandler.builder().build();
        mqttHandler.connect(Config.getInstancia().obtenerDelConfig("conexionBrokerMQTT"));

        HeladerasRepositorio heladerasRepositorio = HeladerasRepositorio.builder().build();
        TarjetasColaboradorRepositorio tarjetasColaboradorRepositorio = TarjetasColaboradorRepositorio.builder().build();
        AccionesSolicitadasRepositorio accionesSolicitadasRepositorio = AccionesSolicitadasRepositorio.builder().build();

        ReceptorDeRespuestasSolicitudes receptorDeRespuestasSolicitudes = ReceptorDeRespuestasSolicitudes.builder()
                .heladerasRepositorio(heladerasRepositorio)
                .tarjetasColaboradorRepositorio(tarjetasColaboradorRepositorio)
                .accionesSolicitadasRepositorio(accionesSolicitadasRepositorio)
                .build();


        mqttHandler.subscribe(Config.getInstancia().obtenerDelConfig("topicoRespuestasAperturas"), receptorDeRespuestasSolicitudes);

    }

    /*
    Topico = vianditasONG/heladeras/aperturas/respuestas
    La heladera le responde al sistema un JSON del siguiente estilo
    {
        "idHeladera": 2,
        "aperturaFehaciente": true,
        "fechaHoraApertura": "13/12/2004",
        "nroTarjeta" : "12345678912",
        "idAccionSolicitada" : 2
    }
    */



}
