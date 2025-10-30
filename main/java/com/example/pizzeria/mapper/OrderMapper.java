package com.example.pizzeria.mapper;

import com.example.pizzeria.domain.entity.Order;
import com.example.pizzeria.domain.entity.OrderItem;
import com.example.pizzeria.domain.entity.ProductEntity;
import com.example.pizzeria.dto.OrderItemResponse;
import com.example.pizzeria.dto.OrderResponse;

import java.util.List;

public final class OrderMapper {

    private OrderMapper() {
    }

    public static OrderResponse toDto(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getPublicId());
        response.setOrderNumber(order.getOrderNumber());
        response.setStatus(order.getStatus().name());
    response.setTotalPrice(order.getTotalPrice().getAmount());
        response.setOrderDateTime(order.getOrderDateTime());
        response.setItems(order.getItems().stream()
                .map(OrderMapper::toDto)
                .toList());
        return response;
    }

    private static OrderItemResponse toDto(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        ProductEntity product = item.getProduct();
        dto.setProductId(product.getPublicId());
        dto.setProductName(product.getName().getValue());
        dto.setProductType(product.getClass().getSimpleName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(product.getPrice().getAmount());
        dto.setSubtotal(item.getSubtotal().getAmount());
        return dto;
    }

    public static List<OrderResponse> toDtos(List<Order> orders) {
        return orders.stream().map(OrderMapper::toDto).toList();
    }
}
