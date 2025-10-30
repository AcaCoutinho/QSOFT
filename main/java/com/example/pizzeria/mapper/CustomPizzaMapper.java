package com.example.pizzeria.mapper;

import com.example.pizzeria.domain.entity.CustomPizza;
import com.example.pizzeria.dto.CustomPizzaResponse;

import java.util.stream.Collectors;

public final class CustomPizzaMapper {

    private CustomPizzaMapper() {
    }

    public static CustomPizzaResponse toDto(CustomPizza pizza) {
        CustomPizzaResponse dto = new CustomPizzaResponse();
        dto.setId(pizza.getPublicId());
        dto.setName(pizza.getName().getValue());
        dto.setPrice(pizza.getPrice().getAmount());
        dto.setBaseId(pizza.getBase().getPublicId());
        dto.setTemplateId(pizza.getTemplate() != null ? pizza.getTemplate().getPublicId() : null);
        dto.setIngredientIds(pizza.getCustomIngredients().stream()
                .map(ingredient -> ingredient.getPublicId())
                .collect(Collectors.toSet()));
        return dto;
    }
}
