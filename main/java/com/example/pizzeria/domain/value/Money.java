package com.example.pizzeria.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
public class Money {

    public static final BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    protected Money() {
        // for JPA
    }

    private Money(BigDecimal amount) {
        this.amount = normalize(amount);
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    private BigDecimal normalize(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Money value cannot be null");
        }
        BigDecimal scaled = value.setScale(SCALE, ROUNDING_MODE);
        if (scaled.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money value must be positive");
        }
        if (scaled.compareTo(MAX_AMOUNT) > 0) {
            throw new IllegalArgumentException("Money value exceeds maximum allowed amount of " + MAX_AMOUNT);
        }
        return scaled;
    }

    public Money add(Money other) {
        Objects.requireNonNull(other, "Money to add cannot be null");
        return Money.of(this.amount.add(other.amount));
    }

    public Money multiply(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return Money.of(this.amount.multiply(BigDecimal.valueOf(quantity)));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return amount.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money money)) {
            return false;
        }
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
