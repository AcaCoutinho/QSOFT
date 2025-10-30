package com.example.pizzeria.service;

import com.example.pizzeria.dto.MenuResponse;
import com.example.pizzeria.mapper.BeverageMapper;
import com.example.pizzeria.mapper.IngredientMapper;
import com.example.pizzeria.mapper.PizzaBaseMapper;
import com.example.pizzeria.mapper.PizzaMapper;
import com.example.pizzeria.mapper.SideMapper;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final PizzaService pizzaService;
    private final SideService sideService;
    private final BeverageService beverageService;
    private final IngredientService ingredientService;
    private final PizzaBaseService pizzaBaseService;

    public MenuService(PizzaService pizzaService,
                       SideService sideService,
                       BeverageService beverageService,
                       IngredientService ingredientService,
                       PizzaBaseService pizzaBaseService) {
        this.pizzaService = pizzaService;
        this.sideService = sideService;
        this.beverageService = beverageService;
        this.ingredientService = ingredientService;
        this.pizzaBaseService = pizzaBaseService;
    }

    public MenuResponse getMenu() {
        return new MenuResponse(
                pizzaService.toDtos(pizzaService.findAll()),
                sideService.findAll().stream().map(SideMapper::toDto).toList(),
                beverageService.findAll().stream().map(BeverageMapper::toDto).toList(),
                ingredientService.findAll().stream().map(IngredientMapper::toDto).toList(),
                pizzaBaseService.findAll().stream().map(PizzaBaseMapper::toDto).toList()
        );
    }
}
