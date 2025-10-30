package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Order resource returned from the API")
public class OrderResponse {

    @Schema(description = "Public identifier for the order", format = "uuid", example = "2d4b8e7d-3bdb-4c39-87cf-0c4097328d6f", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Human-friendly order number", example = "ORD-26A8434F", accessMode = Schema.AccessMode.READ_ONLY)
    private String orderNumber;

    @Schema(description = "Current status of the order", example = "PREPARING", allowableValues = {"PLACED", "PREPARING", "BAKING", "OUT_FOR_DELIVERY", "DELIVERED"}, accessMode = Schema.AccessMode.READ_ONLY)
    private String status;

    @Schema(description = "Total price for the order", type = "number", format = "double", example = "42.75", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal totalPrice;

    @Schema(description = "Timestamp when the order was created", type = "string", format = "date-time", example = "2024-05-12T18:34:12.345Z", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime orderDateTime;

    @ArraySchema(arraySchema = @Schema(description = "Line items associated with the order", accessMode = Schema.AccessMode.READ_ONLY), schema = @Schema(implementation = OrderItemResponse.class))
    private List<OrderItemResponse> items;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
