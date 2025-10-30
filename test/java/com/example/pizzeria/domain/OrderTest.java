package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.Order;
import com.example.pizzeria.domain.entity.OrderItem;
import com.example.pizzeria.domain.entity.ProductEntity;
import com.example.pizzeria.domain.enums.OrderStatus;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderTest {

    @Test
    void shouldAccumulateTotalsFromRealItems() {
        OrderItem first = new OrderItem();
        first.setProduct(new TestProduct("First", new BigDecimal("2.00")));
        first.setQuantity(2);
        first.calculateSubtotal();

        OrderItem second = new OrderItem();
        second.setProduct(new TestProduct("Second", new BigDecimal("3.50")));
        second.setQuantity(1);
        second.calculateSubtotal();

        Order order = new Order();
        order.addItem(first);
        order.addItem(second);

        assertThat(order.getTotalPrice().getAmount()).isEqualTo(new BigDecimal("7.50"));
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PLACED);
    }

    @Test
    void shouldRecalculateTotalUsingStubbedItems() {
        OrderItem first = mock(OrderItem.class);
        OrderItem second = mock(OrderItem.class);
        when(first.getSubtotal()).thenReturn(Money.of(new BigDecimal("4.00")));
        when(second.getSubtotal()).thenReturn(Money.of(new BigDecimal("5.25")));

        Order order = new Order();
        order.setItems(java.util.List.of(first, second));

        assertThat(order.getTotalPrice().getAmount()).isEqualTo(new BigDecimal("9.25"));
        verify(first).setOrder(order);
        verify(second).setOrder(order);
    }

    private static class TestProduct extends ProductEntity {
        TestProduct(String name, BigDecimal price) {
            setName(Name.of(name));
            setPrice(Money.of(price));
        }
    }
}
