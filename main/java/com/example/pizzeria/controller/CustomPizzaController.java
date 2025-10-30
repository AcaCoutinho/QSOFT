package com.example.pizzeria.controller;

import com.example.pizzeria.dto.CustomPizzaRequest;
import com.example.pizzeria.dto.CustomPizzaResponse;
import com.example.pizzeria.mapper.CustomPizzaMapper;
import com.example.pizzeria.service.CustomPizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Custom Pizzas")
@RestController
@RequestMapping("/api/v1/custom-pizzas")
public class CustomPizzaController {

    private final CustomPizzaService customPizzaService;

    public CustomPizzaController(CustomPizzaService customPizzaService) {
        this.customPizzaService = customPizzaService;
    }

    @Operation(summary = "Create a custom pizza")
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public ResponseEntity<CustomPizzaResponse> create(@Valid @RequestBody CustomPizzaRequest request) {
        return ResponseEntity.ok(customPizzaService.create(request));
    }

    @Operation(summary = "Get custom pizza by id")
    @GetMapping("/{publicId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER')")
    public CustomPizzaResponse get(@PathVariable UUID publicId) {
        return CustomPizzaMapper.toDto(customPizzaService.findByPublicId(publicId));
    }

    @Operation(summary = "Delete custom pizza")
    @DeleteMapping("/{publicId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void delete(@PathVariable UUID publicId) {
        customPizzaService.delete(publicId);
    }
}
