package com.example.pizzeria.config;

import com.example.pizzeria.domain.entity.Beverage;
import com.example.pizzeria.domain.entity.Ingredient;
import com.example.pizzeria.domain.entity.Pizza;
import com.example.pizzeria.domain.entity.PizzaBase;
import com.example.pizzeria.domain.entity.Side;
import com.example.pizzeria.domain.entity.User;
import com.example.pizzeria.domain.enums.IngredientType;
import com.example.pizzeria.domain.enums.Role;
import com.example.pizzeria.domain.value.Description;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import com.example.pizzeria.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner seedData(UserRepository userRepository,
                               IngredientRepository ingredientRepository,
                               PizzaBaseRepository pizzaBaseRepository,
                               PizzaRepository pizzaRepository,
                               SideRepository sideRepository,
                               BeverageRepository beverageRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User manager = new User();
                manager.setUsername("manager");
                manager.setPassword(passwordEncoder.encode("Password123!"));
                manager.setEmail("manager@pizzeria.local");
                manager.setRole(Role.MANAGER);
                manager.setPublicId(UUID.randomUUID());
                userRepository.save(manager);

                User customer = new User();
                customer.setUsername("customer");
                customer.setPassword(passwordEncoder.encode("Password123!"));
                customer.setEmail("customer@pizzeria.local");
                customer.setRole(Role.CUSTOMER);
                customer.setPublicId(UUID.randomUUID());
                userRepository.save(customer);
                log.info("Seeded default users");
            }

            if (ingredientRepository.count() == 0) {
                ingredientRepository.saveAll(List.of(
            buildIngredient("Mozzarella", IngredientType.CHEESE, new BigDecimal("1.50")),
            buildIngredient("Pepperoni", IngredientType.MEAT, new BigDecimal("2.00")),
            buildIngredient("Mushrooms", IngredientType.VEGETABLE, new BigDecimal("1.00")),
            buildIngredient("Tomato Sauce", IngredientType.SAUCE, new BigDecimal("0.80")),
            buildIngredient("Basil", IngredientType.VEGETABLE, new BigDecimal("0.50"))
                ));
                log.info("Seeded ingredients");
            }

            if (pizzaBaseRepository.count() == 0) {
                pizzaBaseRepository.saveAll(List.of(
            buildBase("Classic", "Hand-tossed classic base", new BigDecimal("6.00")),
            buildBase("Thin Crust", "Crispy thin crust base", new BigDecimal("5.50"))
                ));
                log.info("Seeded pizza bases");
            }

            if (pizzaRepository.count() == 0) {
                List<PizzaBase> bases = pizzaBaseRepository.findAll();
                if (bases.isEmpty()) {
                    throw new IllegalStateException("Pizza bases must exist before seeding pizzas");
                }
                PizzaBase classic = bases.get(0);
                List<Ingredient> ingredients = ingredientRepository.findAll();
        Pizza margherita = new Pizza();
        margherita.setName(Name.of("Margherita"));
                margherita.setBase(classic);
                margherita.setDefaultIngredients(Set.of(
            findIngredient(ingredients, "Mozzarella"),
            findIngredient(ingredients, "Tomato Sauce"),
            findIngredient(ingredients, "Basil")
                ));
        margherita.setPublicId(UUID.randomUUID());

                Pizza pepperoni = new Pizza();
        pepperoni.setName(Name.of("Pepperoni"));
                pepperoni.setBase(classic);
                pepperoni.setDefaultIngredients(Set.of(
            findIngredient(ingredients, "Mozzarella"),
            findIngredient(ingredients, "Tomato Sauce"),
            findIngredient(ingredients, "Pepperoni")
                ));
        pepperoni.setPublicId(UUID.randomUUID());

                pizzaRepository.saveAll(List.of(margherita, pepperoni));
                log.info("Seeded pizzas");
            }

            if (sideRepository.count() == 0) {
                Side garlicBread = new Side();
                garlicBread.setName(Name.of("Garlic Bread"));
                garlicBread.setDescription(Description.of("Toasted bread with garlic butter"));
                garlicBread.setPrice(Money.of(new BigDecimal("3.50")));
                garlicBread.setPublicId(UUID.randomUUID());

                Side salad = new Side();
                salad.setName(Name.of("Garden Salad"));
                salad.setDescription(Description.of("Fresh salad with vinaigrette"));
                salad.setPrice(Money.of(new BigDecimal("4.00")));
                salad.setPublicId(UUID.randomUUID());

                sideRepository.saveAll(List.of(garlicBread, salad));
                log.info("Seeded sides");
            }

            if (beverageRepository.count() == 0) {
                Beverage cola = new Beverage();
                cola.setName(Name.of("Cola"));
                cola.setDescription(Description.of("Chilled cola beverage"));
                cola.setPrice(Money.of(new BigDecimal("2.00")));
                cola.setPublicId(UUID.randomUUID());

                Beverage lemonade = new Beverage();
                lemonade.setName(Name.of("Lemonade"));
                lemonade.setDescription(Description.of("Fresh lemonade"));
                lemonade.setPrice(Money.of(new BigDecimal("2.50")));
                lemonade.setPublicId(UUID.randomUUID());

                beverageRepository.saveAll(List.of(cola, lemonade));
                log.info("Seeded beverages");
            }
        };
    }

    private Ingredient buildIngredient(String name, IngredientType type, BigDecimal price) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(Name.of(name));
        ingredient.setType(type);
        ingredient.setPrice(Money.of(price));
        ingredient.setPublicId(UUID.randomUUID());
        return ingredient;
    }

    private PizzaBase buildBase(String name, String description, BigDecimal price) {
        PizzaBase base = new PizzaBase();
        base.setName(Name.of(name));
        base.setDescription(Description.of(description));
        base.setPrice(Money.of(price));
        base.setPublicId(UUID.randomUUID());
        return base;
    }

    private Ingredient findIngredient(List<Ingredient> ingredients, String name) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getName().getValue().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow();
    }
}
