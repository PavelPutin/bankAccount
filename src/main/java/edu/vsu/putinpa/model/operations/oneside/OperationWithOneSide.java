package edu.vsu.putinpa.model.operations.oneside;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;
import edu.vsu.putinpa.model.operations.Operation;

abstract public class OperationWithOneSide extends Operation {
    private final Account account;
    private final Money value;

    public OperationWithOneSide(Account account, Money value) {
        this.account = account;
        this.value = value;
    }

    public Account getAccount() {
        return account;
    }

    public Money getValue() {
        return value;
    }
}
