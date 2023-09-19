package edu.vsu.putinpa.application.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account extends EntityWithUUID {
    private String name;
    private final LocalDateTime whenOpened;
    private LocalDateTime whenClosed;
    private final Client creator;
    private Money balance;

    {
        whenOpened = LocalDateTime.now();
    }

    public Account(String name, String currency, Client creator) {
        this.name = name;
        this.creator = creator;
        this.balance = new Money(currency, BigDecimal.ZERO);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getWhenOpened() {
        return whenOpened;
    }

    public LocalDateTime getWhenClosed() {
        return whenClosed;
    }

    public void setWhenClosed(LocalDateTime whenClosed) {
        this.whenClosed = whenClosed;
    }

    public Client getCreator() {
        return creator;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
