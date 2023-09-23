package edu.vsu.putinpa.application.service.operations.oneside;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;

public class Withdrawal extends OperationImplWithOneSide {

    public Withdrawal(Account account, Money money) {
        super(account, money);
    }

    @Override
    public void execute() {
        getAccount().getBalance().subtract(getMoney());
        getRepository().save(getAccount());
    }
}
