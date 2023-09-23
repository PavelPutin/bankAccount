package edu.vsu.putinpa.application.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Account extends EntityWithUUID {
    private String name;
    private Instant whenOpened;
    private Instant whenClosed;
    private Client creator;
    private Money balance;

    {
        whenOpened = Instant.now();
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

    public void setWhenOpened(Instant whenOpened) {
        this.whenOpened = whenOpened;
    }

    public void setCreator(Client creator) {
        this.creator = creator;
    }

    public Instant getWhenOpened() {
        return whenOpened;
    }

    public Instant getWhenClosed() {
        return whenClosed;
    }

    public void setWhenClosed(Instant whenClosed) {
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

    public Money add(Money money) {
        balance = balance.add(money);
        return balance;
    }

    public Money subtract(Money money) {
        balance = balance.subtract(money);
        return balance;
    }
}
