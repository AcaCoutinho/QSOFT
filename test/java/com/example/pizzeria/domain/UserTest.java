package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.User;
import com.example.pizzeria.domain.enums.Role;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void shouldExposeRoleAuthority() {
        User user = new User();
        user.setUsername("manager");
        user.setPassword("secret");
        user.setEmail("manager@example.com");
        user.setRole(Role.MANAGER);

        assertThat(user.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_MANAGER");
    }
}
