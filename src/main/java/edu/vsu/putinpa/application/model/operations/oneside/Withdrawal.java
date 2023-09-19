package edu.vsu.putinpa.application.model.operations.oneside;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.model.operations.OperationType;

import java.util.Set;

public class Withdrawal extends OperationImplWithOneSide {

    public Withdrawal(Account account, Money money) {
        super(account, money);
    }

    @Override
    public void execute() {
        getAccount().getBalance().subtract(getMoney())
    }
}
