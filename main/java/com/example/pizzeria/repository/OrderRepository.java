package com.example.pizzeria.repository;

import com.example.pizzeria.domain.entity.Order;
import com.example.pizzeria.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    Optional<Order> findByPublicId(UUID publicId);
    List<Order> findByCustomerUsername(String username);
    boolean existsByOrderNumber(String orderNumber);
    long countByStatus(OrderStatus status);
}
