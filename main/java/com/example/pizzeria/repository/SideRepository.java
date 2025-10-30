package com.example.pizzeria.repository;

import com.example.pizzeria.domain.entity.Side;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SideRepository extends JpaRepository<Side, Long> {
    boolean existsByNameValueIgnoreCase(String name);
    Optional<Side> findByPublicId(UUID publicId);
}
