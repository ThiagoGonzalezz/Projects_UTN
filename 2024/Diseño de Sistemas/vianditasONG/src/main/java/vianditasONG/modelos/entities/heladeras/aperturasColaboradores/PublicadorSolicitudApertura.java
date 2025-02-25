package vianditasONG.modelos.entities.heladeras.aperturasColaboradores;

import vianditasONG.config.Config;
import vianditasONG.config.ServiceLocator;
import vianditasONG.modelos.entities.heladeras.Heladera;
import vianditasONG.utils.mqtt.MQTTHandler;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Builder;
import org.eclipse.paho.client.mqttv3.MqttException;

@Builder
public class PublicadorSolicitudApertura {

    @Builder.Default
    private static MQTTHandler mqttHandler = ServiceLocator.getService(MQTTHandler.class);

    public static void publicarAperturaSolicitada(AccionSolicitada accionSolicitada) {

        try{
            for(Heladera heladera : accionSolicitada.getHeladerasSolicitadas()){

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("idHeladera", heladera.getId());
                jsonObject.addProperty("idAccionSolicitada", accionSolicitada.getId());
                jsonObject.addProperty("nroTarjeta", accionSolicitada.getTarjetaColaborador().getCodigo());
                jsonObject.addProperty("fechaHoraCaducidadSolicitud", accionSolicitada.getFechaDeCaducidadDeSolicitud().toString());
                JsonElement jsonElement = jsonObject;
                mqttHandler.publish(Config.getInstancia().obtenerDelConfig("topicoPublicadorSolicitudApertura"), jsonElement);

            }
        }catch (MqttException mqttException){
            throw new RuntimeException("No se pudo publicar la solicitud de apertura correctamente");
        }


    }

}
