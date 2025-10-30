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

class PizzaPriceCalculationTest {

    @Test
    void calculatesPriceFromBaseAndIngredients() {
        PizzaBase base = new PizzaBase();
        base.setName(Name.of("Classic"));
        base.setDescription(Description.of("Traditional"));
        base.setPrice(Money.of(new BigDecimal("6.00")));

        Ingredient cheese = new Ingredient();
        cheese.setPrice(Money.of(new BigDecimal("1.50")));
        cheese.setType(IngredientType.CHEESE);
        cheese.setName(Name.of("Mozzarella"));

        Ingredient sauce = new Ingredient();
        sauce.setPrice(Money.of(new BigDecimal("0.80")));
        sauce.setType(IngredientType.SAUCE);
        sauce.setName(Name.of("Tomato Sauce"));

        Pizza pizza = new Pizza();
        pizza.setName(Name.of("Margherita"));
        pizza.setBase(base);
        pizza.setDefaultIngredients(Set.of(cheese, sauce));

        pizza.calculatePrice();

        assertThat(pizza.getPrice()).isEqualTo(Money.of(new BigDecimal("8.30")));
    }
}
