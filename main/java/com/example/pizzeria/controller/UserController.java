package com.example.pizzeria.controller;

import com.example.pizzeria.dto.CreateUserRequest;
import com.example.pizzeria.dto.UserDto;
import com.example.pizzeria.mapper.UserMapper;
import com.example.pizzeria.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Users")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "List all users")
    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public List<UserDto> listUsers() {
        return userService.listUsers().stream().map(UserMapper::toDto).toList();
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.toDto(userService.createUser(request)));
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER')")
    public void deleteUser(@PathVariable UUID publicId) {
        userService.deleteUser(publicId);
    }
}
