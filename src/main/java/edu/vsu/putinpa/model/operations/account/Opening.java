package edu.vsu.putinpa.model.operations.account;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;

final public class Opening extends OperationWithAccount {
    private final Money value;

    public Opening(Account account, Money value) {
        super(account);
        this.value = value;
    }

    public Money getValue() {
        return value;
    }
}
