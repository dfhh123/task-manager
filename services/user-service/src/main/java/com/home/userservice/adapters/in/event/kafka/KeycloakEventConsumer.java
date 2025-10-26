package com.home.userservice.adapters.in.event.kafka;

import com.home.avro.event.user.UserCreatedEvent;
import com.home.userservice.adapters.out.persistence.jpa.UserRepository;
import com.home.userservice.domain.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakEventConsumer {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @KafkaListener(topics = "keycloak.user.event")
    @Transactional
    public void handleKeycloakEvent(KeycloakUserEvent event) {
        log.info("Received event: {} for user {}", event, event.getUserId());

        switch (event.getType()) {
            case "REGISTER" -> handleUserRegistered(event);
            case "UPDATE_PROFILE" -> handleProfileUpdated(event);
            case "UPDATE_EMAIL" -> handleEmailUpdated(event);
            case "DELETE_ACCOUNT" -> handleAccountDeleted(event);
            case "VERIFY_EMAIL" -> handleEmailVerified(event);
        }
    }

    private void handleUserRegistered(KeycloakUserEvent event) {
        if (userRepository.existsByKeycloakId(event.getUserId())) {
            log.warn("User profile already exists: {}", event);
        }

        var user = User.builder()
                .keycloakId(event.getUserId())
                .email(event.getEmail())
                .timezone("UTC").language("en")
                .build();

        var saved = userRepository.save(user);
        log.info("User profile created: {}", saved);

        eventPublisher.publishEvent(new UserCreatedEvent(saved.getId(), saved.getEmail()));
    }


}
