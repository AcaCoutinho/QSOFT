package com.example.pizzeria.mapper;

import com.example.pizzeria.domain.entity.Pizza;
import com.example.pizzeria.dto.PizzaDto;

import java.util.stream.Collectors;

public final class PizzaMapper {

    private PizzaMapper() {
    }

    public static PizzaDto toDto(Pizza pizza) {
        PizzaDto dto = new PizzaDto();
        dto.setId(pizza.getPublicId());
        dto.setName(pizza.getName().getValue());
        dto.setPrice(pizza.getPrice().getAmount());
        dto.setBaseId(pizza.getBase().getPublicId());
        dto.setBaseName(pizza.getBase().getName().getValue());
        dto.setIngredientIds(pizza.getDefaultIngredients().stream()
                .map(ingredient -> ingredient.getPublicId())
                .collect(Collectors.toSet()));
        return dto;
    }
}
