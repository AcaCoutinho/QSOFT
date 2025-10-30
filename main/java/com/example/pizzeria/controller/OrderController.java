package com.example.pizzeria.controller;

import com.example.pizzeria.dto.OrderRequest;
import com.example.pizzeria.dto.OrderResponse;
import com.example.pizzeria.dto.OrderStatusUpdateRequest;
import com.example.pizzeria.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Orders")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Place a new order")
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public ResponseEntity<OrderResponse> placeOrder(Authentication authentication,
                                                    @Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(authentication.getName(), request));
    }

    @Operation(summary = "List orders for current user")
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public List<OrderResponse> myOrders(Authentication authentication) {
        return orderService.getOrdersForCustomer(authentication.getName());
    }

    @Operation(summary = "List all orders")
    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public List<OrderResponse> allOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Get order details")
    @GetMapping("/{publicId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public OrderResponse getOrder(@PathVariable UUID publicId, Authentication authentication) {
        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
        return orderService.getOrderForUser(publicId, authentication.getName(), isManager);
    }

    @Operation(summary = "Update order status")
    @PutMapping("/{publicId}/status")
    @PreAuthorize("hasRole('MANAGER')")
    public OrderResponse updateStatus(@PathVariable UUID publicId,
                                      @Valid @RequestBody OrderStatusUpdateRequest request) {
        return orderService.updateStatus(publicId, request);
    }

    @Operation(summary = "Advance order status to next state")
    @PostMapping("/{publicId}/advance")
    @PreAuthorize("hasRole('MANAGER')")
    public OrderResponse advance(@PathVariable UUID publicId) {
        return orderService.advanceStatus(publicId);
    }
}
