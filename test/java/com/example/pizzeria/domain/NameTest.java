package com.example.pizzeria.domain;

import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameTest {

    @Test
    void shouldTrimAndStoreValue() {
        Name name = Name.of("  Margherita  ");

        assertThat(name.getValue()).isEqualTo("Margherita");
    }

    @Test
    void shouldRejectShortNames() {
        assertThrows(IllegalArgumentException.class, () -> Name.of("A"));
    }
}
