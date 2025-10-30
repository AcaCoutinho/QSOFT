package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Pizza base/crust definition")
public class PizzaBaseDto {

    @Schema(description = "Public identifier for the base", format = "uuid", example = "c29a0682-1e59-495a-8b3f-5dfb8a13d1fd", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank
    @Schema(description = "Base name", example = "Thin Crust", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank
    @Schema(description = "Description shown to customers", example = "Crispy thin crust baked with olive oil", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @DecimalMin("0.0")
    @Schema(description = "Price contribution for selecting this base", type = "number", format = "double", example = "3.50", minimum = "0.0")
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
