package com.example.pizzeria.repository;

import com.example.pizzeria.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPublicId(UUID publicId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
