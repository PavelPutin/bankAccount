package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;

public class ClosingAccountInfo extends OperationInfo {
    private Account target;
    private Account recipient;
    public ClosingAccountInfo(Client invoker, Account target, Account recipient) {
        super(invoker);
        this.target = target;
        this.recipient = recipient;
    }

    public Account getTarget() {
        return target;
    }

    public void setTarget(Account target) {
        this.target = target;
    }

    public Account getRecipient() {
        return recipient;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }
}
