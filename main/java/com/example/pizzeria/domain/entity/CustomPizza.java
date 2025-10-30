package com.example.pizzeria.domain.entity;

import com.example.pizzeria.domain.value.Money;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "custom_pizzas")
public class CustomPizza extends ProductEntity {

    @ManyToOne(optional = false)
    private PizzaBase base;

    @ManyToMany
    @JoinTable(name = "custom_pizza_ingredients",
            joinColumns = @JoinColumn(name = "custom_pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> customIngredients = new HashSet<>();

    @ManyToOne
    private Pizza template;

    @PrePersist
    @PreUpdate
    public void calculatePrice() {
        Money total = base.getPrice();
        for (Ingredient ingredient : customIngredients) {
            total = total.add(ingredient.getPrice());
        }
        setPrice(total);
    }

    public PizzaBase getBase() {
        return base;
    }

    public void setBase(PizzaBase base) {
        this.base = base;
    }

    public Set<Ingredient> getCustomIngredients() {
        return customIngredients;
    }

    public void setCustomIngredients(Set<Ingredient> customIngredients) {
        this.customIngredients.clear();
        if (customIngredients != null) {
            this.customIngredients.addAll(customIngredients);
        }
    }

    public Pizza getTemplate() {
        return template;
    }

    public void setTemplate(Pizza template) {
        this.template = template;
    }
}
