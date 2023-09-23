package edu.vsu.putinpa.application.service.operations.oneside;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;

public class Replenishment extends OperationImplWithOneSide {

    public Replenishment(Account account, Money money) {
        super(account, money);
    }

    @Override
    public void execute() {
        getAccount().getBalance().add(getMoney());
    }
}
