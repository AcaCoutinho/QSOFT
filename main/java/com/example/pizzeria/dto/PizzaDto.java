package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Catalog pizza definition exposed via the API")
public class PizzaDto {

    @Schema(description = "Public identifier of the pizza", format = "uuid", example = "00bd5aff-daa1-4fef-a2d7-2fbbaf6ddffe", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Display name", example = "Pepperoni", accessMode = Schema.AccessMode.READ_ONLY)
    private String name;

    @Schema(description = "Identifier of the base crust", format = "uuid", example = "c29a0682-1e59-495a-8b3f-5dfb8a13d1fd", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID baseId;

    @Schema(description = "Friendly name of the base crust", example = "Thin Crust", accessMode = Schema.AccessMode.READ_ONLY)
    private String baseName;

    @Schema(description = "Menu price", type = "number", format = "double", example = "14.25", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal price;

    @ArraySchema(arraySchema = @Schema(description = "Ingredient identifiers present on the pizza", accessMode = Schema.AccessMode.READ_ONLY, example = "[\"9f568c1c-0df5-4a53-8dfb-3f3e8370a0ab\", \"2dddad63-4023-46f7-9d05-3f2fb5f5c9fb\"]"), schema = @Schema(format = "uuid"))
    private Set<UUID> ingredientIds;

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

    public UUID getBaseId() {
        return baseId;
    }

    public void setBaseId(UUID baseId) {
        this.baseId = baseId;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<UUID> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(Set<UUID> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }
}
