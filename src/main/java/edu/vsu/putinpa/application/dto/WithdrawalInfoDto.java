package edu.vsu.putinpa.application.dto;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.Client;
import edu.vsu.putinpa.application.model.Money;

public class WithdrawalInfoDto extends OneAccountOperationInfoDto {
    public WithdrawalInfoDto(Client invoker, Account target, Money money) {
        super(invoker, target, money);
    }
}
