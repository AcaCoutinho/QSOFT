package com.example.pizzeria.controller;

import com.example.pizzeria.dto.PizzaDto;
import com.example.pizzeria.dto.PizzaRequest;
import com.example.pizzeria.mapper.PizzaMapper;
import com.example.pizzeria.service.PizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Pizzas")
@RestController
@RequestMapping("/api/v1/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Operation(summary = "List pizzas")
    @GetMapping
    public List<PizzaDto> list() {
        return pizzaService.findAll().stream().map(PizzaMapper::toDto).toList();
    }

    @Operation(summary = "Get pizza by id")
    @GetMapping("/{publicId}")
    public PizzaDto get(@PathVariable UUID publicId) {
        return PizzaMapper.toDto(pizzaService.findByPublicId(publicId));
    }

    @Operation(summary = "Create pizza")
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<PizzaDto> create(@Valid @RequestBody PizzaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PizzaMapper.toDto(pizzaService.create(request)));
    }

    @Operation(summary = "Update pizza")
    @PutMapping("/{publicId}")
    @PreAuthorize("hasRole('MANAGER')")
    public PizzaDto update(@PathVariable UUID publicId, @Valid @RequestBody PizzaRequest request) {
        return PizzaMapper.toDto(pizzaService.update(publicId, request));
    }

    @Operation(summary = "Delete pizza")
    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER')")
    public void delete(@PathVariable UUID publicId) {
        pizzaService.delete(publicId);
    }
}
