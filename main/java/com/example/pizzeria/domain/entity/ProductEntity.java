package com.example.pizzeria.domain.entity;

import com.example.pizzeria.domain.Product;
import com.example.pizzeria.domain.value.Money;
import com.example.pizzeria.domain.value.Name;
import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProductEntity extends PublicIdEntity implements Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    private Name name;
    
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price", nullable = false, precision = 12, scale = 2))
    private Money price;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    @Override
    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = Objects.requireNonNull(price, "price cannot be null");
    }
}
