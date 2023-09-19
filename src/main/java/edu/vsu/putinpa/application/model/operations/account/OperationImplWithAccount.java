package edu.vsu.putinpa.application.model.operations.account;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.model.operations.OperationImpl;

abstract public class OperationImplWithAccount extends OperationImpl {
    private final Account account;

    public OperationImplWithAccount(Account account, Money money) {
        super(money);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
