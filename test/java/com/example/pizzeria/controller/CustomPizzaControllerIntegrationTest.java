package com.example.pizzeria.controller;

import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.repository.IngredientRepository;
import com.example.pizzeria.repository.PizzaBaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomPizzaControllerIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private PizzaBaseRepository pizzaBaseRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void shouldCreateCustomPizzaForCustomer() throws Exception {
        UUID baseId = pizzaBaseRepository.findAll().get(0).getPublicId();
        List<Ingredient> ingredients = ingredientRepository.findAll();
        UUID ingredientId = ingredients.get(0).getPublicId();

        var request = new CustomPayload("Freestyle", baseId, Set.of(ingredientId), null);

        String response = mockMvc.perform(post("/api/v1/custom-pizzas")
                        .header("Authorization", customerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Freestyle"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(get("/api/v1/custom-pizzas/" + id)
                        .header("Authorization", customerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(delete("/api/v1/custom-pizzas/" + id)
                        .header("Authorization", managerToken()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectCreationWithoutRole() throws Exception {
        var request = new CustomPayload("Anon", UUID.randomUUID(), Set.of(), null);

        mockMvc.perform(post("/api/v1/custom-pizzas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isUnauthorized());
    }

    private record CustomPayload(String name, UUID baseId, Set<UUID> ingredientIds, UUID templateId) {
    }
}
