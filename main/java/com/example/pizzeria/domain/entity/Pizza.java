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
@Table(name = "pizzas")
public class Pizza extends ProductEntity {

    @ManyToOne(optional = false)
    private PizzaBase base;

    @ManyToMany
    @JoinTable(name = "pizza_default_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> defaultIngredients = new HashSet<>();

    @PrePersist
    @PreUpdate
    public void calculatePrice() {
        Money total = base.getPrice();
        for (Ingredient ingredient : defaultIngredients) {
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

    public Set<Ingredient> getDefaultIngredients() {
        return defaultIngredients;
    }

    public void setDefaultIngredients(Set<Ingredient> defaultIngredients) {
        this.defaultIngredients.clear();
        if (defaultIngredients != null) {
            this.defaultIngredients.addAll(defaultIngredients);
        }
    }
}
