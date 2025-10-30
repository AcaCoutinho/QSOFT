package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Ingredient payload used for both responses and create/update requests")
public class IngredientDto {

    @Schema(description = "Public identifier exposed via the API", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank
    @Schema(description = "Ingredient display name", example = "Smoked Gouda")
    private String name;

    @NotNull
    @Schema(description = "Ingredient category", example = "CHEESE", allowableValues = {"CHEESE", "MEAT", "VEGETABLE", "SAUCE"})
    private String type;

    @NotNull
    @DecimalMin("0.0")
    @Schema(description = "Unit price in the default currency", type = "number", format = "double", example = "2.50")
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
