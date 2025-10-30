package com.example.pizzeria.controller;

import com.example.pizzeria.dto.SideDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SideControllerIntegrationTest extends IntegrationTestSupport {

    @Test
    void shouldListSides() throws Exception {
    mockMvc.perform(get("/api/v1/sides")
            .header("Authorization", customerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").isNotEmpty());
    }

    @Test
    void shouldCreateSideWithManagerRole() throws Exception {
        SideDto dto = new SideDto();
        dto.setName("Breadsticks");
        dto.setDescription("Cheesy");
        dto.setPrice(new BigDecimal("4.10"));

    mockMvc.perform(post("/api/v1/sides")
            .header("Authorization", managerToken())
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJson(dto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Breadsticks"));
    }

    @Test
    void shouldFailValidationForMissingName() throws Exception {
        SideDto dto = new SideDto();
        dto.setDescription("Crunchy");
        dto.setPrice(BigDecimal.ONE);

        mockMvc.perform(post("/api/v1/sides")
                        .header("Authorization", managerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(dto)))
                .andExpect(status().isBadRequest());
    }
}
