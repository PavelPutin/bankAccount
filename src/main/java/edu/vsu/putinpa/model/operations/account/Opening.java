package edu.vsu.putinpa.model.operations.account;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;
import edu.vsu.putinpa.model.operations.OperationType;

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
