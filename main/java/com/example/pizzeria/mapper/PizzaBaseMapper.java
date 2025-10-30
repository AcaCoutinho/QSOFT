package com.example.pizzeria.mapper;

import com.example.pizzeria.domain.entity.PizzaBase;
import com.example.pizzeria.domain.value.Description;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import com.example.pizzeria.dto.PizzaBaseDto;

public final class PizzaBaseMapper {

    private PizzaBaseMapper() {
    }

    public static PizzaBaseDto toDto(PizzaBase base) {
        PizzaBaseDto dto = new PizzaBaseDto();
    dto.setId(base.getPublicId());
        dto.setName(base.getName().getValue());
        dto.setDescription(base.getDescription().getValue());
        dto.setPrice(base.getPrice().getAmount());
        return dto;
    }

    public static void updateEntity(PizzaBaseDto dto, PizzaBase base) {
        base.setName(Name.of(dto.getName()));
        base.setDescription(Description.of(dto.getDescription()));
        base.setPrice(Money.of(dto.getPrice()));
    }
}
