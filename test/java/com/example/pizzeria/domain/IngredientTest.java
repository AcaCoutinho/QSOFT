package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.domain.enums.IngredientType;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientTest {

    @Test
    void shouldSetNameAndPrice() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(Name.of("Mozzarella"));
        ingredient.setType(IngredientType.CHEESE);
        ingredient.setPrice(Money.of(new BigDecimal("1.80")));

        assertThat(ingredient.getName().getValue()).isEqualTo("Mozzarella");
        assertThat(ingredient.getPrice().getAmount()).isEqualTo(new BigDecimal("1.80"));
        assertThat(ingredient.getType()).isEqualTo(IngredientType.CHEESE);
    }
}
