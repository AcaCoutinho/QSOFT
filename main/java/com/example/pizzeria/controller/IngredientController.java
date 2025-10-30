package com.example.pizzeria.controller;

import com.example.pizzeria.dto.IngredientDto;
import com.example.pizzeria.mapper.IngredientMapper;
import com.example.pizzeria.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Ingredients")
@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(summary = "List all ingredients")
    @GetMapping
    public List<IngredientDto> list() {
        return ingredientService.findAll().stream()
                .map(IngredientMapper::toDto)
                .toList();
    }

    @Operation(summary = "Create new ingredient")
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<IngredientDto> create(@Valid @RequestBody IngredientDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(IngredientMapper.toDto(ingredientService.create(dto)));
    }

    @Operation(summary = "Update ingredient")
    @PutMapping("/{publicId}")
    @PreAuthorize("hasRole('MANAGER')")
    public IngredientDto update(@PathVariable UUID publicId, @Valid @RequestBody IngredientDto dto) {
        return IngredientMapper.toDto(ingredientService.update(publicId, dto));
    }

    @Operation(summary = "Delete ingredient")
    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER')")
    public void delete(@PathVariable UUID publicId) {
        ingredientService.delete(publicId);
    }
}
