package edu.vsu.putinpa.application.service.operations.account;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.operations.OperationImpl;

public class Opening extends OperationImpl {

    private Account sender;

    public Opening(Account sender, Money money) {
        super(money);
        this.sender = sender;
    }

    public Account getSender() {
        return sender;
    }

    @Override
    public void execute() {
        Account created = new Account();
    }
}
