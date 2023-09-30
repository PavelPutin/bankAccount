package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.MoneyInfo;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.RecipientInfo;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.SenderInfo;

public class TransferInfo extends OperationInfo {
    @SenderInfo
    private Account sender;
    @RecipientInfo
    private Account recipient;
    @MoneyInfo
    private Money money;

    public TransferInfo(Client invoker, Account sender, Account recipient, Money money) {
        super(invoker);
        this.sender = sender;
        this.recipient = recipient;
        this.money = money;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
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
