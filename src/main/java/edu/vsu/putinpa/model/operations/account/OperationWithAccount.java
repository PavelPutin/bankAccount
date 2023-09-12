package edu.vsu.putinpa.model.operations.account;

import edu.vsu.putinpa.model.Account;

abstract public class OperationWithAccount {
    private final Account account;


    public OperationWithAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
