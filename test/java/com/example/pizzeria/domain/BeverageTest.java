package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.Beverage;
import com.example.pizzeria.domain.value.Description;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BeverageTest {

    @Test
    void shouldExposeDescription() {
        Beverage beverage = new Beverage();
        beverage.setName(Name.of("Cola"));
        beverage.setDescription(Description.of("Chilled"));
        beverage.setPrice(Money.of(new BigDecimal("2.50")));

        assertThat(beverage.getDescription().getValue()).isEqualTo("Chilled");
        assertThat(beverage.getPrice().getAmount()).isEqualTo(new BigDecimal("2.50"));
    }
}
