package edu.vsu.putinpa.application.model.operations.account;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.model.operations.OperationType;

import java.util.Set;

final public class Opening extends OperationWithAccount {
    private final Money value;

    public Opening(Account account, Money value) {
        super(account);
        this.value = value;
    }

    public Money getValue() {
        return value;
    }

    @Override
    protected Set<OperationType> initTypes() {
        return Set.of(OperationType.TRANSFER);
    }
}
