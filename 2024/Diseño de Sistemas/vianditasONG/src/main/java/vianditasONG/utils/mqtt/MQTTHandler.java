package vianditasONG.utils.mqtt;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Builder;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

@Builder
public class MQTTHandler {

    @Getter
    private static MqttClient sampleClient;
    public void connect(String broker) {
        String clientId = "vianditasONGPruebasCID";  // ID predeterminado
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            // Intento de conexión con el clientId predeterminado
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected with clientId: " + clientId);

        } catch (MqttException me) {
            // Si falla la conexión, genera un clientId aleatorio
            System.out.println("Failed to connect with clientId: " + clientId);
            System.out.println("Attempting with a new clientId...");

            // Genera un nuevo clientId aleatorio
            clientId = clientId + "_" + UUID.randomUUID().toString();

            try {
                // Intento de conexión con el clientId aleatorio
                sampleClient = new MqttClient(broker, clientId, persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);

                System.out.println("Connecting to broker: " + broker + " with new clientId: " + clientId);
                sampleClient.connect(connOpts);
                System.out.println("Connected with new clientId: " + clientId);

            } catch (MqttException e) {
                // Si también falla la conexión con el ID aleatorio, maneja el error
                System.out.println("Failed to connect with new clientId: " + clientId);
                e.printStackTrace();
            }
        }
    }

    public void subscribe(String topic, IMqttMessageListener receptor) throws MqttException {

        sampleClient.subscribe(topic, receptor);
        System.out.println("Subscribed to topic: " + topic);

    }

    public void publish(String topic, JsonElement jsonElement) throws MqttException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(jsonElement);
        MqttMessage message = new MqttMessage(jsonString.getBytes());
        message.setQos(2);
        sampleClient.publish(topic, message);
        System.out.println("Message published to topic: " + topic);

    }




}
