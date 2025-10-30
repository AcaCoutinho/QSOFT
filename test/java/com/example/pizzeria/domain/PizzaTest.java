package com.example.pizzeria.domain;

import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.domain.entity.Pizza;
import com.example.pizzeria.domain.entity.PizzaBase;
import com.example.pizzeria.domain.enums.IngredientType;
import com.example.pizzeria.domain.value.Description;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PizzaTest {

    @Test
    void shouldCalculatePriceFromBaseAndIngredients() {
        PizzaBase base = new PizzaBase();
        base.setName(Name.of("Classic"));
        base.setDescription(Description.of("Base"));
        base.setPrice(Money.of(new BigDecimal("6.00")));

        Ingredient cheese = new Ingredient();
        cheese.setName(Name.of("Cheese"));
        cheese.setType(IngredientType.CHEESE);
        cheese.setPrice(Money.of(new BigDecimal("1.50")));

        Ingredient sauce = new Ingredient();
        sauce.setName(Name.of("Sauce"));
        sauce.setType(IngredientType.SAUCE);
        sauce.setPrice(Money.of(new BigDecimal("0.80")));

        Pizza pizza = new Pizza();
        pizza.setName(Name.of("Test"));
        pizza.setBase(base);
        pizza.setDefaultIngredients(Set.of(cheese, sauce));
        pizza.calculatePrice();

        assertThat(pizza.getPrice().getAmount()).isEqualTo(new BigDecimal("8.30"));
    }

    @Test
    void shouldHandleNullIngredientSet() {
        PizzaBase base = new PizzaBase();
        base.setName(Name.of("Thin"));
        base.setDescription(Description.of("Light"));
        base.setPrice(Money.of(new BigDecimal("5.50")));

        Pizza pizza = new Pizza();
        pizza.setName(Name.of("Solo"));
        pizza.setBase(base);
        pizza.setDefaultIngredients(null);
        pizza.calculatePrice();

        assertThat(pizza.getDefaultIngredients()).isEmpty();
        assertThat(pizza.getPrice().getAmount()).isEqualTo(new BigDecimal("5.50"));
    }
}
