package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT authentication response")
public class AuthResponse {

    @Schema(description = "Signed JWT token to use with the Bearer scheme", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdXN0b21lciIsImlhdCI6MTcxNTY1MzYwMCwiZXhwIjoxNzE1NjU3MjAwfQ.wXU4LkRn-FdULuUVz4SJfGso1MOP4CwF3uLrNwuFg8g", accessMode = Schema.AccessMode.READ_ONLY)
    private String token;

    @Schema(description = "Role associated with the authenticated user", example = "CUSTOMER", allowableValues = {"CUSTOMER", "MANAGER"}, accessMode = Schema.AccessMode.READ_ONLY)
    private String role;

    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
