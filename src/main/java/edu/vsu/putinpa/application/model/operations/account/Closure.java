package edu.vsu.putinpa.application.model.operations.account;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.operations.OperationType;

import java.util.Set;

final public class Closure extends OperationImplWithAccount {
    private final Account recipient;

    public Closure(Account account, Account recipient) {
        super(account);
        this.recipient = recipient;
    }

    public Account getRecipient() {
        return recipient;
    }

    @Override
    public void execute() {

    }
}
