package com.realtime_snake_game.apiSnakeGame.service.kafka;

import com.realtime_snake_game.apiSnakeGame.service.mqtt.MqttPublisherService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.logging.Logger;

@Service
public class KafkaConsumerService {
    private static final Logger LOGGER = Logger.getLogger(KafkaConsumerService.class.getName());

    private final MqttPublisherService mqttPublisherService;

    @Autowired
    public KafkaConsumerService(MqttPublisherService mqttPublisherService) {
        this.mqttPublisherService = mqttPublisherService;
    }

    @KafkaListener(topics = "snake-game")
    public void processMove(ConsumerRecord<String, String> record) {
        String move = record.value().trim().toUpperCase();

        if (isValidDirection(move)) {
            LOGGER.info("New move received from Kafka: " + move);
            mqttPublisherService.publishMessage(move);
        } else {
            LOGGER.warning("Invalid direction received: " + move);
        }
    }

    private boolean isValidDirection(String direction) {
        return direction.equals("UP") ||
                direction.equals("DOWN") ||
                direction.equals("LEFT") ||
                direction.equals("RIGHT");
    }
}