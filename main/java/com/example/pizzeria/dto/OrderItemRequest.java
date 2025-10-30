package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Line item definition used when placing orders")
public class OrderItemRequest {

    @NotNull
    @Schema(description = "Type of product being added", example = "PIZZA", allowableValues = {"PIZZA", "CUSTOM_PIZZA", "SIDE", "BEVERAGE"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String productType;

    @NotNull
    @Schema(description = "Identifier of the product being ordered", format = "uuid", example = "a0c8aeab-c68f-44de-a2b2-1bb199f20df1", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID productId;

    @Min(1)
    @Schema(description = "Quantity of the product", minimum = "1", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private int quantity;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
