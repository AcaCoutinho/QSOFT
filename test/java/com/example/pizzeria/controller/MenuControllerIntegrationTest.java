package com.example.pizzeria.controller;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerIntegrationTest extends IntegrationTestSupport {

    @Test
    void shouldReturnMenuWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pizzas[0].name").isNotEmpty())
                .andExpect(jsonPath("$.ingredients").isArray());
    }
}
