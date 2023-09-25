package edu.vsu.putinpa.application.dto;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

public class ReplenishmentInfoDto extends OperationInfoDto {
    private Account target;
    private Money money;

    public ReplenishmentInfoDto(Client invoker, Account target, Money money) {
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
