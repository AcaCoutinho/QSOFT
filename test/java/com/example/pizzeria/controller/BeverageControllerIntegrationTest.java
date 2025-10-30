package com.example.pizzeria.controller;

import com.example.pizzeria.dto.BeverageDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BeverageControllerIntegrationTest extends IntegrationTestSupport {

    @Test
    void shouldListBeverages() throws Exception {
    mockMvc.perform(get("/api/v1/beverages")
            .header("Authorization", customerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").isNotEmpty());
    }

    @Test
    void shouldCreateAndDeleteBeverage() throws Exception {
        BeverageDto dto = new BeverageDto();
        dto.setName("Orange Juice");
        dto.setDescription("Fresh");
        dto.setPrice(new BigDecimal("2.80"));

        String body = mockMvc.perform(post("/api/v1/beverages")
                        .header("Authorization", managerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Orange Juice"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(body).get("id").asText();

        mockMvc.perform(delete("/api/v1/beverages/" + id)
                        .header("Authorization", managerToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldRejectUpdateWithoutManagerRole() throws Exception {
        BeverageDto dto = new BeverageDto();
        dto.setName("Refill");
        dto.setDescription("Refreshed");
        dto.setPrice(new BigDecimal("1.10"));

        mockMvc.perform(put("/api/v1/beverages/" + java.util.UUID.randomUUID())
                        .header("Authorization", customerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(dto)))
                .andExpect(status().isForbidden());
    }
}
