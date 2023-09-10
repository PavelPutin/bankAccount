package edu.vsu.putinpa.model.operations.account;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;

abstract public class OperationWithAccount {
    private final Account account;
    private final Money value;

    public OperationWithAccount(Account account, Money value) {
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
