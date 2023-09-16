package edu.vsu.putinpa.application.model;

public class IncompatibleCurrencyValue extends RuntimeException {
    public IncompatibleCurrencyValue(String owningCurrency, String otherCurrency) {
        super(String.format("Can't add %s to %s. Make currency exchange first.", owningCurrency, otherCurrency));
    }
}
