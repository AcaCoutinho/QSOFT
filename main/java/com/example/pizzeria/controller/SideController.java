package com.example.pizzeria.controller;

import com.example.pizzeria.dto.SideDto;
import com.example.pizzeria.mapper.SideMapper;
import com.example.pizzeria.service.SideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Sides")
@RestController
@RequestMapping("/api/v1/sides")
public class SideController {

    private final SideService sideService;

    public SideController(SideService sideService) {
        this.sideService = sideService;
    }

    @Operation(summary = "List all sides")
    @GetMapping
    public List<SideDto> list() {
        return sideService.findAll().stream().map(SideMapper::toDto).toList();
    }

    @Operation(summary = "Create side")
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<SideDto> create(@Valid @RequestBody SideDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SideMapper.toDto(sideService.create(dto)));
    }

    @Operation(summary = "Update side")
    @PutMapping("/{publicId}")
    @PreAuthorize("hasRole('MANAGER')")
    public SideDto update(@PathVariable UUID publicId, @Valid @RequestBody SideDto dto) {
        return SideMapper.toDto(sideService.update(publicId, dto));
    }

    @Operation(summary = "Delete side")
    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER')")
    public void delete(@PathVariable UUID publicId) {
        sideService.delete(publicId);
    }
}
