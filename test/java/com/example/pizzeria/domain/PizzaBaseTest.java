package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.PizzaBase;
import com.example.pizzeria.domain.value.Description;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PizzaBaseTest {

    @Test
    void shouldStoreValueObjects() {
        PizzaBase base = new PizzaBase();
        base.setName(Name.of("Classic"));
        base.setDescription(Description.of("Hand tossed"));
        base.setPrice(Money.of(new BigDecimal("6.00")));

        assertThat(base.getName().getValue()).isEqualTo("Classic");
        assertThat(base.getDescription().getValue()).isEqualTo("Hand tossed");
        assertThat(base.getPrice().getAmount()).isEqualTo(new BigDecimal("6.00"));
    }
}
