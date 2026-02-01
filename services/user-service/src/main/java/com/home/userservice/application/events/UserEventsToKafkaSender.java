package com.home.userservice.application.events;

import com.home.avro.event.user.*;
import lombok.AllArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserEventsToKafkaSender {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    @EventListener()
    public void publishEvent(UserCreatedEvent event) {
        kafkaTemplate.send("user-created", event.getId().toString(), event);
    }

    @EventListener
    public void publishEvent(UserDeletedEvent event) {
        kafkaTemplate.send("user-deleted", event.getId().toString(), event);
    }

    @EventListener
    public void publishEvent(UserUpdatedEvent event) {
        kafkaTemplate.send("user-updated", event.getId().toString(), event);
    }
}
