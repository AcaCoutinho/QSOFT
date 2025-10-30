package com.example.pizzeria.repository;

import com.example.pizzeria.domain.entity.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {
    boolean existsByNameValueIgnoreCase(String name);
    Optional<Beverage> findByPublicId(UUID publicId);
}
