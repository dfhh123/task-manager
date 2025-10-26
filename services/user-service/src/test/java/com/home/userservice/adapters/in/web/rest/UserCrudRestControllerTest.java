package com.home.userservice.adapters.in.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.userservice.application.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link UserCrudRestController}
 */
@WebMvcTest({UserCrudRestController.class})
public class UserCrudRestControllerTest {

    private static final UUID TEST_ID = UUID.randomUUID();
    private static final String TEST_EMAIL = "example123@gmail.com";

    private static final UserDto TEST_DTO = new UserDto(TEST_ID, TEST_EMAIL);


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @MockitoBean
    private UserService userService;

    @Test
    public void getOne() throws Exception {
        when(userService.getOne(TEST_ID)).thenReturn(TEST_DTO);

        mockMvc.perform(get("/api/rest/v1/users/{id}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(TEST_ID.toString()))
                .andExpect(jsonPath("$.email").value(TEST_EMAIL));
    }

    @Test
    public void delete() throws Exception {
        when(userService.delete(TEST_ID)).thenReturn(TEST_DTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/rest/v1/users/{id}", TEST_ID))
                .andExpect(status().isNoContent())
                .andExpect(header().doesNotExist("Content-Type"));
    }

    @Test
    public void create() throws Exception {
        when(userService.create(TEST_DTO)).thenReturn(TEST_DTO);

        mockMvc.perform(post("/api/rest/v1/users")
                        .content(objectMapper.writeValueAsString(TEST_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        when(userService.update(TEST_ID, TEST_DTO)).thenReturn(TEST_DTO);

        mockMvc.perform(put("/api/rest/v1/users/{id}", TEST_ID)
                        .content(objectMapper.writeValueAsString(TEST_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
