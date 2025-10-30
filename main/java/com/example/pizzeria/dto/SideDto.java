package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Side dish available for ordering")
public class SideDto {

    @Schema(description = "Public identifier for the side", format = "uuid", example = "5fe52f57-4832-4d5e-a0ab-9cb6f76abc48", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank
    @Schema(description = "Display name", example = "Garlic Bread", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank
    @Schema(description = "Menu description", example = "Toasted baguette with garlic butter", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @DecimalMin("0.0")
    @Schema(description = "Price for the side", type = "number", format = "double", example = "4.50", minimum = "0.0")
    private BigDecimal price;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
