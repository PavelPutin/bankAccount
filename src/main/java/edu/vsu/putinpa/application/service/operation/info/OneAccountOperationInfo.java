package edu.vsu.putinpa.application.service.operation.info;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.MoneyInfo;

abstract public class OneAccountOperationInfo extends OperationInfo {
    @MoneyInfo
    private Money money;
    public OneAccountOperationInfo(Client invoker, Money money) {
        super(invoker);
        this.money = money;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }
}
