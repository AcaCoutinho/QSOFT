package com.example.pizzeria.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Name {

    private static final int MIN_LENGTH = 2;

    @Column(name = "value", nullable = false)
    private String value;

    protected Name() {
        // for JPA
    }

    private Name(String value) {
        this.value = normalize(value);
    }

    public static Name of(String value) {
        return new Name(value);
    }

    private String normalize(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        String trimmed = value.trim();
        if (trimmed.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Name must be at least " + MIN_LENGTH + " characters long");
        }
        return trimmed;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name name)) {
            return false;
        }
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
