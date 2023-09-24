package edu.vsu.putinpa.application.dto;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

public class TransferInfoDto extends OperationInfoDto {
    private Account sender;
    private Account recipient;
    private Money money;

    public TransferInfoDto(Client invoker, Account sender, Account recipient, Money money) {
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
