package com.example.pizzeria.mapper;

import com.example.pizzeria.domain.entity.Beverage;
import com.example.pizzeria.domain.value.Description;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import com.example.pizzeria.dto.BeverageDto;

public final class BeverageMapper {

    private BeverageMapper() {
    }

    public static BeverageDto toDto(Beverage beverage) {
        BeverageDto dto = new BeverageDto();
        dto.setId(beverage.getPublicId());
        dto.setName(beverage.getName().getValue());
        dto.setDescription(beverage.getDescription().getValue());
        dto.setPrice(beverage.getPrice().getAmount());
        return dto;
    }

    public static void updateEntity(BeverageDto dto, Beverage beverage) {
        beverage.setName(Name.of(dto.getName()));
        beverage.setDescription(Description.of(dto.getDescription()));
        beverage.setPrice(Money.of(dto.getPrice()));
    }
}
