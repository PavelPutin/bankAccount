package edu.vsu.putinpa.application.model.operations.transfer;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.model.operations.OperationType;

import java.util.Set;

public class Incoming extends Transfer {

    public Incoming(Account sender, Account recipient, Money value, String description) {
        super(sender, recipient, value, description);
    }

    @Override
    protected Set<OperationType> initTypes() {
        return Set.of(OperationType.TRANSFER, OperationType.REPLENISHMENT);
    }
}
