package com.home.userservice.adapters.in.web.rest;

import com.home.userservice.application.dto.UserDomainDataDto;
import com.home.userservice.application.dto.UserRegistrationDataDto;
import com.home.userservice.application.dto.UserRequestRegistrationDto;
import com.home.userservice.application.dto.UserResponseRegistrationDto;
import com.home.userservice.application.service.AnonUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest({AnonUserController.class})
class AnonUserControllerTest {

    private final String TEST_KEYCLOAK_ID = "test-user";
    private final String TEST_EMAIL = "user_email@example.com";
    private final String TEST_PASSWORD = "secret_test_password";
    private final String TEST_LANGUAGE = "ru";
    private final String TEST_TIMEZONE = "UTC";

    private final UserRegistrationDataDto TEST_REGISTRATION_DATA = UserRegistrationDataDto.builder()
            .email(TEST_EMAIL)
            .password(TEST_PASSWORD)
            .build();

    private final UserDomainDataDto TEST_DOMAIN_DATA = UserDomainDataDto.builder()
            .language(TEST_LANGUAGE)
            .timezone(TEST_TIMEZONE)
            .build();

    private final UserRequestRegistrationDto TEST_REGISTRATION_DTO = UserRequestRegistrationDto.builder()
            .registrationData(TEST_REGISTRATION_DATA)
            .domainData(TEST_DOMAIN_DATA)
            .build();

    private final UserResponseRegistrationDto TEST_RESPONSE_DTO = UserResponseRegistrationDto.builder()
            .publicId(TEST_KEYCLOAK_ID)
            .authenticationData(TEST_REGISTRATION_DATA)
            .domainData(TEST_DOMAIN_DATA)
            .build();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnonUserService service;

    @Test
    void registerUser() throws Exception {
        when(service.registerUser(TEST_REGISTRATION_DTO)).thenReturn(TEST_RESPONSE_DTO);
        String version = "1";

        mockMvc.perform(post("/api/rest/%s/users/register".formatted(version), "")
                        .param("registrationData", """
                                {
                                    "email": "email",
                                    "password": "12345"
                                }""")
                        .param("domainData", """
                                {
                                    "language": "ru",
                                    "timezone": "UTC"
                                }""")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(TEST_KEYCLOAK_ID))
                .andDo(print());
    }
}
