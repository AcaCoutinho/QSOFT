package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Order placement payload")
public class OrderRequest {

    @NotEmpty
    @Valid
    @ArraySchema(arraySchema = @Schema(description = "At least one line item is required", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(implementation = OrderItemRequest.class))
    private List<OrderItemRequest> items;

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
