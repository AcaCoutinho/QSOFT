package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.OrderItem;
import com.example.pizzeria.domain.entity.ProductEntity;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    void shouldCalculateSubtotalFromProductPrice() {
        OrderItem item = new OrderItem();
        item.setProduct(new FakeProduct("Water", new BigDecimal("1.20")));
        item.setQuantity(3);
        item.calculateSubtotal();

        assertThat(item.getSubtotal().getAmount()).isEqualTo(new BigDecimal("3.60"));
    }

    @Test
    void shouldResetSubtotalForInvalidQuantity() {
        OrderItem item = new OrderItem();
        item.setProduct(new FakeProduct("Juice", new BigDecimal("2.00")));
        item.setQuantity(0);
        item.calculateSubtotal();

        assertThat(item.getSubtotal().getAmount()).isEqualTo(BigDecimal.ZERO.setScale(2));
    }

    private static class FakeProduct extends ProductEntity {
        FakeProduct(String name, BigDecimal amount) {
            setName(Name.of(name));
            setPrice(Money.of(amount));
        }
    }
}
