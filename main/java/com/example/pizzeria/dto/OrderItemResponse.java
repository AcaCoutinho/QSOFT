package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Line item returned as part of order responses")
public class OrderItemResponse {

    @Schema(description = "Identifier of the product", format = "uuid", example = "a0c8aeab-c68f-44de-a2b2-1bb199f20df1", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID productId;

    @Schema(description = "Display name of the product", example = "Pepperoni Pizza", accessMode = Schema.AccessMode.READ_ONLY)
    private String productName;

    @Schema(description = "Product category", example = "PIZZA", allowableValues = {"PIZZA", "CUSTOM_PIZZA", "SIDE", "BEVERAGE"}, accessMode = Schema.AccessMode.READ_ONLY)
    private String productType;

    @Schema(description = "Quantity purchased", example = "2", accessMode = Schema.AccessMode.READ_ONLY)
    private int quantity;

    @Schema(description = "Unit price charged", type = "number", format = "double", example = "14.25", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal unitPrice;

    @Schema(description = "Subtotal for this line", type = "number", format = "double", example = "28.50", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal subtotal;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
