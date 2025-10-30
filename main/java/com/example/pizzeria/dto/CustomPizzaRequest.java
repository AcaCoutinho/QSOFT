package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

@Schema(description = "Request body for crafting a custom pizza")
public class CustomPizzaRequest {

    @NotBlank
    @Schema(description = "Custom pizza name", example = "Spicy Garden Special", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @Schema(description = "Identifier of the base crust", format = "uuid", example = "f3e1a49e-7b1b-45a5-92ab-2ab7b9de3e1b", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID baseId;

    @Schema(description = "Optional template pizza to clone before customization", format = "uuid", example = "c9d7d938-37e2-4706-af39-0b4b5a623d3c")
    private UUID templateId;

    @ArraySchema(arraySchema = @Schema(description = "Optional list of ingredient IDs to include", example = "[\"9f568c1c-0df5-4a53-8dfb-3f3e8370a0ab\", \"2dddad63-4023-46f7-9d05-3f2fb5f5c9fb\"]"), schema = @Schema(format = "uuid"))
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

    public UUID getTemplateId() {
        return templateId;
    }

    public void setTemplateId(UUID templateId) {
        this.templateId = templateId;
    }

    public Set<UUID> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(Set<UUID> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }
}
