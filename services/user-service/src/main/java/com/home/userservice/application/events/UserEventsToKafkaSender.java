package com.home.userservice.application.events;

import com.home.avro.event.user.*;
import com.home.userservice.application.config.KafkaTopicsProperties;
import lombok.AllArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserEventsToKafkaSender {

    private final KafkaTemplate<String, SpecificRecordBase> kafkaTemplate;

    private final KafkaTopicsProperties topics;

    @EventListener()
    public void publishEvent(UserCreatedEvent event) {
        kafkaTemplate.send(topics.getCreated(), event.getId().toString(), event);
    }

    @EventListener
    public void publishEvent(UserDeletedEvent event) {
        kafkaTemplate.send(topics.getDeleted(), event.getId().toString(), event);
    }

    @EventListener
    public void publishEvent(UserUpdatedEvent event) {
        kafkaTemplate.send(topics.getUpdated(), event.getId().toString(), event);
    }
}
