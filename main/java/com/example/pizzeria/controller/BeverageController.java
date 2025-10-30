package com.example.pizzeria.controller;

import com.example.pizzeria.dto.BeverageDto;
import com.example.pizzeria.mapper.BeverageMapper;
import com.example.pizzeria.service.BeverageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Beverages")
@RestController
@RequestMapping("/api/v1/beverages")
public class BeverageController {

    private final BeverageService beverageService;

    public BeverageController(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @Operation(summary = "List all beverages")
    @GetMapping
    public List<BeverageDto> list() {
        return beverageService.findAll().stream().map(BeverageMapper::toDto).toList();
    }

    @Operation(summary = "Create beverage")
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<BeverageDto> create(@Valid @RequestBody BeverageDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BeverageMapper.toDto(beverageService.create(dto)));
    }

    @Operation(summary = "Update beverage")
    @PutMapping("/{publicId}")
    @PreAuthorize("hasRole('MANAGER')")
    public BeverageDto update(@PathVariable UUID publicId, @Valid @RequestBody BeverageDto dto) {
        return BeverageMapper.toDto(beverageService.update(publicId, dto));
    }

    @Operation(summary = "Delete beverage")
    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER')")
    public void delete(@PathVariable UUID publicId) {
        beverageService.delete(publicId);
    }
}
