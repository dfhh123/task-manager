package com.home.notificationservice.events;

import com.home.avro.event.user.UserCreatedEvent;
import com.home.avro.event.user.UserDeletedEvent;
import com.home.avro.event.user.UserUpdatedEvent;
import com.home.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for user events
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventKafkaConsumer {

    private final NotificationService notificationService;

    /**
     * @param event UserCreatedEvent
     */
    @KafkaListener(topics = "user.created", groupId = "${spring.kafka.consumer.group-id}")
    public void onUserCreated(UserCreatedEvent event) {
        String userId = event.getId();
        log.info("Processing UserCreatedEvent for user ID: {}", userId);
        try {
            notificationService.sendGreetingsNotification(event.getId());
            log.info("Successfully processed UserCreatedEvent for user ID: {}", userId);
        } catch (Exception e) {
            log.error("Error processing UserCreatedEvent for user ID: {}. Error: {}", userId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * @param event UserUpdatedEvent
     */
    @KafkaListener(topics = "user.updated", groupId = "${spring.kafka.consumer.group-id}")
    public void onUserUpdated(UserUpdatedEvent event) {
        String userId = event.getId();
        log.info("Processing UserUpdatedEvent for user ID: {}", userId);
        try {
            notificationService.sendUserUpdateNotification(event.getId());
            log.info("Successfully processed UserUpdatedEvent for user ID: {}", userId);
        } catch (Exception e) {
            log.error("Error processing UserUpdatedEvent for user ID: {}. Error: {}", userId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * @param event UserDeletedEvent
     */
    @KafkaListener(topics = "user.deleted", groupId = "${spring.kafka.consumer.group-id}")
    public void onUserDeleted(UserDeletedEvent event) {
        String userId = event.getId();
        log.info("Processing UserDeletedEvent for user ID: {}", userId);
        try {
            notificationService.sendUserDeletionNotification(event.getId());
            log.info("Successfully processed UserDeletedEvent for user ID: {}", userId);
        } catch (Exception e) {
            log.error("Error processing UserDeletedEvent for user ID: {}. Error: {}", userId, e.getMessage(), e);
            throw e;
        }
    }
}
