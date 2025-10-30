package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.CustomPizza;
import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.domain.entity.PizzaBase;
import com.example.pizzeria.domain.enums.IngredientType;
import com.example.pizzeria.domain.value.Description;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CustomPizzaTest {

    @Test
    void shouldCalculatePriceFromBaseAndCustomIngredients() {
        PizzaBase base = new PizzaBase();
        base.setName(Name.of("Classic"));
        base.setDescription(Description.of("Base"));
        base.setPrice(Money.of(new BigDecimal("6.20")));

        Ingredient olives = new Ingredient();
        olives.setName(Name.of("Olives"));
        olives.setType(IngredientType.VEGETABLE);
        olives.setPrice(Money.of(new BigDecimal("0.70")));

        Ingredient onions = new Ingredient();
        onions.setName(Name.of("Onions"));
        onions.setType(IngredientType.VEGETABLE);
        onions.setPrice(Money.of(new BigDecimal("0.40")));

        CustomPizza pizza = new CustomPizza();
        pizza.setName(Name.of("Build"));
        pizza.setBase(base);
        pizza.setCustomIngredients(Set.of(olives, onions));
        pizza.calculatePrice();

        assertThat(pizza.getPrice().getAmount()).isEqualTo(new BigDecimal("7.30"));
    }

    @Test
    void shouldAllowClearingIngredients() {
        CustomPizza pizza = new CustomPizza();
        pizza.setName(Name.of("Reset"));
        pizza.setBase(buildBase());
        pizza.setCustomIngredients(null);

        assertThat(pizza.getCustomIngredients()).isEmpty();
    }

    private PizzaBase buildBase() {
        PizzaBase base = new PizzaBase();
        base.setName(Name.of("Solo"));
        base.setDescription(Description.of("Plain"));
        base.setPrice(Money.of(new BigDecimal("5.00")));
        return base;
    }
}
