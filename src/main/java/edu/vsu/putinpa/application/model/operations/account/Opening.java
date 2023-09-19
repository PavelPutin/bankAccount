package edu.vsu.putinpa.application.model.operations.account;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.model.operations.OperationType;

import java.util.Set;

public class Opening extends OperationImplWithAccount {

    public Opening(Account account, Money money) {
        super(account, money);
    }


    @Override
    public void execute() {

    }
}
