package com.realtime_snake_game.apiSnakeGame.runner;
import com.realtime_snake_game.apiSnakeGame.service.kafka.KafkaProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class KafkaCommandLineRunner implements CommandLineRunner {

    private final KafkaProducerService kafkaProducerService;

    public KafkaCommandLineRunner(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a message to send to Kafka (or 'q' to exit):");

        while (true) {
            char key = (char) System.in.read();
            if ("q".equalsIgnoreCase(Character.toString(key))) {
                break;
            }
            if (key == 'a'){
                kafkaProducerService.sendMessage("LEFT");
            }
            if (key == 'd'){
                kafkaProducerService.sendMessage("RIGHT");
            }
            if (key == 's'){
                kafkaProducerService.sendMessage("DOWN");
            }
            if (key == 'w'){
                kafkaProducerService.sendMessage("UP");
            }
            System.out.println("Message sent: " + key);
        }

        scanner.close();
        System.out.println("Application terminated.");
    }
}
