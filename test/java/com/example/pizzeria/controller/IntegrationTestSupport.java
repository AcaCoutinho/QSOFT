package com.example.pizzeria.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class IntegrationTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private String managerToken;
    private String customerToken;

    protected String asJson(Object value) throws Exception {
        return objectMapper.writeValueAsString(value);
    }

    protected String managerToken() throws Exception {
        if (managerToken == null) {
            managerToken = authenticate("manager", "Password123!");
        }
        return managerToken;
    }

    protected String customerToken() throws Exception {
        if (customerToken == null) {
            customerToken = authenticate("customer", "Password123!");
        }
        return customerToken;
    }

    private String authenticate(String username, String password) throws Exception {
        String payload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andReturn();
        String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonNode node = objectMapper.readTree(response);
        return "Bearer " + node.get("token").asText();
    }
}
