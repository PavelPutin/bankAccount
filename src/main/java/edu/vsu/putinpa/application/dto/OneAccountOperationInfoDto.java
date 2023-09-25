package edu.vsu.putinpa.application.dto;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

abstract public class OneAccountOperationInfoDto extends OperationInfoDto {
    private Account target;
    private Money money;
    public OneAccountOperationInfoDto(Client invoker, Account target, Money money) {
        super(invoker);
        this.target = target;
        this.money = money;
    }

    public Account getTarget() {
        return target;
    }

    public Money getMoney() {
        return money;
    }
}
