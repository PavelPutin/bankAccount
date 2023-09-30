package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.RecipientInfo;

public class ReplenishmentInfo extends OneAccountOperationInfo {

    @RecipientInfo
    private Account recipient;

    public ReplenishmentInfo(Client invoker, Account recipient, Money money) {
        super(invoker, money);
        this.recipient = recipient;
    }

    public Account getRecipient() {
        return recipient;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }
}
