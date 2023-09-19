package edu.vsu.putinpa.application.model;

import java.math.BigDecimal;

public record Money(String currency, BigDecimal value) {
    public Money {
        if (value.doubleValue() < 0) {
            throw new IllegalArgumentException("Money value should be non-negative");
        }
    }
    public Money add(Money other) {
        if (!currency.equals(other.currency())) {
            throw new IncompatibleCurrencyValue(currency, other.currency());
        }
        return new Money(currency, value.add(other.value()));
    }

    public Money subtract(Money other) {
        if (!currency.equals(other.currency())) {
            throw new IncompatibleCurrencyValue(currency, other.currency());
        }
        return new Money(currency, value.subtract(other.value()));
    }
}
