package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.Beverage;
import com.example.pizzeria.domain.entity.CustomPizza;
import com.example.pizzeria.domain.entity.Pizza;
import com.example.pizzeria.domain.entity.Side;
import com.example.pizzeria.domain.entity.ProductEntity;
import com.example.pizzeria.domain.enums.ProductType;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.repository.BeverageRepository;
import com.example.pizzeria.repository.CustomPizzaRepository;
import com.example.pizzeria.repository.PizzaRepository;
import com.example.pizzeria.repository.SideRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductLookupService {

    private final PizzaRepository pizzaRepository;
    private final CustomPizzaRepository customPizzaRepository;
    private final SideRepository sideRepository;
    private final BeverageRepository beverageRepository;

    public ProductLookupService(PizzaRepository pizzaRepository,
                                CustomPizzaRepository customPizzaRepository,
                                SideRepository sideRepository,
                                BeverageRepository beverageRepository) {
        this.pizzaRepository = pizzaRepository;
        this.customPizzaRepository = customPizzaRepository;
        this.sideRepository = sideRepository;
        this.beverageRepository = beverageRepository;
    }

    public ProductEntity findProduct(ProductType type, UUID publicId) {
        return switch (type) {
            case PIZZA -> pizzaRepository.findByPublicId(publicId)
                    .orElseThrow(() -> new ResourceNotFoundException("Pizza not found: " + publicId));
            case CUSTOM_PIZZA -> customPizzaRepository.findByPublicId(publicId)
                    .orElseThrow(() -> new ResourceNotFoundException("Custom pizza not found: " + publicId));
            case SIDE -> sideRepository.findByPublicId(publicId)
                    .orElseThrow(() -> new ResourceNotFoundException("Side not found: " + publicId));
            case BEVERAGE -> beverageRepository.findByPublicId(publicId)
                    .orElseThrow(() -> new ResourceNotFoundException("Beverage not found: " + publicId));
        };
    }
}
