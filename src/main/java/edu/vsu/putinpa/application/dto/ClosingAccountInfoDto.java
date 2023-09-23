package edu.vsu.putinpa.application.dto;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;

public class ClosingAccountInfoDto extends OperationInfoDto {
    private Account target;
    private Account recipient;
    public ClosingAccountInfoDto(Client invoker, Account target, Account recipient) {
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
