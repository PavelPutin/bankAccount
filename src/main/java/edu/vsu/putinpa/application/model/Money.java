package edu.vsu.putinpa.application.model;

import edu.vsu.putinpa.infrastructure.orm.api.Column;
import edu.vsu.putinpa.infrastructure.orm.api.JoinColumn;
import edu.vsu.putinpa.infrastructure.orm.api.ManyToOne;

import java.math.BigDecimal;

public record Money(
        @Column("currency_id")
        String currency,
        @Column("balance")
        BigDecimal value) {
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
