package com.example.pizzeria.domain.enums;

public enum OrderStatus {
    PLACED,
    PREPARING,
    BAKING,
    OUT_FOR_DELIVERY,
    DELIVERED;

    public OrderStatus next() {
        return switch (this) {
            case PLACED -> PREPARING;
            case PREPARING -> BAKING;
            case BAKING -> OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY -> DELIVERED;
            case DELIVERED -> DELIVERED;
        };
    }
}
