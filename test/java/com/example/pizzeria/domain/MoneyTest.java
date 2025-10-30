package com.example.pizzeria.domain;

import com.example.pizzeria.domain.value.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @Test
    void shouldNormalizeScale() {
        Money money = Money.of(new BigDecimal("5"));

        assertThat(money.getAmount()).isEqualTo(new BigDecimal("5.00"));
    }

    @Test
    void shouldRejectNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> Money.of(new BigDecimal("-0.01")));
    }

    @Test
    void shouldAddAmounts() {
        Money left = Money.of(new BigDecimal("2.50"));
        Money right = Money.of(new BigDecimal("3.20"));

        Money sum = left.add(right);

        assertThat(sum.getAmount()).isEqualTo(new BigDecimal("5.70"));
    }

    @Test
    void shouldMultiplyByQuantity() {
        Money unit = Money.of(new BigDecimal("1.11"));

        Money total = unit.multiply(7);

        assertThat(total.getAmount()).isEqualTo(new BigDecimal("7.77"));
    }

    @Test
    void shouldRejectZeroQuantityOnMultiply() {
        Money unit = Money.of(new BigDecimal("1.00"));

        assertThrows(IllegalArgumentException.class, () -> unit.multiply(0));
    }
}
