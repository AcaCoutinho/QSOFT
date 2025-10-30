package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "User resource representation")
public class UserDto {

    @Schema(description = "Public identifier for the user", format = "uuid", example = "c43f7035-d5bf-4ff3-90fa-1d0dad418236", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Unique username", example = "manager", accessMode = Schema.AccessMode.READ_ONLY)
    private String username;

    @Schema(description = "Email address", example = "manager@example.com", accessMode = Schema.AccessMode.READ_ONLY)
    private String email;

    @Schema(description = "Role assigned to the user", example = "MANAGER", allowableValues = {"CUSTOMER", "MANAGER"}, accessMode = Schema.AccessMode.READ_ONLY)
    private String role;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
