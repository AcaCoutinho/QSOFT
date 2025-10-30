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

class CustomPizzaPriceCalculationTest {

    @Test
    void calculatesPriceUsingBaseAndCustomIngredients() {
        PizzaBase base = new PizzaBase();
        base.setName(Name.of("Thin Crust"));
        base.setDescription(Description.of("Crispy"));
        base.setPrice(Money.of(new BigDecimal("5.00")));

        Ingredient pepperoni = new Ingredient();
        pepperoni.setName(Name.of("Pepperoni"));
        pepperoni.setType(IngredientType.MEAT);
        pepperoni.setPrice(Money.of(new BigDecimal("2.00")));

        Ingredient mushrooms = new Ingredient();
        mushrooms.setName(Name.of("Mushrooms"));
        mushrooms.setType(IngredientType.VEGETABLE);
        mushrooms.setPrice(Money.of(new BigDecimal("1.00")));

        CustomPizza customPizza = new CustomPizza();
        customPizza.setName(Name.of("Pepperoni Mushroom"));
        customPizza.setBase(base);
        customPizza.setCustomIngredients(Set.of(pepperoni, mushrooms));

        customPizza.calculatePrice();

        assertThat(customPizza.getPrice()).isEqualTo(Money.of(new BigDecimal("8.00")));
    }
}
