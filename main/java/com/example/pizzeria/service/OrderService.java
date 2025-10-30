package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.Order;
import com.example.pizzeria.domain.entity.OrderItem;
import com.example.pizzeria.domain.entity.ProductEntity;
import com.example.pizzeria.domain.entity.User;
import com.example.pizzeria.domain.enums.OrderStatus;
import com.example.pizzeria.domain.enums.ProductType;
import com.example.pizzeria.dto.OrderRequest;
import com.example.pizzeria.dto.OrderResponse;
import com.example.pizzeria.dto.OrderStatusUpdateRequest;
import com.example.pizzeria.exception.ForbiddenOperationException;
import com.example.pizzeria.exception.InvalidOrderStateException;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.mapper.OrderMapper;
import com.example.pizzeria.repository.OrderRepository;
import com.example.pizzeria.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductLookupService productLookupService;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ProductLookupService productLookupService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productLookupService = productLookupService;
    }

    public OrderResponse placeOrder(String username, OrderRequest request) {
        User customer = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        Order order = new Order();
        order.setPublicId(UUID.randomUUID());
        order.setOrderNumber(generateOrderNumber());
        order.setCustomer(customer);
        order.setOrderDateTime(LocalDateTime.now());

        request.getItems().forEach(itemRequest -> {
            ProductType type = ProductType.valueOf(itemRequest.getProductType());
            ProductEntity product = productLookupService.findProduct(type, itemRequest.getProductId());
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.calculateSubtotal();
            order.addItem(item);
        });

        order.recalculateTotal();
        Order saved = orderRepository.save(order);
        log.info("Order {} placed by {}", saved.getOrderNumber(), username);
        return OrderMapper.toDto(saved);
    }

    public List<OrderResponse> getOrdersForCustomer(String username) {
        return OrderMapper.toDtos(orderRepository.findByCustomerUsername(username));
    }

    public List<OrderResponse> getAllOrders() {
        return OrderMapper.toDtos(orderRepository.findAll());
    }

    public OrderResponse getOrderForUser(UUID publicId, String username, boolean isManager) {
        Order order = orderRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + publicId));
        if (!isManager && !order.getCustomer().getUsername().equals(username)) {
            throw new ForbiddenOperationException("You are not allowed to view this order");
        }
        return OrderMapper.toDto(order);
    }

    public OrderResponse updateStatus(UUID publicId, OrderStatusUpdateRequest request) {
        Order order = orderRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + publicId));
        OrderStatus targetStatus = OrderStatus.valueOf(request.getStatus());
        validateTransition(order.getStatus(), targetStatus);
        order.setStatus(targetStatus);
        Order saved = orderRepository.save(order);
        log.info("Order {} status updated to {}", order.getOrderNumber(), targetStatus);
        return OrderMapper.toDto(saved);
    }

    public OrderResponse advanceStatus(UUID publicId) {
        Order order = orderRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + publicId));
        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new InvalidOrderStateException("Order already delivered");
        }
        OrderStatus next = order.getStatus().next();
        order.setStatus(next);
        Order saved = orderRepository.save(order);
        log.info("Order {} advanced to {}", order.getOrderNumber(), next);
        return OrderMapper.toDto(saved);
    }

    private void validateTransition(OrderStatus current, OrderStatus target) {
        if (current == target) {
            return;
        }
        OrderStatus expectedNext = current.next();
        if (target != expectedNext) {
            throw new InvalidOrderStateException(
                    "Invalid status transition from " + current + " to " + target + ". Expected " + expectedNext);
        }
    }

    private String generateOrderNumber() {
        String candidate;
        do {
            candidate = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (orderRepository.existsByOrderNumber(candidate));
        return candidate;
    }
}
