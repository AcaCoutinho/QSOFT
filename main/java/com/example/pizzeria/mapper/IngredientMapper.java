package com.example.pizzeria.mapper;

import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.domain.enums.IngredientType;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import com.example.pizzeria.dto.IngredientDto;

public final class IngredientMapper {

    private IngredientMapper() {
    }

    public static IngredientDto toDto(Ingredient ingredient) {
        IngredientDto dto = new IngredientDto();
    dto.setId(ingredient.getPublicId());
        dto.setName(ingredient.getName().getValue());
        dto.setType(ingredient.getType().name());
        dto.setPrice(ingredient.getPrice().getAmount());
        return dto;
    }

    public static void updateEntity(IngredientDto dto, Ingredient ingredient) {
        ingredient.setName(Name.of(dto.getName()));
        ingredient.setPrice(Money.of(dto.getPrice()));
        ingredient.setType(IngredientType.valueOf(dto.getType()));
    }
}
