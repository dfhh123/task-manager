package com.home.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Notification Service - sends notifications to users
 * 
 * Architecture: Hexagonal (Ports and Adapters)
 * - Domain: business logic and models
 * - Application: use cases and ports
 * - Adapter: inbound (Kafka) and outbound (Email, HTTP, Persistence) adapters
 */
@SpringBootApplication
@EnableKafka
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}

