package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.Order;
import com.example.pizzeria.domain.entity.ProductEntity;
import com.example.pizzeria.domain.entity.User;
import com.example.pizzeria.domain.enums.OrderStatus;
import com.example.pizzeria.domain.enums.ProductType;
import com.example.pizzeria.dto.OrderItemRequest;
import com.example.pizzeria.dto.OrderRequest;
import com.example.pizzeria.dto.OrderStatusUpdateRequest;
import com.example.pizzeria.exception.InvalidOrderStateException;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.repository.OrderRepository;
import com.example.pizzeria.repository.UserRepository;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductLookupService productLookupService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        productLookupService = mock(ProductLookupService.class);
        orderService = new OrderService(orderRepository, userRepository, productLookupService);
    }

    @Test
    void placesOrderAndCalculatesTotals() {
        User user = new User();
        user.setUsername("customer");
        when(userRepository.findByUsername("customer")).thenReturn(Optional.of(user));

        UUID productId = UUID.randomUUID();

        ProductEntity pizza = mock(ProductEntity.class);
        when(pizza.getPublicId()).thenReturn(productId);
        when(pizza.getName()).thenReturn(Name.of("Margherita"));
        when(pizza.getPrice()).thenReturn(Money.of(new BigDecimal("8.30")));
        when(productLookupService.findProduct(ProductType.PIZZA, productId)).thenReturn(pizza);

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setProductType(ProductType.PIZZA.name());
        itemRequest.setProductId(productId);
        itemRequest.setQuantity(2);

        OrderRequest request = new OrderRequest();
        request.setItems(List.of(itemRequest));

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        orderService.placeOrder("customer", request);

        verify(orderRepository).save(orderCaptor.capture());
        Order saved = orderCaptor.getValue();
        assertThat(saved.getTotalPrice()).isEqualTo(Money.of(new BigDecimal("16.60")));
        assertThat(saved.getItems()).hasSize(1);
        assertThat(saved.getItems().get(0).getSubtotal()).isEqualTo(Money.of(new BigDecimal("16.60")));
    }

    @Test
    void preventsInvalidStatusTransition() {
        UUID orderPublicId = UUID.randomUUID();
        Order order = new Order();
        order.setPublicId(orderPublicId);
        order.setStatus(OrderStatus.PLACED);
        order.setOrderNumber("ORD-1");
        when(orderRepository.findByPublicId(orderPublicId)).thenReturn(Optional.of(order));

        OrderStatusUpdateRequest request = new OrderStatusUpdateRequest();
        request.setStatus(OrderStatus.BAKING.name());

        assertThatThrownBy(() -> orderService.updateStatus(orderPublicId, request))
                .isInstanceOf(InvalidOrderStateException.class)
                .hasMessageContaining("Invalid status transition");
    }

    @Test
    void throwsWhenOrderNotFoundForStatusUpdate() {
        UUID missingId = UUID.randomUUID();
        when(orderRepository.findByPublicId(missingId)).thenReturn(Optional.empty());

        OrderStatusUpdateRequest request = new OrderStatusUpdateRequest();
        request.setStatus(OrderStatus.PREPARING.name());

        assertThatThrownBy(() -> orderService.updateStatus(missingId, request))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
