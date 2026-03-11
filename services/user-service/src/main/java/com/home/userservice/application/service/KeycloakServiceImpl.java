package com.home.userservice.application.service;

import com.home.userservice.application.dto.UserRegistrationDataDto;
import com.home.userservice.application.exceptions.KeycloakUserRegistrationException;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloakClient;
    private final String realmName;

    public KeycloakServiceImpl(
            @Value("${keycloak.server-url}") String keycloakUrl,
            @Value("${keycloak.realm}") String realmName,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret) {

        this.realmName = realmName;

        this.keycloakClient = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(realmName)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        log.info("Keycloak admin client initialized");
    }

    @Override
    public String registerUser(UserRegistrationDataDto registrationData) {
        var user = new UserRepresentation();

        user.setUsername(registrationData.email());
        user.setEmail(registrationData.email());
        user.setEnabled(true);
        user.setEmailVerified(false);


        try (var responce = keycloakClient.realm(realmName).users().create(user)) {
            if (responce.getStatus() != 201) {
                String userId = CreatedResponseUtil.getCreatedId(responce);
                log.info("User created with ID: {}", userId);
                return userId;
            } else {
                String errorBody = responce.readEntity(String.class);
                log.error("Error while registering user. Status: {}. Body: {}", responce.getStatus(), errorBody);
                throw new KeycloakUserRegistrationException(
                        "Error %s while creating user in auth service".formatted(responce.getStatus()),
                        registrationData);
            }

        } catch (Exception e) {
            log.error("Error while registering user {}", e.getMessage());
            throw new KeycloakUserRegistrationException("Internal error while connecting to Keycloak", registrationData);
        }
    }

    @PreDestroy
    public void close() {
        if (keycloakClient != null) keycloakClient.close();
    }
}
