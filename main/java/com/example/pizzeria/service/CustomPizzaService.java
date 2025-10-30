package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.CustomPizza;
import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.domain.entity.Pizza;
import com.example.pizzeria.domain.entity.PizzaBase;
import com.example.pizzeria.domain.value.Name;
import com.example.pizzeria.dto.CustomPizzaRequest;
import com.example.pizzeria.dto.CustomPizzaResponse;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.mapper.CustomPizzaMapper;
import com.example.pizzeria.repository.CustomPizzaRepository;
import com.example.pizzeria.repository.IngredientRepository;
import com.example.pizzeria.repository.PizzaBaseRepository;
import com.example.pizzeria.repository.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class CustomPizzaService {

    private final CustomPizzaRepository customPizzaRepository;
    private final PizzaBaseRepository pizzaBaseRepository;
    private final IngredientRepository ingredientRepository;
    private final PizzaRepository pizzaRepository;

    public CustomPizzaService(CustomPizzaRepository customPizzaRepository,
                              PizzaBaseRepository pizzaBaseRepository,
                              IngredientRepository ingredientRepository,
                              PizzaRepository pizzaRepository) {
        this.customPizzaRepository = customPizzaRepository;
        this.pizzaBaseRepository = pizzaBaseRepository;
        this.ingredientRepository = ingredientRepository;
        this.pizzaRepository = pizzaRepository;
    }

    public CustomPizzaResponse create(CustomPizzaRequest request) {
    CustomPizza customPizza = new CustomPizza();
    customPizza.setName(Name.of(request.getName()));
        customPizza.setBase(resolveBase(request.getBaseId()));
        customPizza.setCustomIngredients(resolveIngredients(request.getIngredientIds()));
        if (request.getTemplateId() != null) {
            customPizza.setTemplate(resolveTemplate(request.getTemplateId()));
        }
        customPizza.setPublicId(UUID.randomUUID());
        CustomPizza saved = customPizzaRepository.save(customPizza);
        return CustomPizzaMapper.toDto(saved);
    }

    public CustomPizza findByPublicId(UUID publicId) {
        return customPizzaRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Custom pizza not found: " + publicId));
    }

    public void delete(UUID publicId) {
        customPizzaRepository.delete(findByPublicId(publicId));
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

    private Pizza resolveTemplate(UUID templateId) {
        return pizzaRepository.findByPublicId(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza not found: " + templateId));
    }
}
