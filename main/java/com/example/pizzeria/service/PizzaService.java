package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.domain.entity.Pizza;
import com.example.pizzeria.domain.entity.PizzaBase;
import com.example.pizzeria.domain.value.Name;
import com.example.pizzeria.dto.PizzaDto;
import com.example.pizzeria.dto.PizzaRequest;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.mapper.PizzaMapper;
import com.example.pizzeria.repository.IngredientRepository;
import com.example.pizzeria.repository.PizzaBaseRepository;
import com.example.pizzeria.repository.PizzaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class PizzaService {

    private static final Logger log = LoggerFactory.getLogger(PizzaService.class);

    private final PizzaRepository pizzaRepository;
    private final PizzaBaseRepository pizzaBaseRepository;
    private final IngredientRepository ingredientRepository;

    public PizzaService(PizzaRepository pizzaRepository,
                        PizzaBaseRepository pizzaBaseRepository,
                        IngredientRepository ingredientRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaBaseRepository = pizzaBaseRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    public Pizza findByPublicId(UUID publicId) {
        return pizzaRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza not found: " + publicId));
    }

    public Pizza create(PizzaRequest request) {
    Pizza pizza = new Pizza();
    pizza.setName(Name.of(request.getName()));
        pizza.setBase(resolveBase(request.getBaseId()));
        pizza.setDefaultIngredients(resolveIngredients(request.getIngredientIds()));
        pizza.setPublicId(UUID.randomUUID());
        Pizza saved = pizzaRepository.save(pizza);
        log.info("Created pizza {}", saved.getName());
        return saved;
    }

    public Pizza update(UUID publicId, PizzaRequest request) {
        Pizza pizza = findByPublicId(publicId);
    pizza.setName(Name.of(request.getName()));
        pizza.setBase(resolveBase(request.getBaseId()));
        pizza.setDefaultIngredients(resolveIngredients(request.getIngredientIds()));
        return pizzaRepository.save(pizza);
    }

    public void delete(UUID publicId) {
        pizzaRepository.delete(findByPublicId(publicId));
    }

    public List<PizzaDto> toDtos(List<Pizza> pizzas) {
        return pizzas.stream().map(PizzaMapper::toDto).toList();
    }

    private PizzaBase resolveBase(UUID baseId) {
        return pizzaBaseRepository.findByPublicId(baseId)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza base not found: " + baseId));
    }

    private Set<Ingredient> resolveIngredients(Set<UUID> ingredientIds) {
        if (ingredientIds == null || ingredientIds.isEmpty()) {
            return new HashSet<>();
        }
        Set<Ingredient> ingredients = new HashSet<>();
        for (UUID ingredientId : ingredientIds) {
            Ingredient ingredient = ingredientRepository.findByPublicId(ingredientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found: " + ingredientId));
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}
