package com.example.pizzeria.repository;

import com.example.pizzeria.domain.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    boolean existsByNameValueIgnoreCase(String name);
    Optional<Pizza> findByPublicId(UUID publicId);
}
