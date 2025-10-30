package com.example.pizzeria.controller;

import com.example.pizzeria.dto.IngredientDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IngredientControllerIntegrationTest extends IntegrationTestSupport {

    @Test
    void shouldListIngredients() throws Exception {
    mockMvc.perform(get("/api/v1/ingredients")
            .header("Authorization", customerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("CHEESE"));
    }

    @Test
    void shouldCreateIngredient() throws Exception {
        IngredientDto dto = new IngredientDto();
        dto.setName("Chili Flakes");
        dto.setType("VEGETABLE");
        dto.setPrice(new BigDecimal("0.95"));

        mockMvc.perform(post("/api/v1/ingredients")
                        .header("Authorization", managerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Chili Flakes"));
    }

    @Test
    void shouldRequireManagerRoleOnUpdate() throws Exception {
        IngredientDto dto = new IngredientDto();
        dto.setName("Basil");
        dto.setType("VEGETABLE");
        dto.setPrice(new BigDecimal("0.60"));

        mockMvc.perform(put("/api/v1/ingredients/" + java.util.UUID.randomUUID())
                        .header("Authorization", customerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(dto)))
                .andExpect(status().isForbidden());
    }
}
