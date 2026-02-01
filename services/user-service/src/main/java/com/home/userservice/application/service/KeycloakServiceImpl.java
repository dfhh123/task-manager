package com.home.userservice.application.service;

import com.home.userservice.application.config.KeycloakProperties;
import com.home.userservice.application.dto.UserRegistrationDataDto;
import com.home.userservice.application.exceptions.KeycloakUserRegistrationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloakClient;

    private final KeycloakProperties keycloakProperties;

    public KeycloakServiceImpl() {

        this.keycloakClient = KeycloakBuilder.builder()
                .serverUrl("http://keycloak")
                .realm("master")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId("user-service")
                .clientSecret("secret")
                .build();
    }

    @Override
    public String registerUser(UserRegistrationDataDto registrationData) {
        var user = new UserRepresentation();

        user.setUsername(registrationData.email());
        user.setEmail(registrationData.email());

        Response response = keycloakClient.realm("super-pet").users().create(user);

        if (response.getStatus() == 201) {
            String location = response.getLocation().toString();
            String userId = location.substring(location.lastIndexOf("/") + 1);
            return userId;
        } else {
            throw new KeycloakUserRegistrationException("error %s while creating user in auth service".formatted(response.getStatus()),
                                                    registrationData);
        }
    }
}
