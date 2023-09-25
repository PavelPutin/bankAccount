package edu.vsu.putinpa.application.dto;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

public class ReplenishmentInfoDto extends OneAccountOperationInfoDto {

    public ReplenishmentInfoDto(Client invoker, Account target, Money money) {
        super(invoker, target, money);
    }
}
