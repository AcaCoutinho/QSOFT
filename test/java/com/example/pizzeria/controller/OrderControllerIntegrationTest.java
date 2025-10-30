package com.example.pizzeria.controller;

import com.example.pizzeria.domain.entity.Pizza;
import com.example.pizzeria.domain.enums.ProductType;
import com.example.pizzeria.repository.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Test
    void shouldPlaceOrderForCustomer() throws Exception {
        List<Pizza> pizzas = pizzaRepository.findAll();
        UUID productId = pizzas.get(0).getPublicId();

        var request = new OrderPayload(List.of(new ItemPayload(ProductType.PIZZA.name(), productId, 2)));

        String response = mockMvc.perform(post("/api/v1/orders")
                        .header("Authorization", customerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String orderId = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(get("/api/v1/orders/" + orderId)
                        .header("Authorization", customerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId));

        mockMvc.perform(get("/api/v1/orders" )
                        .header("Authorization", managerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderNumber").isNotEmpty());
    }

    @Test
    void shouldRejectInvalidQuantity() throws Exception {
        List<Pizza> pizzas = pizzaRepository.findAll();
        UUID productId = pizzas.get(0).getPublicId();

        var request = new OrderPayload(List.of(new ItemPayload(ProductType.PIZZA.name(), productId, 0)));

        mockMvc.perform(post("/api/v1/orders")
                        .header("Authorization", customerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRequireManagerForStatusUpdate() throws Exception {
        List<Pizza> pizzas = pizzaRepository.findAll();
        UUID productId = pizzas.get(0).getPublicId();
        var request = new OrderPayload(List.of(new ItemPayload(ProductType.PIZZA.name(), productId, 1)));

        String response = mockMvc.perform(post("/api/v1/orders")
                        .header("Authorization", customerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String orderId = objectMapper.readTree(response).get("id").asText();

        var update = new StatusPayload("PREPARING");

        mockMvc.perform(put("/api/v1/orders/" + orderId + "/status")
                        .header("Authorization", managerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PREPARING"));
    }

    private record OrderPayload(List<ItemPayload> items) {
    }

    private record ItemPayload(String productType, UUID productId, int quantity) {
    }

    private record StatusPayload(String status) {
    }
}
