package com.example.pizzeria.repository;

import com.example.pizzeria.domain.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    boolean existsByNameValueIgnoreCase(String name);
    Optional<Ingredient> findByPublicId(UUID publicId);
}
