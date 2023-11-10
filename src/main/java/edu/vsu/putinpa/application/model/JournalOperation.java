package edu.vsu.putinpa.application.model;

import edu.vsu.putinpa.infrastructure.orm.api.Column;
import edu.vsu.putinpa.infrastructure.orm.api.Table;

import java.time.Instant;

@Table("journal_operation")
public class JournalOperation extends EntityWithUUID {
    @Column("whencreated")
    private Instant whenCreated;
    private Client client;
    private Account sender;
    private Account recipient;
    private Money money;
    @Column("type")
    private OperationType type;

    public JournalOperation(Instant whenCreated, Client client, OperationType type) {
        this.whenCreated = whenCreated;
        this.client = client;
        this.type = type;
    }

    public Instant getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Instant whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getRecipient() {
        return recipient;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JournalOperation{" +
                "whenCreated=" + whenCreated +
                ", client=" + client +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", money=" + money +
                '}';
    }
}
