package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Representation of a beverage available on the menu")
public class BeverageDto {

    @Schema(description = "Public identifier for the beverage", format = "uuid", accessMode = Schema.AccessMode.READ_ONLY, example = "2c6b4c68-25d4-4bd1-9f42-a9a6dfa4c901")
    private UUID id;

    @NotBlank
    @Schema(description = "Display name", example = "Lemonade", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank
    @Schema(description = "Short marketing description", example = "Freshly squeezed lemons over ice", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @DecimalMin("0.0")
    @Schema(description = "Unit price in the default currency", type = "number", format = "double", example = "2.99", minimum = "0.0")
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
