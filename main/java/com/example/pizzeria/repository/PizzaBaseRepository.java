package com.example.pizzeria.repository;

import com.example.pizzeria.domain.entity.PizzaBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PizzaBaseRepository extends JpaRepository<PizzaBase, Long> {
    boolean existsByNameValueIgnoreCase(String name);
    Optional<PizzaBase> findByPublicId(UUID publicId);
}
