package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Payload for creating a new user account")
public class CreateUserRequest {

    @NotBlank
    @Schema(description = "Unique username for the user", example = "team_member", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank
    @Schema(description = "Temporary password assigned to the user", example = "Password123!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Email
    @NotBlank
    @Schema(description = "Contact email address", example = "team.member@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotNull
    @Schema(description = "Role to assign", example = "CUSTOMER", allowableValues = {"CUSTOMER", "MANAGER"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
