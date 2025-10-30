package com.example.pizzeria.domain;

import com.example.pizzeria.domain.value.Description;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DescriptionTest {

    @Test
    void shouldNormalizeValue() {
        Description description = Description.of("  Crispy crust ");

        assertThat(description.getValue()).isEqualTo("Crispy crust");
    }

    @Test
    void shouldRejectNullValues() {
        assertThrows(IllegalArgumentException.class, () -> Description.of(null));
    }

    @Test
    void shouldIgnoreDefaultState() {
    }
}
