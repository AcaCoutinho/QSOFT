package com.example.pizzeria.controller;

import com.example.pizzeria.dto.MenuResponse;
import com.example.pizzeria.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Menu")
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "Retrieve full menu")
    @GetMapping
    public MenuResponse getMenu() {
        return menuService.getMenu();
    }
}
