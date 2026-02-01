package com.home.userservice.adapters.in.web.rest;

import com.home.userservice.application.dto.UserDomainDataDto;
import com.home.userservice.application.dto.UserResponseDto;
import com.home.userservice.application.service.DomainUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(AdminUserRestController.class)
@AutoConfigureRestTestClient
@ActiveProfiles("test")
@WithMockUser(username = "test-admin", roles = "ADMIN")
class AdminUserRestControllerTest {

    private static final String TEST_ID = String.valueOf(UUID.randomUUID());
    private static final UserResponseDto TEST_USER_RESPONSE_DTO = getTestResponseDto();
    private static final String BASE_URI = "/api/rest/v1/users";

    @Autowired
    private RestTestClient testClient;

    @MockitoBean
    private DomainUserService domainUserService;

    @Test
    void testGetOneUser() {
        when(domainUserService.getOne(TEST_ID)).thenReturn(TEST_USER_RESPONSE_DTO);

        testClient.get()
                .uri(BASE_URI + "/{id}", TEST_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserResponseDto.class).isEqualTo(TEST_USER_RESPONSE_DTO);
    }

//    @Test
//    void testCreateUser() {
//        UserDomainDataDto testDto = new UserDomainDataDto("ru", "UTC");
//        when(domainUserService.createOne(any(UserDomainDataDto.class))).thenReturn(TEST_USER_RESPONSE_DTO);
//
//        testClient.post()
//                .uri(BASE_URI)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body("""
//                        {
//                        "language": "ru",
//                        "timezone": "UTC"
//                        }
//                        """)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBody(UserResponseDto.class).isEqualTo(TEST_USER_RESPONSE_DTO);
//    }

    @Test
    void testPutUser() {
        UserDomainDataDto testDto = new UserDomainDataDto("ru", "UTC");
        when(domainUserService.putOne(eq(TEST_ID), any(UserDomainDataDto.class))).thenReturn(TEST_USER_RESPONSE_DTO);

        testClient.put()
                .uri(BASE_URI + "/{id}", TEST_ID)
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

    @Test
    void testPatchUser() {
        UserDomainDataDto testDto = new UserDomainDataDto("ru", "UTC");
        when(domainUserService.patchOne(eq(TEST_ID), any(UserDomainDataDto.class))).thenReturn(TEST_USER_RESPONSE_DTO);

        testClient.patch()
                .uri(BASE_URI + "/{id}", TEST_ID)
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

    @Test
    void testDeleteUser() {
        when(domainUserService.deleteOne(TEST_ID)).thenReturn(TEST_USER_RESPONSE_DTO);

        testClient.delete()
                .uri(BASE_URI + "/{id}", TEST_ID)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

    private static UserResponseDto getTestResponseDto() {
        return new UserResponseDto(TEST_ID, "ru", "UTC", LocalDateTime.now(), LocalDateTime.now());
    }

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(authz -> authz
                            .anyRequest().authenticated()
                    )
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }
    }
}