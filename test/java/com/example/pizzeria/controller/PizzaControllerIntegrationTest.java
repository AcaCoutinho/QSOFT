package com.example.pizzeria.controller;

import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.domain.entity.PizzaBase;
import com.example.pizzeria.repository.IngredientRepository;
import com.example.pizzeria.repository.PizzaBaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PizzaControllerIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private PizzaBaseRepository pizzaBaseRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void shouldListPizzasForAuthenticatedUsers() throws Exception {
        mockMvc.perform(get("/api/v1/pizzas")
                        .header("Authorization", customerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").isNotEmpty());
    }

    @Test
    void shouldRejectCreationForCustomer() throws Exception {
        PizzaBase base = pizzaBaseRepository.findAll().get(0);
        Ingredient ingredient = ingredientRepository.findAll().get(0);

        var request = new PizzaPayload("Customer Special", base.getPublicId(), Set.of(ingredient.getPublicId()));

        mockMvc.perform(post("/api/v1/pizzas")
                        .header("Authorization", customerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldCreatePizzaForManager() throws Exception {
        List<PizzaBase> bases = pizzaBaseRepository.findAll();
        List<Ingredient> ingredients = ingredientRepository.findAll();
        assertThat(bases).isNotEmpty();
        assertThat(ingredients).isNotEmpty();

        var request = new PizzaPayload("Manager Delight",
                bases.get(0).getPublicId(),
                Set.of(ingredients.get(0).getPublicId()));

        mockMvc.perform(post("/api/v1/pizzas")
                        .header("Authorization", managerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Manager Delight"));
    }

    private record PizzaPayload(String name, UUID baseId, Set<UUID> ingredientIds) {
    }
}
