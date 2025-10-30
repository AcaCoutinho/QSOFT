package com.example.pizzeria.controller;

import com.example.pizzeria.dto.PizzaBaseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PizzaBaseControllerIntegrationTest extends IntegrationTestSupport {

    @Test
    void shouldListBases() throws Exception {
    mockMvc.perform(get("/api/v1/bases")
            .header("Authorization", customerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Classic"));
    }

    @Test
    void shouldCreateBaseAsManager() throws Exception {
        PizzaBaseDto dto = new PizzaBaseDto();
        dto.setName("Deep Dish");
        dto.setDescription("Thick crust");
        dto.setPrice(new BigDecimal("7.80"));

        String response = mockMvc.perform(post("/api/v1/bases")
                        .header("Authorization", managerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(delete("/api/v1/bases/" + id)
                        .header("Authorization", managerToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldRejectUpdateWithoutToken() throws Exception {
        PizzaBaseDto dto = new PizzaBaseDto();
        dto.setName("Airy");
        dto.setDescription("Light");
        dto.setPrice(new BigDecimal("5.10"));

        mockMvc.perform(put("/api/v1/bases/" + java.util.UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(dto)))
                .andExpect(status().isUnauthorized());
    }
}
