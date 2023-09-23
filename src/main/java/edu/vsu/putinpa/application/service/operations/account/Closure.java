package edu.vsu.putinpa.application.service.operations.account;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.operations.OperationImpl;

final public class Closure extends OperationImpl {
    private final Account recipient;

    public Closure(Account recipient, Money money) {
        super(money);
        this.recipient = recipient;
    }

    public Account getRecipient() {
        return recipient;
    }

    @Override
    public void execute() {

    }
}
