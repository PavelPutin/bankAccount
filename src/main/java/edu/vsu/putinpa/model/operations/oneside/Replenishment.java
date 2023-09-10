package edu.vsu.putinpa.model.operations.oneside;

import edu.vsu.putinpa.model.Account;
import edu.vsu.putinpa.model.Money;

public class Replenishment extends OperationWithOneSide {

    public Replenishment(Account account, Money value) {
        super(account, value);
    }
}
