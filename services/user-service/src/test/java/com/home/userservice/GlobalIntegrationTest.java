package com.home.userservice;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class GlobalIntegrationTest {

    private static final String POSTGRES_VERSION = System.getProperty("postgres.version", "latest");
    private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres")
                                                                                            .withTag(POSTGRES_VERSION);

    private static final String KAFKA_VERSION = System.getProperty("kafka.version", "latest");
    private static final DockerImageName KAFKA_IMAGE = DockerImageName.parse("confluentinc/cp-kafka")
                                                                                            .withTag(KAFKA_VERSION);

    private static final String KEYCLOAK_VERSION = System.getProperty("keycloak.version", "latest");
    private static final DockerImageName KEYCLOAK_IMAGE = DockerImageName.parse("quay.io/keycloak/keycloak")
                                                                                            .withTag(KEYCLOAK_VERSION);

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(POSTGRES_IMAGE);

    @Container
    @ServiceConnection
    static ConfluentKafkaContainer kafka = new ConfluentKafkaContainer(KAFKA_IMAGE);

    @Container
    static KeycloakContainer keycloak = new KeycloakContainer(KEYCLOAK_IMAGE.toString())
            .withRealmImportFile("resources/test-realm.json")
            .withEnv("KC_BOOTSTRAP_ADMIN_USERNAME", "admin")
            .withEnv("KC_BOOTSTRAP_ADMIN_PASSWORD", "admin")
            .withCommand("start-dev");

    @DynamicPropertySource
    static void kcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", keycloak::getAuthServerUrl);
    }

    @Test
    void contextLoads() {
        System.out.println(postgres.getUsername() + "\n" + postgres.getPassword());
    }
}