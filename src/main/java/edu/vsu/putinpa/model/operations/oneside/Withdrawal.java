package edu.vsu.putinpa.model.operations.oneside;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;
import edu.vsu.putinpa.model.operations.OperationType;

import java.util.Set;

public class Withdrawal extends OperationWithOneSide {

    public Withdrawal(Account account, Money value) {
        super(account, value);
    }

    @Override
    protected Set<OperationType> initTypes() {
        return Set.of(OperationType.WITHDRAWAL);
    }
}
