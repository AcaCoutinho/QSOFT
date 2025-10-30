package com.example.pizzeria.repository;

import com.example.pizzeria.domain.entity.CustomPizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomPizzaRepository extends JpaRepository<CustomPizza, Long> {
	Optional<CustomPizza> findByPublicId(UUID publicId);
}
