package com.home.userservice.application.events;

import com.home.avro.event.user.UserCreatedEvent;
import com.home.avro.event.user.UserDeletedEvent;
import com.home.avro.event.user.UserUpdatedEvent;
import com.home.userservice.application.config.KafkaTopicsProperties;
import org.apache.avro.specific.SpecificRecordBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserEventsToKafkaSenderTest {

    @Mock
    private KafkaTemplate<String, SpecificRecordBase> kafkaTemplate;

    @Mock
    private KafkaTopicsProperties topics;

    @InjectMocks
    private UserEventsToKafkaSender sender;

    @Test
    void created_event_published_to_created_topic() {
        var event = new UserCreatedEvent(UUID.randomUUID(), "example@email.com");
        given(topics.getCreated()).willReturn("created-topic");

        sender.publishEvent(event);

        then(kafkaTemplate).should().send("created-topic", event.getId().toString(), event);
    }

    @Test
    void updated_event_published_to_updated_topic() {
        var event = new UserUpdatedEvent(UUID.randomUUID(), "example@email.com");
        given(topics.getUpdated()).willReturn("updated-topic");

        sender.publishEvent(event);

        then(kafkaTemplate).should().send("updated-topic", event.getId().toString(), event);
    }

    @Test
    void deleted_event_published_to_deleted_topic() {
        var event = new UserDeletedEvent(UUID.randomUUID());
        given(topics.getDeleted()).willReturn("deleted-topic");

        sender.publishEvent(event);

        then(kafkaTemplate).should().send("deleted-topic", event.getId().toString(), event);
    }
}