package com.example.pizzeria.service;

import com.example.pizzeria.domain.entity.User;
import com.example.pizzeria.domain.enums.Role;
import com.example.pizzeria.dto.CreateUserRequest;
import com.example.pizzeria.exception.ResourceNotFoundException;
import com.example.pizzeria.mapper.UserMapper;
import com.example.pizzeria.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User createUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.valueOf(request.getRole()));
        user.setPublicId(UUID.randomUUID());
        User saved = userRepository.save(user);
        log.info("Created user {} with role {}", saved.getUsername(), saved.getRole());
        return saved;
    }

    public User getUser(UUID publicId) {
        return userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + publicId));
    }

    public void deleteUser(UUID publicId) {
        User user = getUser(publicId);
        userRepository.delete(user);
        log.info("Deleted user {}", user.getUsername());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
