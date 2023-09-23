package edu.vsu.putinpa.application.service.operations.oneside;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.operations.OperationImpl;

abstract public class OperationImplWithOneSide extends OperationImpl {
    private final Account account;

    public OperationImplWithOneSide(Account account, Money money) {
        super(money);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
