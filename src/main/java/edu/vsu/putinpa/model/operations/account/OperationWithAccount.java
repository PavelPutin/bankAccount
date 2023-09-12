package edu.vsu.putinpa.model.operations.account;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.operations.Operation;

abstract public class OperationWithAccount extends Operation {
    private final Account account;


    public OperationWithAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
