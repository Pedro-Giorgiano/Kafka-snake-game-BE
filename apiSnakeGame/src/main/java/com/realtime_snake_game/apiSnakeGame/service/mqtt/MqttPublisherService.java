package com.realtime_snake_game.apiSnakeGame.service.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MqttPublisherService {
    private static final Logger LOGGER = Logger.getLogger(MqttPublisherService.class.getName());

    @Value("${mqtt.broker.url:tcp://your_computer_ip:1883}") // modify with your computer ip
    private String mqttBrokerUrl;

    @Value("${mqtt.client.id:SnakeGameBridge}")
    private String mqttClientId;

    @Value("${mqtt.topic:snake/direction}")
    private String mqttTopic;

    private MqttClient mqttClient;

    @PostConstruct
    public void init() {
        try {
            mqttClient = new MqttClient(mqttBrokerUrl, mqttClientId, new MemoryPersistence());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            connectOptions.setAutomaticReconnect(true);
            mqttClient.connect(connectOptions);
            LOGGER.info("Connected to MQTT broker: " + mqttBrokerUrl);
        } catch (MqttException e) {
            LOGGER.log(Level.SEVERE, "Error initializing MQTT client", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.disconnect();
                LOGGER.info("Disconnected from MQTT broker");
            }
        } catch (MqttException e) {
            LOGGER.log(Level.SEVERE, "Error disconnecting MQTT client", e);
        }
    }

    public void publishMessage(String message) {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                mqttMessage.setQos(1);
                mqttClient.publish(mqttTopic, mqttMessage);
                LOGGER.info("Published to MQTT topic: " + mqttTopic + ", message: " + message);
            } else {
                LOGGER.warning("Cannot publish to MQTT - client not connected");
                reconnectMqtt();
            }
        } catch (MqttException e) {
            LOGGER.log(Level.SEVERE, "Error publishing to MQTT", e);
            reconnectMqtt();
        }
    }

    private void reconnectMqtt() {
        try {
            if (mqttClient != null && !mqttClient.isConnected()) {
                LOGGER.info("Attempting to reconnect to MQTT broker...");
                mqttClient.connect();
                LOGGER.info("Reconnected to MQTT broker");
            }
        } catch (MqttException e) {
            LOGGER.log(Level.SEVERE, "Failed to reconnect to MQTT broker", e);
        }
    }
}