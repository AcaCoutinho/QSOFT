package com.example.pizzeria.domain;

import com.example.pizzeria.domain.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusTest {

    @Test
    void shouldAdvanceUntilDelivered() {
        assertThat(OrderStatus.PLACED.next()).isEqualTo(OrderStatus.PREPARING);
        assertThat(OrderStatus.PREPARING.next()).isEqualTo(OrderStatus.BAKING);
        assertThat(OrderStatus.BAKING.next()).isEqualTo(OrderStatus.OUT_FOR_DELIVERY);
        assertThat(OrderStatus.OUT_FOR_DELIVERY.next()).isEqualTo(OrderStatus.DELIVERED);
        assertThat(OrderStatus.DELIVERED.next()).isEqualTo(OrderStatus.DELIVERED);
    }
}
