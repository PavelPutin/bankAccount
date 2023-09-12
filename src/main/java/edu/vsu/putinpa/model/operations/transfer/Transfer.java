package edu.vsu.putinpa.model.operations.transfer;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;
import edu.vsu.putinpa.model.operations.Operation;

abstract public class Transfer extends Operation {
    private final Account sender;
    private final Account recipient;
    private final Money value;
    private final String description;

    public Transfer(Account sender, Account recipient, Money value, String description) {
        this.sender = sender;
        this.recipient = recipient;
        this.value = value;
        this.description = description;
    }

    public Account getSender() {
        return sender;
    }

    public Account getRecipient() {
        return recipient;
    }

    public Money getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
