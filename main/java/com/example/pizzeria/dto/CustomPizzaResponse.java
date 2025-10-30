package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Representation of a saved custom pizza")
public class CustomPizzaResponse {

    @Schema(description = "Identifier of the custom pizza", format = "uuid", example = "5b45ab26-5efe-44bc-8866-3b0b3cb7709a", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Custom pizza name", example = "Spicy Garden Special", accessMode = Schema.AccessMode.READ_ONLY)
    private String name;

    @Schema(description = "Identifier of the crust used", format = "uuid", example = "f3e1a49e-7b1b-45a5-92ab-2ab7b9de3e1b", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID baseId;

    @Schema(description = "Total calculated price", type = "number", format = "double", example = "18.45", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal price;

    @ArraySchema(arraySchema = @Schema(description = "Ingredient identifiers included in the custom pizza", example = "[\"9f568c1c-0df5-4a53-8dfb-3f3e8370a0ab\", \"2dddad63-4023-46f7-9d05-3f2fb5f5c9fb\"]", accessMode = Schema.AccessMode.READ_ONLY), schema = @Schema(format = "uuid"))
    private Set<UUID> ingredientIds;

    @Schema(description = "Template pizza the custom pizza originated from, if any", format = "uuid", example = "c9d7d938-37e2-4706-af39-0b4b5a623d3c", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID templateId;

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

    public UUID getTemplateId() {
        return templateId;
    }

    public void setTemplateId(UUID templateId) {
        this.templateId = templateId;
    }
}
