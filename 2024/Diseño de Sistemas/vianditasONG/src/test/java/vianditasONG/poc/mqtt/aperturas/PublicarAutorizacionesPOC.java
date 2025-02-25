package vianditasONG.poc.mqtt.aperturas;

import vianditasONG.config.Config;
import vianditasONG.modelos.entities.colaboradores.Humano;
import vianditasONG.modelos.entities.colaboradores.tarjeta.TarjetaColaborador;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.AccionSolicitada;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.PublicadorSolicitudApertura;
import vianditasONG.modelos.entities.heladeras.aperturasColaboradores.TipoDeAccion;
import vianditasONG.utils.mqtt.MQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PublicarAutorizacionesPOC {

    public static void main(String[] args) throws MqttException {

        MQTTHandler mqttHandler = MQTTHandler.builder().build();
        mqttHandler.connect(Config.getInstancia().obtenerDelConfig("conexionBrokerMQTT"));

        Heladera heladera1 = Heladera.builder().build();
        List<Heladera> heladerasSolicitadas = new ArrayList<>();
        heladerasSolicitadas.add(heladera1);

        Humano colaborador1 = Humano.builder().build();
        TarjetaColaborador tarjetaColaborador1 = TarjetaColaborador.builder().build();
        tarjetaColaborador1.setColaboradorAsociado(colaborador1);
        tarjetaColaborador1.setCodigo("12345678912");



        AccionSolicitada accionSolicitada = AccionSolicitada.builder()
                .tipoDeAccion(TipoDeAccion.APERTURA_PARA_INGRESAR_DONACION)
                .heladerasSolicitadas(heladerasSolicitadas)
                .tarjetaColaborador(tarjetaColaborador1)
                .fechaYHoraDeSolicitud(LocalDateTime.now())
                .fechaDeCaducidadDeSolicitud(LocalDateTime.now().plusHours(3)) //Esto lo haria el controller
                .build();

        PublicadorSolicitudApertura publicadorSolicitudApertura = PublicadorSolicitudApertura.builder().build();
        publicadorSolicitudApertura.publicarAperturaSolicitada(accionSolicitada);

    }

    /*
        Publica la autorización a la heladera para que esta sepa si el colaborador está o no autorizado.
        Topico = vianditasONG/heladeras/aperturas/solicitudes
        El JSON que se publica es de la siguiente forma:
        {
            "idHeladera":1,
            "nroTarjeta":"12345678912",
            "fechaHoraCaducidadSolicitud":"2024-06-27T17:42:51.163628100"
        }
    */

}
