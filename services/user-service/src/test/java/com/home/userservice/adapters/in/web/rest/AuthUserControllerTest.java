package com.home.userservice.adapters.in.web.rest;

import com.home.userservice.application.dto.UserDomainDataDto;
import com.home.userservice.application.dto.UserResponseDto;
import com.home.userservice.application.service.AuthUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(AuthUserController.class)
@ActiveProfiles("test")
@AutoConfigureRestTestClient
@WithMockUser(username = "test-user", roles = "USER")
class AuthUserControllerTest {

    private static final String TEST_KEYCLOAK_ID = "test-user";
    private static final UserResponseDto TEST_USER_RESPONSE_DTO = getTestResponseDto();

    @Autowired
    private RestTestClient testClient;

    @MockitoBean
    private AuthUserService userService;

    @Test
    void testGetAuthenticatedUser() {
        when(userService.getAuthenticatedUser(TEST_KEYCLOAK_ID)).thenReturn(TEST_USER_RESPONSE_DTO);

        testClient.get()
                .uri("/api/rest/v1/users/me")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserResponseDto.class).isEqualTo(TEST_USER_RESPONSE_DTO);
    }

    @Test
    void testPatchAuthenticatedUser() {
        UserDomainDataDto testDto = new UserDomainDataDto("ru", "UTC");
        when(userService.patchAuthenticatedUser(TEST_KEYCLOAK_ID, testDto)).thenReturn(TEST_USER_RESPONSE_DTO);

        testClient.patch()
                .uri("/api/rest/v1/users/me")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {
                        "language": "ru",
                        "timezone": "UTC"
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserResponseDto.class).isEqualTo(TEST_USER_RESPONSE_DTO);
    }

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(authz -> authz
                            .anyRequest().authenticated()
                    )
                    .csrf(csrf -> csrf.disable());
            return http.build();
        }
    }

    private static UserResponseDto getTestResponseDto() {
        return new UserResponseDto(UUID.randomUUID().toString(), "ru",
                "UTC", LocalDateTime.now(), LocalDateTime.now());
    }
}