package com.example.pizzeria.controller;

import com.example.pizzeria.domain.enums.ProductType;
import com.example.pizzeria.dto.OrderItemRequest;
import com.example.pizzeria.dto.OrderRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String customerToken;
    private String managerToken;

    @BeforeEach
    void setUp() throws Exception {
        customerToken = obtainToken("customer", "Password123!");
        managerToken = obtainToken("manager", "Password123!");
    }

    @Test
    void customerPlacesOrderAndManagerAdvancesStatus() throws Exception {
    UUID pizzaId = fetchFirstPizzaId();

    OrderItemRequest item = new OrderItemRequest();
    item.setProductType(ProductType.PIZZA.name());
    item.setProductId(pizzaId);
        item.setQuantity(1);

        OrderRequest request = new OrderRequest();
        request.setItems(List.of(item));

        String response = mockMvc.perform(post("/api/v1/orders")
                        .header("Authorization", "Bearer " + customerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(response);
    String orderNumber = node.get("orderNumber").asText();
    UUID orderPublicId = UUID.fromString(node.get("id").asText());
        assertThat(orderNumber).startsWith("ORD-");
        assertThat(node.get("status").asText()).isEqualTo("PLACED");

        String myOrdersResponse = mockMvc.perform(get("/api/v1/orders/me")
                        .header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(myOrdersResponse).contains(orderNumber);

    String advanceResponse = mockMvc.perform(post("/api/v1/orders/" + orderPublicId + "/advance")
                        .header("Authorization", "Bearer " + managerToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode advancedOrder = objectMapper.readTree(advanceResponse);
        assertThat(advancedOrder.get("status").asText()).isEqualTo("PREPARING");
    }

    private String obtainToken(String username, String password) throws Exception {
        String requestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        String response = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(response);
        return node.get("token").asText();
    }

    private UUID fetchFirstPizzaId() throws Exception {
        String menuResponse = mockMvc.perform(get("/api/v1/menu"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode menu = objectMapper.readTree(menuResponse);
        return UUID.fromString(menu.get("pizzas").get(0).get("id").asText());
    }
}
