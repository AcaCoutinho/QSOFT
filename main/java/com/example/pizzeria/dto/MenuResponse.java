package com.example.pizzeria.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Aggregated menu response containing all published catalog entries")
public class MenuResponse {
    @ArraySchema(arraySchema = @Schema(description = "Available pizzas"))
    private List<PizzaDto> pizzas;

    @ArraySchema(arraySchema = @Schema(description = "Available sides"))
    private List<SideDto> sides;

    @ArraySchema(arraySchema = @Schema(description = "Available beverages"))
    private List<BeverageDto> beverages;

    @ArraySchema(arraySchema = @Schema(description = "Ingredients that can be used when crafting custom pizzas"))
    private List<IngredientDto> ingredients;

    @ArraySchema(arraySchema = @Schema(description = "Pizza bases that can be selected"))
    private List<PizzaBaseDto> bases;

    public MenuResponse(List<PizzaDto> pizzas,
                        List<SideDto> sides,
                        List<BeverageDto> beverages,
                        List<IngredientDto> ingredients,
                        List<PizzaBaseDto> bases) {
        this.pizzas = pizzas;
        this.sides = sides;
        this.beverages = beverages;
        this.ingredients = ingredients;
        this.bases = bases;
    }

    public List<PizzaDto> getPizzas() {
        return pizzas;
    }

    public List<SideDto> getSides() {
        return sides;
    }

    public List<BeverageDto> getBeverages() {
        return beverages;
    }

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }

    public List<PizzaBaseDto> getBases() {
        return bases;
    }
}
