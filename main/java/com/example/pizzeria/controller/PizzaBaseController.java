package com.example.pizzeria.controller;

import com.example.pizzeria.dto.PizzaBaseDto;
import com.example.pizzeria.mapper.PizzaBaseMapper;
import com.example.pizzeria.service.PizzaBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Pizza Bases")
@RestController
@RequestMapping("/api/v1/bases")
public class PizzaBaseController {

    private final PizzaBaseService pizzaBaseService;

    public PizzaBaseController(PizzaBaseService pizzaBaseService) {
        this.pizzaBaseService = pizzaBaseService;
    }

    @Operation(summary = "List pizza bases")
    @GetMapping
    public List<PizzaBaseDto> list() {
        return pizzaBaseService.findAll().stream().map(PizzaBaseMapper::toDto).toList();
    }

    @Operation(summary = "Create pizza base")
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<PizzaBaseDto> create(@Valid @RequestBody PizzaBaseDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PizzaBaseMapper.toDto(pizzaBaseService.create(dto)));
    }

    @Operation(summary = "Update pizza base")
    @PutMapping("/{publicId}")
    @PreAuthorize("hasRole('MANAGER')")
    public PizzaBaseDto update(@PathVariable UUID publicId, @Valid @RequestBody PizzaBaseDto dto) {
        return PizzaBaseMapper.toDto(pizzaBaseService.update(publicId, dto));
    }

    @Operation(summary = "Delete pizza base")
    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER')")
    public void delete(@PathVariable UUID publicId) {
        pizzaBaseService.delete(publicId);
    }
}
