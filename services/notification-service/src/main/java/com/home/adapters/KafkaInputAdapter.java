package com.home.adapters;

import com.home.avro.event.user.UserCreatedDomainEvent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaInputAdapter {

    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    @KafkaListener(topics = "user.created", groupId = "notification-service")
    public void onUserCreated(ConsumerRecord<String, UserCreatedDomainEvent> record) {
        eventPublisher.publishEvent(record.value());
    }

    @KafkaListener(topics = "user.updated", groupId = "notification-service")
    public void onUserUpdated(ConsumerRecord<String, UserCreatedDomainEvent> record) {
        eventPublisher.publishEvent(record.value());
    }

    @KafkaListener(topics = "user.deleted", groupId = "notification-service")
    public void onUserDeleted(ConsumerRecord<String, UserCreatedDomainEvent> record) {
        eventPublisher.publishEvent(record.value());
    }
}
