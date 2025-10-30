package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

@Schema(description = "Payload for creating or updating a catalog pizza")
public class PizzaRequest {

    @NotBlank
    @Schema(description = "Display name", example = "Pepperoni", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @Schema(description = "Identifier of the base crust", format = "uuid", example = "c29a0682-1e59-495a-8b3f-5dfb8a13d1fd", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID baseId;

    @ArraySchema(arraySchema = @Schema(description = "Optional ingredient identifiers to include", example = "[\"9f568c1c-0df5-4a53-8dfb-3f3e8370a0ab\", \"2dddad63-4023-46f7-9d05-3f2fb5f5c9fb\"]"), schema = @Schema(format = "uuid"))
    private Set<UUID> ingredientIds;

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

    public Set<UUID> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(Set<UUID> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }
}
