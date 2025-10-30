package com.example.pizzeria.domain;

import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import java.util.UUID;

public interface Product {
    Long getId();
    UUID getPublicId();
    Name getName();
    Money getPrice();
}
