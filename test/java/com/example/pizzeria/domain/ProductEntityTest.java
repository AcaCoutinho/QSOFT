package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.ProductEntity;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductEntityTest {

    @Test
    void shouldRequireValueObjects() {
        DemoProduct product = new DemoProduct();
        product.setName(Name.of("Sample"));
        product.setPrice(Money.of(new BigDecimal("4.00")));

        assertThat(product.getName().getValue()).isEqualTo("Sample");
        assertThat(product.getPrice().getAmount()).isEqualTo(new BigDecimal("4.00"));
    }

    @Test
    void shouldRejectNullValues() {
        DemoProduct product = new DemoProduct();

        assertThrows(NullPointerException.class, () -> product.setName(null));
        assertThrows(NullPointerException.class, () -> product.setPrice(null));
    }

    private static class DemoProduct extends ProductEntity {
    }
}
