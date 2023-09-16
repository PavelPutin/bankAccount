package edu.vsu.putinpa.application.model.operations.oneside;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.model.operations.OperationType;

import java.util.Set;

public class Replenishment extends OperationWithOneSide {

    public Replenishment(Account account, Money value) {
        super(account, value);
    }

    @Override
    protected Set<OperationType> initTypes() {
        return Set.of(OperationType.REPLENISHMENT);
    }
}
