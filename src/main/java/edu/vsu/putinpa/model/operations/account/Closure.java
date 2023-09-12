package edu.vsu.putinpa.model.operations.account;

import edu.vsu.putinpa.model.Account;

final public class Closure extends OperationWithAccount {
    private final Account recipient;

    public Closure(Account account, Account recipient) {
        super(account);
        this.recipient = recipient;
    }

    public Account getRecipient() {
        return recipient;
    }
}
