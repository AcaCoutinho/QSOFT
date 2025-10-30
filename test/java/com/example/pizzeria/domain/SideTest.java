package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.Side;
import com.example.pizzeria.domain.value.Description;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class SideTest {

    @Test
    void shouldHoldDescriptionAndPrice() {
        Side side = new Side();
        side.setName(Name.of("Bread"));
        side.setDescription(Description.of("Garlic"));
        side.setPrice(Money.of(new BigDecimal("3.50")));

        assertThat(side.getDescription().getValue()).isEqualTo("Garlic");
        assertThat(side.getPrice().getAmount()).isEqualTo(new BigDecimal("3.50"));
    }
}
