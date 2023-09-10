package edu.vsu.putinpa.model;

import java.math.BigDecimal;

public record Money(String currency, BigDecimal value) {
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
