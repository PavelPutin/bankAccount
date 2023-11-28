package edu.vsu.putinpa.application.model;

import edu.vsu.putinpa.infrastructure.orm.api.Column;
import edu.vsu.putinpa.infrastructure.orm.api.JoinColumn;
import edu.vsu.putinpa.infrastructure.orm.api.ManyToOne;
import edu.vsu.putinpa.infrastructure.orm.api.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Table("account")
public class Account extends EntityWithUUID {
    @Column("name")
    private String name;
    @Column("whenopened")
    private Instant whenOpened;
    @Column("whenclosed")
    private Instant whenClosed;
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Client creator;
    private Money balance;

    {
        whenOpened = Instant.now();
    }

    public Account() {}

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

    public Money replenishment(Money money) {
        balance = balance.add(money);
        return balance;
    }

    public Money withdrawal(Money money) {
        balance = balance.subtract(money);
        return balance;
    }
}
