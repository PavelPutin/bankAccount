package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.MoneyInfo;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.RecipientInfo;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.SenderInfo;

public class ClosingAccountInfo extends OperationInfo {
    @SenderInfo
    private Account target;
    @RecipientInfo
    private Account recipient;
    @MoneyInfo
    private Money money;
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

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }
}
