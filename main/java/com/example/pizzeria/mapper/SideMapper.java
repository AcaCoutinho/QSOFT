package com.example.pizzeria.mapper;

import com.example.pizzeria.domain.entity.Side;
import com.example.pizzeria.domain.value.Description;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import com.example.pizzeria.dto.SideDto;

public final class SideMapper {

    private SideMapper() {
    }

    public static SideDto toDto(Side side) {
        SideDto dto = new SideDto();
        dto.setId(side.getPublicId());
        dto.setName(side.getName().getValue());
        dto.setDescription(side.getDescription().getValue());
        dto.setPrice(side.getPrice().getAmount());
        return dto;
    }

    public static void updateEntity(SideDto dto, Side side) {
        side.setName(Name.of(dto.getName()));
        side.setDescription(Description.of(dto.getDescription()));
        side.setPrice(Money.of(dto.getPrice()));
    }
}
