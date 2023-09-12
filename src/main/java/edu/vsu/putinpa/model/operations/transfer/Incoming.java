package edu.vsu.putinpa.model.operations.transfer;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;
import edu.vsu.putinpa.model.operations.OperationType;

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
