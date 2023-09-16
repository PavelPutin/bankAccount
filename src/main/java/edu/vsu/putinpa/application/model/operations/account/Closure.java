package edu.vsu.putinpa.application.model.operations.account;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.operations.OperationType;

import java.util.Set;

final public class Closure extends OperationWithAccount {
    private final Account recipient;

    public Closure(Account account, Account recipient) {
        super(account);
        this.recipient = recipient;
    }

    public Account getRecipient() {
        return recipient;
    }

    @Override
    protected Set<OperationType> initTypes() {
        return Set.of(OperationType.TRANSFER);
    }
}
