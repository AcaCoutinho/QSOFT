package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Payload for updating the status of an order")
public class OrderStatusUpdateRequest {

    @NotNull
    @Schema(description = "New status for the order", example = "PREPARING", allowableValues = {"PLACED", "PREPARING", "BAKING", "OUT_FOR_DELIVERY", "DELIVERED"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
