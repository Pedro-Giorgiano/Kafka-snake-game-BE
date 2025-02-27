package com.realtime_snake_game.apiSnakeGame.runner;
import com.realtime_snake_game.apiSnakeGame.service.kafka.KafkaProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class KafkaCommandLineRunner implements CommandLineRunner {
    private final KafkaProducerService kafkaProducerService;
    private final Map<Character, String> keyDirections = new HashMap<>();

    public KafkaCommandLineRunner(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
        
        // Initialize key mappings
        keyDirections.put('w', "UP");
        keyDirections.put('a', "LEFT");
        keyDirections.put('s', "DOWN");
        keyDirections.put('d', "RIGHT");
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a message to send to Kafka (or 'q' to exit):");

        while (true) {
            char key = (char) System.in.read();
            
            if ('q' == key) {
                break;
            }
            
            String direction = keyDirections.get(key);
            if (direction != null) {
                kafkaProducerService.sendMessage(direction);
                System.out.println("Message sent: " + key + " -> " + direction);
            }
        }

        scanner.close();
        System.out.println("Application terminated.");
    }
}
