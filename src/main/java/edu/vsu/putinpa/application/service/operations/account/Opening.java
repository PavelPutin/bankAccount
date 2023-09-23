package edu.vsu.putinpa.application.service.operations.account;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;

public class Opening extends OperationImplWithAccount {

    public Opening(Account account, Money money) {
        super(account, money);
    }


    @Override
    public void execute() {

    }
}
