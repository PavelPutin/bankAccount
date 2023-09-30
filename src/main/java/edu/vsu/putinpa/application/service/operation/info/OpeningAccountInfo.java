package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.MoneyInfo;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.RecipientInfo;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.SenderInfo;

public class OpeningAccountInfo extends OperationInfo {
    private String name;
    private String currency;
    @SenderInfo
    private Account sender;
    @RecipientInfo
    private Account created;
    @MoneyInfo
    private Money money;

    public OpeningAccountInfo(Client invoker, String name, String currency, Account sender, Money money) {
        super(invoker);
        this.name = name;
        this.currency = currency;
        this.sender = sender;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getCreated() {
        return created;
    }

    public void setCreated(Account created) {
        this.created = created;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }
}
