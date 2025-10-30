package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.dto.IngredientDto;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.mapper.IngredientMapper;
import com.example.pizzeria.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class IngredientService {

    private static final Logger log = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> findAll() {
        return repository.findAll();
    }

    public Ingredient findByPublicId(UUID publicId) {
        return repository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found: " + publicId));
    }

    public Ingredient create(IngredientDto dto) {
        Ingredient ingredient = new Ingredient();
        IngredientMapper.updateEntity(dto, ingredient);
        ingredient.setPublicId(UUID.randomUUID());
        Ingredient saved = repository.save(ingredient);
        log.info("Created ingredient {}", saved.getName());
        return saved;
    }

    public Ingredient update(UUID publicId, IngredientDto dto) {
        Ingredient ingredient = findByPublicId(publicId);
        IngredientMapper.updateEntity(dto, ingredient);
        return repository.save(ingredient);
    }

    public void delete(UUID publicId) {
        Ingredient ingredient = findByPublicId(publicId);
        repository.delete(ingredient);
        log.info("Deleted ingredient {}", ingredient.getName());
    }
}
