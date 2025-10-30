package com.example.pizzeria.controller;

import com.example.pizzeria.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIntegrationTest extends IntegrationTestSupport {

    @Test
    void shouldAllowManagerToListUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header("Authorization", managerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("manager"));
    }

    @Test
    void shouldForbidCustomerFromListingUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .header("Authorization", customerToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldCreateAndDeleteUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("team_member");
        request.setPassword("TempPass123!");
        request.setEmail("team_member@pizzeria.local");
        request.setRole("CUSTOMER");

        String response = mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", managerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("team_member"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(delete("/api/v1/users/" + id)
                        .header("Authorization", managerToken()))
                .andExpect(status().isNoContent());
    }
}
